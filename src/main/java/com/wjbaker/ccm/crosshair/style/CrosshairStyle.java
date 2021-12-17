package com.wjbaker.ccm.crosshair.style;

public enum CrosshairStyle {

    DEFAULT(0, "Default"),
    CROSS(1, "Cross"),
    CIRCLE(2, "Circle"),
    SQUARE(3, "Square"),
    TRIANGLE(4, "Triangle"),
    ARROW(5, "Arrow"),
    DEBUG(6, "Debug"),

    ;

    private static final CrosshairStyle[] values = values();

    private final int ordinal;
    private final String name;

    CrosshairStyle(final int ordinal, final String name) {
        this.ordinal = ordinal;
        this.name = name;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    public String getName() {
        return this.name;
    }

    public static CrosshairStyle fromOrdinal(final int ordinal) {
        for (CrosshairStyle value : values) {
            if (value.getOrdinal() == ordinal)
                return value;
        }

        return CROSS;
    }
}
