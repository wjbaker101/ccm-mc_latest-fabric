package com.wjbaker.ccm.crosshair.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Matrix3x2fStack;

public final class SquareStyle extends CrosshairStyle {

    public SquareStyle(final Matrix3x2fStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final GuiGraphicsExtractor graphics, final int x, final int y, final ComputedProperties computedProperties) {
        var isOutlineEnabled = this.crosshair.isOutlineEnabled.get();
        var width = this.crosshair.width.get();
        var height = this.crosshair.height.get();
        var thickness = this.crosshair.thickness.get();
        var gap = computedProperties.gap();
        var colour = computedProperties.colour();
        var isAdaptiveColourEnabled = this.crosshair.isAdaptiveColourEnabled.get();

        if (isOutlineEnabled) {
            var outlineColour = this.crosshair.outlineColour.get();

            // Inner
            this.renderManager.drawRectangle(
                graphics,
                x - width - gap + 0.5F, y - height - gap + 0.5F,
                x + width + gap - 0.5F, y + height + gap - 0.5F,
                2.0F,
                outlineColour);

            // Outer
            this.renderManager.drawRectangle(
                graphics,
                x - width - thickness - gap - 0.5F, y - height - thickness - gap - 0.5F,
                x + width + thickness + gap + 0.5F, y + height + thickness + gap + 0.5F,
                2.0F,
                outlineColour);
        }

        // Top
        this.renderManager.drawFilledRectangle(
            graphics,
            x - width - thickness - gap, y - height - thickness - gap,
            x + width + thickness + gap, y - height - gap,
            colour,
            isAdaptiveColourEnabled);

        // Bottom
        this.renderManager.drawFilledRectangle(
            graphics,
            x - width - thickness - gap, y + height + gap,
            x + width + thickness + gap, y + height + thickness + gap,
            colour,
            isAdaptiveColourEnabled);

        // Left
        this.renderManager.drawFilledRectangle(
            graphics,
            x - width - thickness - gap, y - gap - height,
            x - width - gap, y + gap + height,
            colour,
            isAdaptiveColourEnabled);

        // Right
        this.renderManager.drawFilledRectangle(
            graphics,
            x + width + gap, y - gap - height,
            x + width + thickness + gap, y + gap + height,
            colour,
            isAdaptiveColourEnabled);
    }
}