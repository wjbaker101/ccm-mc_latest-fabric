package com.wjbaker.ccm.crosshair.style.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import com.wjbaker.ccm.type.RGBA;
import net.minecraft.client.util.math.MatrixStack;

public final class CircleStyle extends AbstractCrosshairStyle {

    public CircleStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        boolean isOutlineEnabled = this.crosshair.isOutlineEnabled.get();
        int thickness = this.crosshair.thickness.get();

        if (isOutlineEnabled) {
            RGBA outlineColour = this.crosshair.outlineColour.get();

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