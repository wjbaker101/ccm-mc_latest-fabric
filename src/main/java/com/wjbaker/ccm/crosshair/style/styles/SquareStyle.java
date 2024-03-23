package com.wjbaker.ccm.crosshair.style.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.BaseCrosshairStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public final class SquareStyle extends BaseCrosshairStyle {

    public SquareStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
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
                this.matrixStack,
                x - width - gap + 0.5F, y - height - gap + 0.5F,
                x + width + gap - 0.5F, y + height + gap - 0.5F,
                2.0F,
                outlineColour);

            // Outer
            this.renderManager.drawRectangle(
                this.matrixStack,
                x - width - thickness - gap - 0.5F, y - height - thickness - gap - 0.5F,
                x + width + thickness + gap + 0.5F, y + height + thickness + gap + 0.5F,
                2.0F,
                outlineColour);
        }

        // Top
        this.renderManager.drawFilledRectangle(
            this.matrixStack,
            x - width - thickness - gap, y - height - thickness - gap,
            x + width + thickness + gap, y - height - gap,
            colour,
            isAdaptiveColourEnabled);

        // Bottom
        this.renderManager.drawFilledRectangle(
            this.matrixStack,
            x - width - thickness - gap, y + height + gap,
            x + width + thickness + gap, y + height + thickness + gap,
            colour,
            isAdaptiveColourEnabled);

        // Left
        this.renderManager.drawFilledRectangle(
            this.matrixStack,
            x - width - thickness - gap, y - gap - height,
            x - width - gap, y + gap + height,
            colour,
            isAdaptiveColourEnabled);

        // Right
        this.renderManager.drawFilledRectangle(
            this.matrixStack,
            x + width + gap, y - gap - height,
            x + width + thickness + gap, y + gap + height,
            colour,
            isAdaptiveColourEnabled);
    }
}
