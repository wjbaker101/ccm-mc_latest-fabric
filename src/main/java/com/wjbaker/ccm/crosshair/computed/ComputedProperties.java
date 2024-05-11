package com.wjbaker.ccm.crosshair.computed;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.properties.ComputeColour;
import com.wjbaker.ccm.crosshair.computed.properties.ComputeGap;
import com.wjbaker.ccm.crosshair.computed.properties.ComputeIndicators;
import com.wjbaker.ccm.crosshair.computed.properties.ComputeVisibility;
import com.wjbaker.ccm.rendering.types.RGBA;

import java.util.List;

public final class ComputedProperties {

    private final int gap;
    private final RGBA colour;
    private final boolean isVisible;
    private final List<ComputeIndicators.IndicatorItem> indicatorItems;

    public ComputedProperties(final CustomCrosshair crosshair) {
        this.gap = ComputeGap.compute(crosshair);
        this.colour = ComputeColour.compute(crosshair);
        this.isVisible = ComputeVisibility.compute(crosshair);
        this.indicatorItems = ComputeIndicators.compute(crosshair);
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

    public List<ComputeIndicators.IndicatorItem> indicatorItems() {
        return this.indicatorItems;
    }
}