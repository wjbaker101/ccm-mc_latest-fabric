package com.wjbaker.ccm.crosshair.style;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.style.styles.*;
import net.minecraft.client.util.math.MatrixStack;

public final class CrosshairStyleFactory {

    public ICrosshairStyle from(final MatrixStack matrixStack, final CrosshairStyle style, final CustomCrosshair crosshair) {
        switch (style) {
            case DEFAULT: return new DefaultStyle(matrixStack, crosshair);
            case CIRCLE: return new CircleStyle(matrixStack, crosshair);
            case SQUARE: return new SquareStyle(matrixStack, crosshair);
            case TRIANGLE: return new TriangleStyle(matrixStack, crosshair);
            case ARROW: return new ArrowStyle(matrixStack, crosshair);

            default: return new CrossStyle(matrixStack, crosshair);
        }
    }
}
