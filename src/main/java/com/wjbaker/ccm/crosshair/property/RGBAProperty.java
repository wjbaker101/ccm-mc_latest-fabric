package com.wjbaker.ccm.crosshair.property;

import com.wjbaker.ccm.type.RGBA;

public final class RGBAProperty extends ICrosshairProperty<RGBA> {

    public RGBAProperty(final String alias, final RGBA value) {
        super(alias, value);
    }

    @Override
    public String forConfig() {
        return this.get().toString();
    }

    @Override
    public void setFromConfig(final String value) {
        String[] values = value.split("/");

        if (values.length != 4)
            this.set(new RGBA(0, 0, 0, 0));

        int red = Integer.parseInt(values[0]);
        int green = Integer.parseInt(values[1]);
        int blue = Integer.parseInt(values[2]);
        int opacity = Integer.parseInt(values[3]);

        this.set(new RGBA(red, green, blue, opacity));
    }
}
