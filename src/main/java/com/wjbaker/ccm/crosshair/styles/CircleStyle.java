package com.wjbaker.ccm.crosshair.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public final class CircleStyle extends CrosshairStyle {

    public CircleStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
        var isOutlineEnabled = this.crosshair.isOutlineEnabled.get();
        var thickness = this.crosshair.thickness.get();

        if (isOutlineEnabled) {
            var outlineColour = this.crosshair.outlineColour.get();

            this.renderManager.drawCircle(this.matrixStack, x, y, computedProperties.gap() + 0.5F + thickness, 2.0F, outlineColour);
            this.renderManager.drawCircle(this.matrixStack, x, y, computedProperties.gap() - 0.5F, 2.0F, outlineColour);
        }

        this.renderManager.drawTorus(
            this.matrixStack,
            x, y,
            computedProperties.gap(),
            computedProperties.gap() + thickness,
            computedProperties.colour());
    }
}