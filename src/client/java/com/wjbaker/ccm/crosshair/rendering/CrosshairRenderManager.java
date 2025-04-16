package com.wjbaker.ccm.crosshair.rendering;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.styles.*;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import com.wjbaker.ccm.rendering.ModTheme;
import com.wjbaker.ccm.rendering.RenderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

import java.util.Set;

public final class CrosshairRenderManager {

    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE = Identifier.ofVanilla("hud/crosshair_attack_indicator_full");
    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/crosshair_attack_indicator_background");
    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.ofVanilla("hud/crosshair_attack_indicator_progress");

    private final RenderManager renderManager;

    private final Set<Item> itemCooldownItems = ImmutableSet.of(
        Items.ENDER_PEARL,
        Items.CHORUS_FRUIT
    );

    public CrosshairRenderManager() {
        this.renderManager = new RenderManager();
    }

    public void draw(final CustomCrosshair crosshair, final DrawContext drawContext, final int x, final int y) {
        var computedProperties = new ComputedProperties(crosshair);

        if (!computedProperties.isVisible())
            return;

        var calculatedStyle = MinecraftClient.getInstance().inGameHud.getDebugHud().shouldShowDebugHud() && crosshair.isKeepDebugEnabled.get()
            ? CrosshairStyle.Styles.DEBUG
            : crosshair.style.get();

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        var matrixStack = drawContext.getMatrices();

        var style = this.mapCrosshairStyle(matrixStack, calculatedStyle, crosshair);
        var isItemCooldownEnabled = crosshair.isItemCooldownEnabled.get();
        var isDotEnabled = crosshair.isDotEnabled.get();

        if (isItemCooldownEnabled)
            this.drawItemCooldownIndicator(matrixStack, crosshair, computedProperties, x, y);

        if (isDotEnabled && crosshair.style.get() != CrosshairStyle.Styles.VANILLA)
            this.renderManager.drawCircle(matrixStack, x, y, 0.5F, 1.0F, crosshair.dotColour.get());

        this.drawDefaultAttackIndicator(drawContext);

        var renderX = x + crosshair.offsetX.get();
        var renderY = y + crosshair.offsetY.get();

        this.drawIndicators(drawContext, crosshair, computedProperties, renderX, renderY);

        this.preTransformation(drawContext, crosshair, renderX, renderY);

        var xx = calculatedStyle == CrosshairStyle.Styles.DEBUG ? renderX : 0;
        var yy = calculatedStyle == CrosshairStyle.Styles.DEBUG ? renderY : 0;

        style.draw(drawContext, xx, yy, computedProperties);

        this.postTransformation(drawContext);
    }

    private CrosshairStyle mapCrosshairStyle(
        final MatrixStack matrixStack,
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
        final DrawContext drawContext,
        final CustomCrosshair crosshair,
        final int x, final int y) {

        drawContext.getMatrices().push();
        var matrices = drawContext.getMatrices().peek().getPositionMatrix();

        var rotation = crosshair.rotation.get();
        var scale = crosshair.scale.get();

        matrices.translate(x, y, 0.0F);
        matrices.scale(scale / 100.0F, scale / 100.0F, 1.0F);
        matrices.rotateAffine(new Quaternionf(RotationAxis.POSITIVE_Z.rotationDegrees(rotation)));
    }

    private void postTransformation(final DrawContext drawContext) {
        drawContext.getMatrices().pop();
    }

    private void drawItemCooldownIndicator(
        final MatrixStack matrixStack,
        final CustomCrosshair crosshair,
        final ComputedProperties computedProperties,
        final int x, final int y) {

        var player = MinecraftClient.getInstance().player;

        if (player == null)
            return;

        var colour = crosshair.itemCooldownColour.get();

        var width = crosshair.width.get();
        var height = crosshair.height.get();
        var maxSize = Math.max(width, height);
        var offset = 3;

        for (final Item item : this.itemCooldownItems) {
            var cooldown = player.getItemCooldownManager().getCooldownProgress(item.getDefaultStack(), 0.0F);

            if (cooldown == 0.0F)
                continue;

            var progress = Math.round(360 - (360 * cooldown));

            this.renderManager.drawPartialCircle(
                matrixStack,
                x, y,
                computedProperties.gap() + maxSize + offset,
                0,
                progress,
                2.0F,
                colour);

            offset += 3;
        }
    }

    private void drawDefaultAttackIndicator(final DrawContext drawContext) {
        var mc = MinecraftClient.getInstance();

        if (mc.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR && mc.player != null) {
            var f = mc.player.getAttackCooldownProgress(0.0F);
            var bl = false;

            if (mc.targetedEntity instanceof LivingEntity && f >= 1.0F) {
                bl = mc.player.getAttackCooldownProgressPerTick() > 5.0F;
                bl &= mc.targetedEntity.isAlive();
            }

            var j = mc.getWindow().getScaledHeight() / 2 - 7 + 16;
            var k = mc.getWindow().getScaledWidth() / 2 - 8;

            if (bl) {
                drawContext.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE, k, j, 16, 16);
            }
            else if (f < 1.0F) {
                var l = (int)(f * 17.0F);

                drawContext.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, k, j, 16, 4);
                drawContext.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE, 16, 4, 0, 0, k, j, l, 4);
            }
        }
    }

    private void drawIndicators(
        final DrawContext drawContext,
        final CustomCrosshair crosshair,
        final ComputedProperties computedProperties,
        final int x, final int y) {

        var drawX = x + crosshair.gap.get() + 5;
        var drawY = y + crosshair.gap.get() + 5;

        var mc = MinecraftClient.getInstance();

        mc.getTextureManager().getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).setFilter(false, false);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        var indicatorItems = computedProperties.indicatorItems();
        var matrixStack = drawContext.getMatrices();

        DiffuseLighting.disableGuiDepthLighting();

        for (var indicatorItem : indicatorItems) {
            matrixStack.scale(0.5F, 0.5F, 1.0F);
            drawContext.drawItem(indicatorItem.icon(), drawX * 2 - 8, drawY * 2 - 8);
            matrixStack.scale(2F, 2F, 1.0F);

            this.renderManager.drawSmallText(drawContext, indicatorItem.text(), drawX + 5, drawY, ModTheme.WHITE, true);

            drawX += 15;
        }

        DiffuseLighting.enableGuiDepthLighting();
    }
}