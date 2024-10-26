package com.wjbaker.ccm.crosshair.styles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public final class VanillaStyle extends CrosshairStyle {

    private static final Identifier ICONS = Identifier.ofVanilla("hud/crosshair");

    public VanillaStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
        RenderSystem.enableBlend();

        RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR,
            GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR,
            GlStateManager.SrcFactor.ONE,
            GlStateManager.DstFactor.ZERO);

        var crosshairSize = 15;
        var textureSize = 15;

        drawContext.drawGuiTexture(
            RenderLayer::getCrosshair,
            ICONS,
            x - (crosshairSize / 2), y - (crosshairSize / 2),
            textureSize, textureSize);
    }
}