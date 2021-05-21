package com.wjbaker.ccm.crosshair.style.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import com.wjbaker.ccm.type.RGBA;

public final class ArrowStyle extends AbstractCrosshairStyle {

    public ArrowStyle(final CustomCrosshair crosshair) {
        super(crosshair);
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        boolean isOutlineEnabled = this.crosshair.isOutlineEnabled.get();
        int width = this.crosshair.width.get();
        int height = this.crosshair.height.get();
        int thickness = this.crosshair.thickness.get();

        if (isOutlineEnabled) {
            RGBA outlineColour = this.crosshair.outlineColour.get();

            this.renderManager.drawLines(new float[] {
                x - width - 1, y + height + 1, x, y,
                x, y, x + width + 1, y + height + 1
            }, thickness + 3, outlineColour);

            this.renderManager.drawCircle(x, y, thickness == 1 ? 0.5F : thickness / 5.0F, 1.0F, outlineColour);
        }

        this.renderManager.drawLines(new float[] {
            x - width, y + height, x, y,
            x, y, x + width, y + height
        }, thickness, computedProperties.colour());
    }
}
