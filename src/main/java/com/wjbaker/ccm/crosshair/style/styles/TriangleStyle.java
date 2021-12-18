package com.wjbaker.ccm.crosshair.style.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import net.minecraft.client.util.math.MatrixStack;

public final class TriangleStyle extends AbstractCrosshairStyle {

    public TriangleStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        var isOutlineEnabled = this.crosshair.isOutlineEnabled.get();
        var width = this.crosshair.width.get();
        var height = this.crosshair.height.get();
        var gap = computedProperties.gap();
        var colour = computedProperties.colour();
        var isAdaptiveColourEnabled = this.crosshair.isAdaptiveColourEnabled.get();

        if (isOutlineEnabled) {
            var outlineColour = this.crosshair.outlineColour.get();

            // Outer
            this.renderManager.drawLines(this.matrixStack, new float[] {
                x, y - (height / 2.0F) - gap - 0.5F,
                x + width / 2.0F + gap + 1.0F, y + (height / 2.0F) + gap + 0.5F,
                x + width / 2.0F + gap + 1.0F, y + (height / 2.0F) + gap + 0.5F,
                x - (width / 2.0F) - gap - 1.0F, y + (height / 2.0F) + gap + 0.5F,
                x - (width / 2.0F) - gap - 1.0F, y + (height / 2.0F) + gap + 0.5F,
                x, y - (height / 2.0F) - gap - 1.0F
            }, 2.0F, outlineColour);

            // Inner
            this.renderManager.drawLines(this.matrixStack, new float[] {
                x, y - (height / 2.0F) - gap + 0.5F,
                x + width / 2.0F + gap - 1.0F, y + (height / 2.0F) + gap - 0.5F,
                x + width / 2.0F + gap - 1.0F, y + (height / 2.0F) + gap - 0.5F,
                x - (width / 2.0F) - gap + 1.0F, y + (height / 2.0F) + gap - 0.5F,
                x - (width / 2.0F) - gap + 1.0F, y + (height / 2.0F) + gap - 0.5F,
                x, y - (height / 2.0F) - gap + 1.0F
            }, 2.0F, outlineColour);
        }

        this.renderManager.drawLines(this.matrixStack, new float[] {
            x, y - (height / 2.0F) - gap,
            x + width / 2.0F + gap, y + (height / 2.0F) + gap,
            x + width / 2.0F + gap, y + (height / 2.0F) + gap,
            x - (width / 2.0F) - gap, y + (height / 2.0F) + gap,
            x - (width / 2.0F) - gap, y + (height / 2.0F) + gap,
            x, y - (height / 2.0F) - gap
        }, 1.0F, colour, isAdaptiveColourEnabled);
    }
}
