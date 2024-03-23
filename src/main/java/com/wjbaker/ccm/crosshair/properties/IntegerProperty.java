package com.wjbaker.ccm.crosshair.properties;

public final class IntegerProperty extends ICrosshairProperty<Integer> {

    public IntegerProperty(final String alias, final Integer value) {
        super(alias, value);
    }

    @Override
    public String forConfig() {
        return this.get().toString();
    }

    @Override
    public void setFromConfig(final String value) {
        this.set(Integer.parseInt(value));
    }
}
