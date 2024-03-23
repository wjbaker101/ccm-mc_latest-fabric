package com.wjbaker.ccm.crosshair.property;

import com.wjbaker.ccm.type.RGBA;

public final class RgbaProperty extends ICrosshairProperty<RGBA> {

    public RgbaProperty(final String alias, final RGBA value) {
        super(alias, value);
    }

    @Override
    public String forConfig() {
        return this.get().toString();
    }

    @Override
    public void setFromConfig(final String value) {
        var values = value.split("/");

        if (values.length != 4)
            this.set(new RGBA(0, 0, 0, 0));

        var red = Integer.parseInt(values[0]);
        var green = Integer.parseInt(values[1]);
        var blue = Integer.parseInt(values[2]);
        var opacity = Integer.parseInt(values[3]);

        this.set(new RGBA(red, green, blue, opacity));
    }
}
