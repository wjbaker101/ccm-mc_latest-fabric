package com.wjbaker.ccm.crosshair.properties;

import com.wjbaker.ccm.crosshair.style.CrosshairStyle;
import com.wjbaker.ccm.crosshair.types.ICrosshairProperty;

public final class CrosshairStyleProperty extends ICrosshairProperty<CrosshairStyle> {

    public CrosshairStyleProperty(final String alias, final CrosshairStyle value) {
        super(alias, value);
    }

    @Override
    public String forConfig() {
        return String.valueOf(this.get().getOrdinal());
    }

    @Override
    public void setFromConfig(String value) {
        this.set(CrosshairStyle.fromOrdinal(Integer.parseInt(value)));
    }
}
