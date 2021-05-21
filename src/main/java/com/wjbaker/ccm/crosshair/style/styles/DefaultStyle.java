package com.wjbaker.ccm.crosshair.style.styles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public final class DefaultStyle extends AbstractCrosshairStyle {

    private final Identifier guiIconsLocation;

    public DefaultStyle(final CustomCrosshair crosshair) {
        super(crosshair);

        this.guiIconsLocation = new Identifier("textures/gui/icons.png");
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        RenderSystem.enableBlend();

        RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR,
            GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR,
            GlStateManager.SrcFactor.ONE,
            GlStateManager.DstFactor.ZERO);

        MinecraftClient.getInstance().getTextureManager().bindTexture(this.guiIconsLocation);

        int crosshairSize = 15;
        int textureSize = 256;

        DrawableHelper.drawTexture(
            new MatrixStack(),
            x - Math.round(crosshairSize / 2.0F),
            y - Math.round(crosshairSize / 2.0F),
            0, 0,
            crosshairSize, crosshairSize,
            textureSize, textureSize);
    }
}
