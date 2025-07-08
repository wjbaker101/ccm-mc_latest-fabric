package com.wjbaker.ccm.crosshair.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

public final class VanillaStyle extends CrosshairStyle {

    private static final Identifier ICONS = Identifier.ofVanilla("hud/crosshair");

    public VanillaStyle(final Matrix3x2fStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
        var crosshairSize = 15;
        var textureSize = 15;

        drawContext.drawGuiTexture(
            RenderPipelines.CROSSHAIR,
            ICONS,
            x - (crosshairSize / 2), y - (crosshairSize / 2),
            textureSize, textureSize);
    }
}