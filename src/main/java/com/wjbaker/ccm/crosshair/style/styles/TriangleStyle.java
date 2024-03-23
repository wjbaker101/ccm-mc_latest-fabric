package com.wjbaker.ccm.crosshair.style.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public final class TriangleStyle extends CrosshairStyle {

    public TriangleStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
        var width = this.crosshair.width.get();
        var height = this.crosshair.height.get();
        var gap = computedProperties.gap();
        var colour = computedProperties.colour();
        var isAdaptiveColourEnabled = this.crosshair.isAdaptiveColourEnabled.get();

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
