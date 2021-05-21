package com.wjbaker.ccm.render.gui.component.type;

import com.wjbaker.ccm.crosshair.property.ICrosshairProperty;

public interface IBindableGuiComponent<TProperty extends ICrosshairProperty<?>> {

    void bind(final TProperty property);
}
