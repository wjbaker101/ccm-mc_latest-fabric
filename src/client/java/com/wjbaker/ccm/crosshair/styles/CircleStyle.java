package com.wjbaker.ccm.crosshair.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Matrix3x2fStack;

public final class CircleStyle extends CrosshairStyle {

    public CircleStyle(final Matrix3x2fStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final GuiGraphicsExtractor graphics, final int x, final int y, final ComputedProperties computedProperties) {
        this.renderManager.drawCircle(graphics, x, y, computedProperties.gap(), 2.0F, computedProperties.colour());
    }
}