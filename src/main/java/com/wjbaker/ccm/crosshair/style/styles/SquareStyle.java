package com.wjbaker.ccm.crosshair.style.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import com.wjbaker.ccm.type.RGBA;

public final class SquareStyle extends AbstractCrosshairStyle {

    public SquareStyle(final CustomCrosshair crosshair) {
        super(crosshair);
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        boolean isOutlineEnabled = this.crosshair.isOutlineEnabled.get();
        int width = this.crosshair.width.get();
        int height = this.crosshair.height.get();
        int thickness = this.crosshair.thickness.get();
        int gap = computedProperties.gap();
        RGBA colour = computedProperties.colour();

        if (isOutlineEnabled) {
            RGBA outlineColour = this.crosshair.outlineColour.get();

            // Inner
            this.renderManager.drawRectangle(
                x - width - gap + 0.5F, y - height - gap + 0.5F,
                x + width + gap - 0.5F, y + height + gap - 0.5F,
                2.0F,
                outlineColour);

            // Outer
            this.renderManager.drawRectangle(
                x - width - thickness - gap - 0.5F, y - height - thickness - gap - 0.5F,
                x + width + thickness + gap + 0.5F, y + height + thickness + gap + 0.5F,
                2.0F,
                outlineColour);
        }

        // Top
        this.renderManager.drawFilledRectangle(
            x - width - thickness - gap, y - height - thickness - gap,
            x + width + thickness + gap, y - height - gap,
            colour);

        // Bottom
        this.renderManager.drawFilledRectangle(
            x - width - thickness - gap, y + height + gap,
            x + width + thickness + gap, y + height + thickness + gap,
            colour);

        // Left
        this.renderManager.drawFilledRectangle(
            x - width - thickness - gap, y - gap - height,
            x - width - gap, y + gap + height,
            colour);

        // Right
        this.renderManager.drawFilledRectangle(
            x + width + gap, y - gap - height,
            x + width + thickness + gap, y + gap + height,
            colour);
    }
}
