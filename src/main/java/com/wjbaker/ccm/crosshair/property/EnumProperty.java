package com.wjbaker.ccm.crosshair.property;

import java.util.EnumSet;

public final class EnumProperty<TEnum extends Enum<TEnum>> extends ICrosshairProperty<TEnum> {

    public EnumProperty(final String alias, final TEnum value) {
        super(alias, value);
    }

    @Override
    public String forConfig() {
        return String.valueOf(this.get().ordinal());
    }

    @Override
    public void setFromConfig(final String value) {
        int ordinal = Integer.parseInt(value);

        this.set(this.enumOf(ordinal));
    }

    public void setFromOrdinal(final int value) {
        this.set(this.enumOf(value));
    }

    @SuppressWarnings("unchecked")
    private TEnum enumOf(final int ordinal) {
        Class<TEnum> enumClass = (Class<TEnum>)this.get().getClass();

        return EnumSet.allOf(enumClass).stream()
            .filter(x -> x.ordinal() == ordinal)
            .findFirst()
            .orElse(null);
    }
}
