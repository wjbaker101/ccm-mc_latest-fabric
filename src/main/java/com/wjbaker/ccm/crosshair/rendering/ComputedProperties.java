package com.wjbaker.ccm.crosshair.rendering;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.rendering.computed.ComputeColour;
import com.wjbaker.ccm.crosshair.rendering.computed.ComputeGap;
import com.wjbaker.ccm.crosshair.rendering.computed.ComputeIndicators;
import com.wjbaker.ccm.crosshair.rendering.computed.ComputeVisibility;
import com.wjbaker.ccm.type.RGBA;

import java.util.List;

public final class ComputedProperties {

    private final CustomCrosshair crosshair;

    private final int gap;
    private final RGBA colour;
    private final boolean isVisible;

    public ComputedProperties(final CustomCrosshair crosshair) {
        this.crosshair = crosshair;

        this.gap = ComputeGap.compute(crosshair);
        this.colour = ComputeColour.compute(crosshair);
        this.isVisible = ComputeVisibility.compute(crosshair);
    }

    public int gap() {
        return this.gap;
    }

    public RGBA colour() {
        return this.colour;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public List<ComputeIndicators.IndicatorItem> getIndicatorItems() {
        return ComputeIndicators.compute(this.crosshair);
    }
}