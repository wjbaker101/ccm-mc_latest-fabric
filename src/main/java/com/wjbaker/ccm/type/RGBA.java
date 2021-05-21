package com.wjbaker.ccm.type;

public final class RGBA {

    private final int red;
    private final int green;
    private final int blue;
    private final int opacity;

    public RGBA(final int red, final int green, final int blue, final int opacity) {
        this.red = this.checkBounds(red);
        this.green = this.checkBounds(green);
        this.blue = this.checkBounds(blue);
        this.opacity = this.checkBounds(opacity);
    }

    private int checkBounds(final int value) {
        return Math.max(0, Math.min(255, value));
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d/%d", this.red, this.green, this.blue, this.opacity);
    }

    public int getRed() {
        return this.red;
    }

    public RGBA setRed(final int value) {
        return new RGBA(value, this.green, this.blue, this.opacity);
    }

    public int getGreen() {
        return this.green;
    }

    public RGBA setGreen(final int value) {
        return new RGBA(this.red, value, this.blue, this.opacity);
    }

    public int getBlue() {
        return this.blue;
    }

    public RGBA setBlue(final int value) {
        return new RGBA(this.red, this.green, value, this.opacity);
    }

    public int getOpacity() {
        return this.opacity;
    }

    public RGBA setOpacity(final int value) {
        return new RGBA(this.red, this.green, this.blue, value);
    }
}
