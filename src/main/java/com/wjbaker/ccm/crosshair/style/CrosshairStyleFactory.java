package com.wjbaker.ccm.crosshair.style;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.style.styles.*;

public final class CrosshairStyleFactory {

    public ICrosshairStyle from(final CrosshairStyle style, final CustomCrosshair crosshair) {
        switch (style) {
            case DEFAULT: return new DefaultStyle(crosshair);
            case CIRCLE: return new CircleStyle(crosshair);
            case SQUARE: return new SquareStyle(crosshair);
            case TRIANGLE: return new TriangleStyle(crosshair);
            case ARROW: return new ArrowStyle(crosshair);

            default: return new CrossStyle(crosshair);
        }
    }
}
