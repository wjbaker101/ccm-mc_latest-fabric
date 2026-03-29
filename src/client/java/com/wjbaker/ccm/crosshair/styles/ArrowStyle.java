package com.wjbaker.ccm.crosshair.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Matrix3x2fStack;

public final class ArrowStyle extends CrosshairStyle {

    public ArrowStyle(final Matrix3x2fStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final GuiGraphicsExtractor graphics, final int x, final int y, final ComputedProperties computedProperties) {
        var width = this.crosshair.width.get();
        var height = this.crosshair.height.get();
        var thickness = this.crosshair.thickness.get();
        var isAdaptiveColourEnabled = this.crosshair.isAdaptiveColourEnabled.get();

        this.renderManager.drawLines(graphics, new Float[] {
            (float)x - width, (float)y + height, (float)x, (float)y,
            (float)x, (float)y, (float)x + width, (float)y + height
        }, thickness, computedProperties.colour(), isAdaptiveColourEnabled);
    }
}