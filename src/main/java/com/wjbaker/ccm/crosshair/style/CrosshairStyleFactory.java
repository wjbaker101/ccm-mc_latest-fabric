package com.wjbaker.ccm.crosshair.style;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.style.styles.*;
import net.minecraft.client.util.math.MatrixStack;

public final class CrosshairStyleFactory {

    public ICrosshairStyle from(final MatrixStack matrixStack, final CrosshairStyle style, final CustomCrosshair crosshair) {
        return switch (style) {
            case DEFAULT -> new DefaultStyle(matrixStack, crosshair);
            case CIRCLE -> new CircleStyle(matrixStack, crosshair);
            case SQUARE -> new SquareStyle(matrixStack, crosshair);
            case TRIANGLE -> new TriangleStyle(matrixStack, crosshair);
            case ARROW -> new ArrowStyle(matrixStack, crosshair);
            case DEBUG -> new DebugStyle(matrixStack, crosshair);
            case DRAWN -> new DrawnStyle(matrixStack, crosshair);

            default -> new CrossStyle(matrixStack, crosshair);
        };
    }
}