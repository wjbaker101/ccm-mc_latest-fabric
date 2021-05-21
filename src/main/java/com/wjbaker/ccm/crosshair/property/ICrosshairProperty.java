package com.wjbaker.ccm.crosshair.property;

public abstract class ICrosshairProperty<T> {

    private final String alias;

    private T value;

    public ICrosshairProperty(final String alias, final T value) {
        this.alias = alias;
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(final T value) {
        this.value = value;
    }

    public String alias() {
        return this.alias;
    }

    public abstract String forConfig();
    public abstract void setFromConfig(final String value);
}
