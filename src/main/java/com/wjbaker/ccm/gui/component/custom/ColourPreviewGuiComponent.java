package com.wjbaker.ccm.gui.component.custom;

import com.wjbaker.ccm.crosshair.properties.RgbaProperty;
import com.wjbaker.ccm.gui.component.GuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import net.minecraft.client.gui.DrawContext;

public final class ColourPreviewGuiComponent extends GuiComponent {

    private final RgbaProperty colour;

    public ColourPreviewGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final RgbaProperty colour) {

        super(parentGuiScreen, x, y, 35, 35);

        this.colour = colour;
    }

    @Override
    public void draw(final DrawContext drawContext) {
        super.draw(drawContext);

        var matrixStack = drawContext.getMatrices();

        this.renderManager.drawFilledRectangle(matrixStack, this.x, this.y, this.x + this.width, this.y + this.height, this.colour.get());
    }
}