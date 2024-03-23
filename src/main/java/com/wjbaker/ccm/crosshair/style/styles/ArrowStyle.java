package com.wjbaker.ccm.crosshair.style.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public final class ArrowStyle extends CrosshairStyle {

    public ArrowStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
        var width = this.crosshair.width.get();
        var height = this.crosshair.height.get();
        var thickness = this.crosshair.thickness.get();
        var isAdaptiveColourEnabled = this.crosshair.isAdaptiveColourEnabled.get();

        this.renderManager.drawLines(this.matrixStack, new float[] {
            x - width, y + height, x, y,
            x, y, x + width, y + height
        }, thickness, computedProperties.colour(), isAdaptiveColourEnabled);
    }
}
