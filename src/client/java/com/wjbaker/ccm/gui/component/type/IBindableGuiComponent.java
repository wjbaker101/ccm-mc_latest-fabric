package com.wjbaker.ccm.gui.component.type;

import com.wjbaker.ccm.crosshair.types.ICrosshairProperty;

public interface IBindableGuiComponent<TProperty extends ICrosshairProperty<?>> {

    void bind(final TProperty property);
}
