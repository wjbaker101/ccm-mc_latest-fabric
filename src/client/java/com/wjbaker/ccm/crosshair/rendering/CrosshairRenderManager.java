package com.wjbaker.ccm.crosshair.rendering;

import com.google.common.collect.ImmutableSet;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.styles.*;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import com.wjbaker.ccm.rendering.ModTheme;
import com.wjbaker.ccm.rendering.RenderManager;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.AttackRange;
import org.joml.Matrix3x2fStack;

import java.util.Set;

public final class CrosshairRenderManager {

    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE = Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_full");
    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_background");
    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE = Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_progress");

    private final RenderManager renderManager;

    private final Set<Item> itemCooldownItems = ImmutableSet.of(
        Items.ENDER_PEARL,
        Items.CHORUS_FRUIT
    );

    public CrosshairRenderManager() {
        this.renderManager = new RenderManager();
    }

    public void draw(final CustomCrosshair crosshair, final GuiGraphicsExtractor graphics, final int x, final int y) {
        var computedProperties = new ComputedProperties(crosshair);

        if (!computedProperties.isVisible())
            return;

        var calculatedStyle = Minecraft.getInstance().gui.getDebugOverlay().showDebugScreen() && crosshair.isKeepDebugEnabled.get()
            ? CrosshairStyle.Styles.DEBUG
            : crosshair.style.get();

        var matrixStack = graphics.pose();

        var style = this.mapCrosshairStyle(matrixStack, calculatedStyle, crosshair);
        var isItemCooldownEnabled = crosshair.isItemCooldownEnabled.get();
        var isDotEnabled = crosshair.isDotEnabled.get();

        if (isItemCooldownEnabled)
            this.drawItemCooldownIndicator(graphics, crosshair, computedProperties, x, y);

        if (isDotEnabled && crosshair.style.get() != CrosshairStyle.Styles.VANILLA)
            this.renderManager.drawCircle(graphics, x, y, 0.5F, 1.0F, crosshair.dotColour.get());

        this.drawDefaultAttackIndicator(graphics);

        var renderX = x + crosshair.offsetX.get();
        var renderY = y + crosshair.offsetY.get();

        this.drawIndicators(graphics, crosshair, computedProperties, renderX, renderY);

        this.preTransformation(graphics, crosshair, renderX, renderY);

        style.draw(graphics, 0, 0, computedProperties);

        this.postTransformation(graphics, renderX, renderY);
    }

    private CrosshairStyle mapCrosshairStyle(
        final Matrix3x2fStack matrixStack,
        final CrosshairStyle.Styles style,
        final CustomCrosshair crosshair) {

        return switch (style) {
            case VANILLA -> new VanillaStyle(matrixStack, crosshair);
            case CIRCLE -> new CircleStyle(matrixStack, crosshair);
            case SQUARE -> new SquareStyle(matrixStack, crosshair);
            case TRIANGLE -> new TriangleStyle(matrixStack, crosshair);
            case ARROW -> new ArrowStyle(matrixStack, crosshair);
            case DEBUG -> new DebugStyle(matrixStack, crosshair);
            case DRAWN -> new DrawnStyle(matrixStack, crosshair);

            default -> new CrossStyle(matrixStack, crosshair);
        };
    }

    private void preTransformation(
        final GuiGraphicsExtractor graphics,
        final CustomCrosshair crosshair,
        final int x, final int y) {

        var matrices = graphics.pose();
        matrices.pushMatrix();

        var rotation = crosshair.rotation.get();
        var scale = crosshair.scale.get();

        matrices.scale(scale / 100.0F, scale / 100.0F);
        matrices.rotate(rotation * 0.01745329F);

        matrices.translateLocal(x, y);
    }

    private void postTransformation(final GuiGraphicsExtractor graphics, final int x, final int y) {
        var matrices = graphics.pose();
        matrices.popMatrix();
    }

    private void drawItemCooldownIndicator(
        final GuiGraphicsExtractor graphics,
        final CustomCrosshair crosshair,
        final ComputedProperties computedProperties,
        final int x, final int y) {

        var player = Minecraft.getInstance().player;

        if (player == null)
            return;

        var colour = crosshair.itemCooldownColour.get();

        var width = crosshair.width.get();
        var height = crosshair.height.get();
        var maxSize = Math.max(width, height);
        var offset = 3;

        for (final Item item : this.itemCooldownItems) {
            var cooldown = player.getCooldowns().getCooldownPercent(item.getDefaultInstance(), 0.0F);

            if (cooldown == 0.0F)
                continue;

            var progress = Math.round(360 - (360 * cooldown));

            this.renderManager.drawPartialCircle(
                graphics,
                x, y,
                computedProperties.gap() + maxSize + offset,
                0,
                progress,
                2.0F,
                colour);

            offset += 3;
        }
    }

    private void drawDefaultAttackIndicator(final GuiGraphicsExtractor graphics) {
        var mc = Minecraft.getInstance();

        if (mc.options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
            float attackStrengthScale = mc.player.getAttackStrengthScale(0.0F);
            boolean renderMaxAttackIndicator = false;
            if (mc.crosshairPickEntity != null && mc.crosshairPickEntity instanceof LivingEntity && attackStrengthScale >= 1.0F) {
                renderMaxAttackIndicator = mc.player.getCurrentItemAttackStrengthDelay() > 5.0F;
                renderMaxAttackIndicator &= mc.crosshairPickEntity.isAlive();
                var attackRange = mc.player.getActiveItem().get(DataComponents.ATTACK_RANGE);
                renderMaxAttackIndicator &= attackRange == null || attackRange.isInRange(mc.player, mc.hitResult.getLocation());
            }

            int y = graphics.guiHeight() / 2 - 7 + 16;
            int x = graphics.guiWidth() / 2 - 8;
            if (renderMaxAttackIndicator) {
                graphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE, x, y, 16, 16);
            } else if (attackStrengthScale < 1.0F) {
                int progress = (int)(attackStrengthScale * 17.0F);
                graphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE, x, y, 16, 4);
                graphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE, 16, 4, 0, 0, x, y, progress, 4);
            }
        }
    }

    private void drawIndicators(
        final GuiGraphicsExtractor graphics,
        final CustomCrosshair crosshair,
        final ComputedProperties computedProperties,
        final int x, final int y) {

        var drawX = x + crosshair.gap.get() + 5;
        var drawY = y + crosshair.gap.get() + 5;

        var indicatorItems = computedProperties.indicatorItems();
        var matrixStack = graphics.pose();

        for (var indicatorItem : indicatorItems) {
            matrixStack.scale(0.5F, 0.5F);
            graphics.item(indicatorItem.icon(), drawX * 2 - 8, drawY * 2 - 8);
            matrixStack.scale(2F, 2F);

            this.renderManager.drawSmallText(graphics, indicatorItem.text(), drawX + 5, drawY, ModTheme.WHITE, true);

            drawX += 15;
        }
    }
}