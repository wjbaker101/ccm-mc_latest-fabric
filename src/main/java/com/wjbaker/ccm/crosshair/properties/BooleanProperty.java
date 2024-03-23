package com.wjbaker.ccm.crosshair.properties;

import com.wjbaker.ccm.crosshair.types.ICrosshairProperty;

public final class BooleanProperty extends ICrosshairProperty<Boolean> {

    public BooleanProperty(final String alias, final Boolean value) {
        super(alias, value);
    }

    @Override
    public String forConfig() {
        return this.get().toString();
    }

    @Override
    public void setFromConfig(final String value) {
        this.set(Boolean.valueOf(value));
    }
}
