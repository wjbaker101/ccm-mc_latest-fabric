package com.wjbaker.ccm.render.type;

public final class GuiBounds {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public GuiBounds(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }
}
