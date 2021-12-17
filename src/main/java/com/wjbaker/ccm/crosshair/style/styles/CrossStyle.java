package com.wjbaker.ccm.crosshair.style.styles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import net.minecraft.client.util.math.MatrixStack;

public final class CrossStyle extends AbstractCrosshairStyle {

    public CrossStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        RenderSystem.enableBlend();

        var isOutlineEnabled = this.crosshair.isOutlineEnabled.get();
        var baseColour = computedProperties.colour();
        var gap = computedProperties.gap();
        var thickness = this.crosshair.thickness.get();
        var width = this.crosshair.width.get();
        var height  = this.crosshair.height.get();

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

            this.renderManager.drawBorderedRectangle(this.matrixStack, x - thickness, y - adjustedGap - adjustedHeight, x + thickness, y - adjustedGap, 2.0F, outlineColour, baseColour);
            this.renderManager.drawBorderedRectangle(this.matrixStack, x - thickness, y + adjustedGap, x + thickness, y + adjustedGap + adjustedHeight, 2.0F, outlineColour, baseColour);
            this.renderManager.drawBorderedRectangle(this.matrixStack, x - adjustedGap - adjustedWidth, y - thickness, x - adjustedGap, y + thickness, 2.0F, outlineColour, baseColour);
            this.renderManager.drawBorderedRectangle(this.matrixStack, x + adjustedGap, y - thickness, x + adjustedGap + adjustedWidth, y + thickness, 2.0F, outlineColour, baseColour);
        }
        else {
            var adjustedThickness = thickness - 0.5F;

            this.renderManager.drawFilledRectangle(this.matrixStack, x - adjustedThickness, y - gap - height, x + adjustedThickness, y - gap, baseColour);
            this.renderManager.drawFilledRectangle(this.matrixStack, x - adjustedThickness, y + gap, x + adjustedThickness, y + gap + height, baseColour);
            this.renderManager.drawFilledRectangle(this.matrixStack, x - gap - width, y - adjustedThickness, x - gap, y + adjustedThickness, baseColour);
            this.renderManager.drawFilledRectangle(this.matrixStack, x + gap, y - adjustedThickness, x + gap + width, y + adjustedThickness, baseColour);
        }
    }
}
