package com.wjbaker.ccm.render.gui.component;

import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.type.RGBA;

public abstract class GuiComponentTheme {

    protected RGBA baseBackgroundColour;
    protected RGBA hoverBackgroundColour;
    protected RGBA currentBackgroundColour;

    protected RGBA baseBorderColour;
    protected RGBA hoverBorderColour;
    protected RGBA currentBorderColour;

    protected RGBA baseTextColour;
    protected RGBA hoverTextColour;
    protected RGBA currentTextColour;

    public GuiComponentTheme() {
        this.baseBackgroundColour = ModTheme.PRIMARY;
        this.hoverBackgroundColour = ModTheme.SECONDARY;
        this.currentBackgroundColour = this.baseBackgroundColour;

        this.baseBorderColour = ModTheme.DARK_GREY;
        this.hoverBorderColour = this.baseBorderColour;
        this.currentBorderColour = this.baseBorderColour;

        this.baseTextColour = ModTheme.WHITE;
        this.hoverTextColour = this.baseTextColour;
        this.currentTextColour = this.baseTextColour;
    }

    public void setBaseBackgroundColour(final RGBA baseBackgroundColour) {
        this.baseBackgroundColour = baseBackgroundColour;
        this.currentBackgroundColour = this.baseBackgroundColour;
    }

    public void setHoverBackgroundColour(final RGBA hoverBackgroundColour) {
        this.hoverBackgroundColour = hoverBackgroundColour;
    }

    public void setBaseBorderColour(final RGBA baseBorderColour) {
        this.baseBorderColour = baseBorderColour;
        this.currentBorderColour = this.baseBorderColour;
    }

    public void setHoverBorderColour(final RGBA hoverBorderColour) {
        this.hoverBorderColour = hoverBorderColour;
    }

    public void setBaseTextColour(final RGBA baseTextColour) {
        this.baseTextColour = baseTextColour;
        this.currentTextColour = this.baseTextColour;
    }

    public void setHoverTextColour(final RGBA hoverTextColour) {
        this.hoverTextColour = hoverTextColour;
    }
}
