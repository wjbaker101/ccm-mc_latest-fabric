package com.wjbaker.ccm.crosshair.style.styles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.AbstractCrosshairStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public final class DefaultStyle extends AbstractCrosshairStyle {

    private static final Identifier ICONS = new Identifier("hud/crosshair");

    public DefaultStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR,
            GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR,
            GlStateManager.SrcFactor.ONE,
            GlStateManager.DstFactor.ZERO);

        var crosshairSize = 15;
        var textureSize = 15;

        drawContext.drawGuiTexture(
            ICONS,
            (x - crosshairSize) / 2, (y - crosshairSize) / 2,
            textureSize, textureSize);
    }
}