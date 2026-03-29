package com.wjbaker.ccm.crosshair.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Matrix3x2fStack;

public final class CrossStyle extends CrosshairStyle {

    public CrossStyle(final Matrix3x2fStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final GuiGraphicsExtractor graphics, final int x, final int y, final ComputedProperties computedProperties) {
        var isOutlineEnabled = this.crosshair.isOutlineEnabled.get();
        var baseColour = computedProperties.colour();
        var gap = computedProperties.gap();
        var thickness = this.crosshair.thickness.get() - 0.1F;
        var width = this.crosshair.width.get();
        var height  = this.crosshair.height.get();
        var isAdaptiveColourEnabled = this.crosshair.isAdaptiveColourEnabled.get();

        // Order of the orientation of the bars:
        // Left
        // Bottom
        // Right
        // Left

        if (isOutlineEnabled) {
            var outlineColour = this.crosshair.outlineColour.get();
            var adjustedWidth = width + 1.0F;
            var adjustedHeight = height + 1.0F;
            var adjustedGap = gap - 0.5F;

            this.renderManager.drawBorderedRectangle(graphics, x - thickness, y - adjustedGap - adjustedHeight, x + thickness, y - adjustedGap, 2.0F, outlineColour, baseColour, isAdaptiveColourEnabled);
            this.renderManager.drawBorderedRectangle(graphics, x - thickness, y + adjustedGap, x + thickness, y + adjustedGap + adjustedHeight, 2.0F, outlineColour, baseColour, isAdaptiveColourEnabled);
            this.renderManager.drawBorderedRectangle(graphics, x - adjustedGap - adjustedWidth, y - thickness, x - adjustedGap, y + thickness, 2.0F, outlineColour, baseColour, isAdaptiveColourEnabled);
            this.renderManager.drawBorderedRectangle(graphics, x + adjustedGap, y - thickness, x + adjustedGap + adjustedWidth, y + thickness, 2.0F, outlineColour, baseColour, isAdaptiveColourEnabled);
        }
        else {
            var adjustedThickness = thickness - 0.5F;

            this.renderManager.drawFilledRectangle(graphics, x - adjustedThickness, y - gap - height, x + adjustedThickness, y - gap, baseColour, isAdaptiveColourEnabled);
            this.renderManager.drawFilledRectangle(graphics, x - adjustedThickness, y + gap, x + adjustedThickness, y + gap + height, baseColour, isAdaptiveColourEnabled);
            this.renderManager.drawFilledRectangle(graphics, x - gap - width, y - adjustedThickness, x - gap, y + adjustedThickness, baseColour, isAdaptiveColourEnabled);
            this.renderManager.drawFilledRectangle(graphics, x + gap, y - adjustedThickness, x + gap + width, y + adjustedThickness, baseColour, isAdaptiveColourEnabled);
        }
    }
}