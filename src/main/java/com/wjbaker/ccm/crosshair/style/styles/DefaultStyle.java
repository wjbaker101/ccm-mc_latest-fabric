package com.wjbaker.ccm.crosshair.style.styles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

public final class DefaultStyle extends AbstractCrosshairStyle {

    public DefaultStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR,
            GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR,
            GlStateManager.SrcFactor.ONE,
            GlStateManager.DstFactor.ZERO);

        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);

        var crosshairSize = 15;
        var textureSize = 256;

        DrawableHelper.drawTexture(
            this.matrixStack,
            x - Math.round(crosshairSize / 2.0F),
            y - Math.round(crosshairSize / 2.0F),
            0, 0,
            crosshairSize, crosshairSize,
            textureSize, textureSize);
    }
}
