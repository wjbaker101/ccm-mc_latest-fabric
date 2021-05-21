package com.wjbaker.ccm.crosshair.style.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import com.wjbaker.ccm.type.RGBA;
import net.minecraft.client.MinecraftClient;

public final class CrossStyle extends AbstractCrosshairStyle {

    public CrossStyle(final CustomCrosshair crosshair) {
        super(crosshair);
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        boolean isOutlineEnabled = this.crosshair.isOutlineEnabled.get();

        if (isOutlineEnabled) {
            this.drawOutline(x, y, computedProperties);
        }

        this.drawBars(x, y, computedProperties);
    }

    private void drawOutline(final int x, final int y, final ComputedProperties computedProperties) {
        RGBA outlineColour = this.crosshair.outlineColour.get();
        float thickness = this.crosshair.thickness.get() - 0.5F;
        int width = this.crosshair.width.get();
        int height  = this.crosshair.height.get();
        int gap = computedProperties.gap();

        double guiScale = MinecraftClient.getInstance().getWindow().getScaleFactor();
        float offsetA = guiScale > 2 ? 0.25F : 0.0F;
        float offsetB = guiScale == 4 ? 0.25F : 0.0F;
        float offsetC = guiScale == 3 && thickness > 1 ? 0.25F : 0.0F;

        float[] top = new float[] {
            x - thickness - 0.5F + offsetB + offsetC, y - gap - height - 0.5F + offsetA - offsetC,
            x - thickness - 0.5F + offsetB + offsetC, y - gap + 0.5F - offsetB - offsetC,

            x - thickness - 0.5F + offsetB + offsetC, y - gap + 0.5F - offsetB,
            x + thickness + 0.5F - offsetA, y - gap + 0.5F - offsetB - offsetC,

            x + thickness + 0.5F - offsetA, y - gap + 0.5F - offsetB - offsetC,
            x + thickness + 0.5F - offsetA, y - gap - height - 0.5F + offsetA,

            x + thickness + 0.5F - offsetA, y - gap - height - 0.5F + offsetA,
            x - thickness - 0.5F + offsetB + offsetC, y - gap - height - 0.5F + offsetA
        };

        float[] bottom = new float[] {
            x - thickness - 0.5F + offsetB + offsetC, y + gap + height + 0.5F - offsetB - offsetC,
            x - thickness - 0.5F + offsetB + offsetC, y + gap - 0.5F + offsetA,

            x - thickness - 0.5F + offsetB + offsetC, y + gap - 0.5F + offsetA,
            x + thickness + 0.5F - offsetA, y + gap - 0.5F + offsetA,

            x + thickness + 0.5F - offsetA, y + gap - 0.5F + offsetA,
            x + thickness + 0.5F - offsetA, y + gap + height + 0.5F - offsetB - offsetC,

            x + thickness + 0.5F - offsetA, y + gap + height + 0.5F - offsetB - offsetC,
            x - thickness - 0.5F + offsetB + offsetC, y + gap + height + 0.5F - offsetB - offsetC
        };

        float[] right = new float[] {
            x + gap + width + 0.5F - offsetA, y - thickness - 0.5F + offsetA,
            x + gap + width + 0.5F - offsetA, y + thickness + 0.5F - offsetB - offsetC,

            x + gap + width + 0.5F - offsetA, y + thickness + 0.5F - offsetB - offsetC,
            x + gap - 0.5F + offsetB + offsetC, y + thickness + 0.5F - offsetB - offsetC,

            x + gap - 0.5F + offsetB + offsetC, y + thickness + 0.5F - offsetB - offsetC,
            x + gap - 0.5F + offsetB + offsetC, y - thickness - 0.5F + offsetA,

            x + gap - 0.5F + offsetB + offsetC, y - thickness - 0.5F + offsetA,
            x + gap + width + 0.5F - offsetA, y - thickness - 0.5F + offsetA
        };

        float[] left = new float[] {
            x - gap - width - 0.5F + offsetA, y - thickness - 0.5F + offsetA,
            x - gap - width - 0.5F + offsetA, y + thickness + 0.5F - offsetB - offsetC,

            x - gap - width - 0.5F + offsetA, y + thickness + 0.5F - offsetB - offsetC,
            x - gap + 0.5F - offsetB, y + thickness + 0.5F - offsetB,

            x - gap + 0.5F - offsetB, y + thickness + 0.5F - offsetB,
            x - gap + 0.5F - offsetB, y - thickness - 0.5F + offsetA - offsetC,

            x - gap + 0.5F - offsetB, y - thickness - 0.5F + offsetA - offsetC,
            x - gap - width - 0.5F + offsetA, y - thickness - 0.5F + offsetA
        };

        this.renderManager.drawLines(top, 2.0F, outlineColour);
        this.renderManager.drawLines(bottom, 2.0F, outlineColour);
        this.renderManager.drawLines(right, 2.0F, outlineColour);
        this.renderManager.drawLines(left, 2.0F, outlineColour);
    }

    public void drawBars(final int x, final int y, final ComputedProperties computedProperties) {
        RGBA colour = computedProperties.colour();
        int gap = computedProperties.gap();
        float thickness = this.crosshair.thickness.get() - 0.5F;
        int width = this.crosshair.width.get();
        int height  = this.crosshair.height.get();

        // Left
        // Bottom
        // Right
        // Left
        this.renderManager.drawFilledRectangle(x - thickness, y - gap - height, x + thickness, y - gap, colour);
        this.renderManager.drawFilledRectangle(x - thickness, y + gap, x + thickness, y + gap + height, colour);
        this.renderManager.drawFilledRectangle(x - gap - width, y - thickness, x - gap, y + thickness, colour);
        this.renderManager.drawFilledRectangle(x + gap, y - thickness, x + gap + width, y + thickness, colour);
    }
}
