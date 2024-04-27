package com.wjbaker.ccm.crosshair.rendering;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.platform.GlStateManager;
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
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;

import java.util.Set;

public final class CrosshairRenderManager {

    private static final Identifier ICONS = new Identifier("textures/gui/icons.png");

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

        var transformMatrixStack = RenderSystem.getModelViewStack();

        var renderX = x + crosshair.offsetX.get();
        var renderY = y + crosshair.offsetY.get();

        this.drawToolDamageIndicator(drawContext, crosshair, computedProperties, renderX, renderY);

        this.preTransformation(transformMatrixStack, crosshair, renderX, renderY);

        style.draw(drawContext, 0, 0, computedProperties);

        this.postTransformation(transformMatrixStack);
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
        final Matrix4fStack matrixStack,
        final CustomCrosshair crosshair,
        final int x, final int y) {

        var rotation = crosshair.rotation.get();
        var scale = crosshair.scale.get() - 2;
        var windowScaling = (float)MinecraftClient.getInstance().getWindow().getScaleFactor() / 2.0F;

        matrixStack.pushMatrix();
        matrixStack.translate(x, y, 0.0F);
        matrixStack.scale(scale / 100.0F / windowScaling, scale / 100.0F / windowScaling, 1.0F);
        matrixStack.rotateAffine(new Quaternionf(RotationAxis.POSITIVE_Z.rotationDegrees(rotation)));

        RenderSystem.applyModelViewMatrix();
    }

    private void postTransformation(final Matrix4fStack matrixStack) {
        matrixStack.popMatrix();
        RenderSystem.applyModelViewMatrix();
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
            var cooldown = player.getItemCooldownManager().getCooldownProgress(item, 0.0F);

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
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);

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
                drawContext.drawTexture(ICONS, k, j, 68, 94, 16, 16, 256, 256);
            }
            else if (f < 1.0F) {
                var l = (int)(f * 17.0F);

                drawContext.drawTexture(ICONS, k, j, 36, 94, 16, 4, 256, 256);
                drawContext.drawTexture(ICONS, k, j, 52, 94, l, 4, 256, 256);
            }
        }
    }

    private void drawToolDamageIndicator(
        final DrawContext drawContext,
        final CustomCrosshair crosshair,
        final ComputedProperties computedProperties,
        final int x, final int y) {

        var drawX = x + crosshair.gap.get() + 5;
        var drawY = y + crosshair.gap.get() + 5;

        var mc = MinecraftClient.getInstance();

        mc.getTextureManager().getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).setFilter(false, false);
        RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        var indicatorItems = computedProperties.indicatorItems();
        var immediate = mc.getBufferBuilders().getEntityVertexConsumers();
        var matrixStack = drawContext.getMatrices();
        var itemRenderer = mc.getItemRenderer();

        DiffuseLighting.disableGuiDepthLighting();

        for (var indicatorItem : indicatorItems) {
            matrixStack.push();
            matrixStack.translate(drawX, drawY, (100.0F + 1));
            matrixStack.scale(1.0F, -1.0F, 1.0F);
            matrixStack.scale(8F, 8F, 8F);
            var model = itemRenderer.getModel(indicatorItem.icon(), null, null, 0);

            itemRenderer.renderItem(indicatorItem.icon(), ModelTransformationMode.GUI, false, matrixStack, immediate, 15728880, OverlayTexture.DEFAULT_UV, model);
            immediate.draw();

            matrixStack.pop();

            this.renderManager.drawSmallText(drawContext, indicatorItem.text(), drawX + 5, drawY, ModTheme.WHITE, true);

            drawX += 15;
        }

        DiffuseLighting.enableGuiDepthLighting();
    }
}