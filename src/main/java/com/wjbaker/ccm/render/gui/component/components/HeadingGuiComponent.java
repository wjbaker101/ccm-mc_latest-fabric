package com.wjbaker.ccm.render.gui.component.components;

import com.wjbaker.ccm.render.gui.component.GuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class HeadingGuiComponent extends GuiComponent {

    private final String label;

    public HeadingGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final String label) {

        super(parentGuiScreen, x, y, -1, -1);

        this.label = label;

        this.width = this.renderManager.textWidth(this.label) * 2;
        this.height = 12;
    }

    @Override
    public void draw() {
        super.draw();

        this.renderManager.drawBigText(this.label, this.x, this.y, this.currentTextColour, true);
    }
}
