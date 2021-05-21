package com.wjbaker.ccm.crosshair.style;

import com.wjbaker.ccm.crosshair.render.ComputedProperties;

public interface ICrosshairStyle {

    void draw(final int x, final int y, final ComputedProperties computedProperties);
}
