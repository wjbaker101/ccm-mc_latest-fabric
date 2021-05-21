package com.wjbaker.ccm.crosshair.style;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.RenderManager;

public abstract class AbstractCrosshairStyle implements ICrosshairStyle {

    protected final CustomCrosshair crosshair;
    protected final RenderManager renderManager;

    public AbstractCrosshairStyle(final CustomCrosshair crosshair) {
        this.crosshair = crosshair;
        this.renderManager = new RenderManager();
    }
}
