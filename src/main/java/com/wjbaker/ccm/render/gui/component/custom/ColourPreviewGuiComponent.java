package com.wjbaker.ccm.render.gui.component.custom;

import com.wjbaker.ccm.crosshair.property.RGBAProperty;
import com.wjbaker.ccm.render.gui.component.GuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class ColourPreviewGuiComponent extends GuiComponent {

    private final RGBAProperty colour;

    public ColourPreviewGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final RGBAProperty colour) {

        super(parentGuiScreen, x, y, 35, 35);

        this.colour = colour;
    }

    @Override
    public void draw() {
        super.draw();

        this.renderManager.drawFilledRectangle(this.x, this.y, this.x + this.width, this.y + this.height, this.colour.get());
    }
}
