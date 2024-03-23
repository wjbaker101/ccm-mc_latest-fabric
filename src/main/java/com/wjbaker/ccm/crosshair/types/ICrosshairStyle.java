package com.wjbaker.ccm.crosshair.types;

import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import net.minecraft.client.gui.DrawContext;

public interface ICrosshairStyle {

    void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties);
}