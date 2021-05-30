package com.wjbaker.ccm.render.gui.component.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.property.RGBAProperty;
import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.render.gui.component.GuiComponent;
import com.wjbaker.ccm.render.gui.component.type.IBindableGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;
import com.wjbaker.ccm.render.gui.screen.screens.editColour.EditColourGuiScreen;
import net.minecraft.client.MinecraftClient;

public final class ColourPickerGuiComponent extends GuiComponent implements IBindableGuiComponent<RGBAProperty> {

    private final int labelSpacing;
    private final int boxSize;
    private final String label;
    private final CustomCrosshair crosshair;

    private RGBAProperty colour;
    private boolean isMouseOverBox;

    public ColourPickerGuiComponent(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final String label) {

        super(parentGuiScreen, x, y, -1, 25);
        this.crosshair = crosshair;

        this.labelSpacing = 3;
        this.boxSize = 25;
        this.label = label;

        this.colour = new RGBAProperty("fake_colour", ModTheme.WHITE);
        this.isMouseOverBox = false;

        this.width = this.boxSize + this.labelSpacing + this.renderManager.textWidth(this.label);
    }

    @Override
    public void draw() {
        super.draw();

        this.renderManager.drawBorderedRectangle(
            this.x, this.y,
            this.x + this.boxSize, this.y + this.boxSize,
            2.0F,
            this.currentBorderColour,
            this.currentBackgroundColour);

        this.renderManager.drawFilledRectangle(
            this.x + 2, this.y + 2,
            this.x + this.boxSize - 2, this.y + this.boxSize - 2,
            this.colour.get());

        this.renderManager.drawText(
            this.label,
            this.x + this.boxSize + this.labelSpacing,
            this.y + (this.boxSize / 2) - 3,
            ModTheme.WHITE,
            false);
    }

    @Override
    public void onMouseMove(final int mouseX, final int mouseY) {
        super.onMouseMove(mouseX, mouseY);

        if (this.isInsideBox(mouseX, mouseY))
            this.currentBackgroundColour = this.hoverBackgroundColour;
        else
            this.currentBackgroundColour = this.baseBackgroundColour;
    }

    @Override
    public void onMouseDown(final int mouseX, final int mouseY, final int button) {
        super.onMouseDown(mouseX, mouseY, button);

        this.isMouseDownInside = this.isInsideBox(mouseX, mouseY);
    }

    @Override
    public void onMouseUp(final int mouseX, final int mouseY, final int button) {
        super.onMouseUp(mouseX, mouseY, button);

        if (!this.isMouseDownInside)
            return;

        MinecraftClient.getInstance().openScreen(new EditColourGuiScreen(this.crosshair, this.colour));

        this.isMouseDownInside = false;
    }

    private boolean isInsideBox(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX <= this.x + this.boxSize
            && mouseY > this.y && mouseY <= this.y + this.boxSize;
    }

    @Override
    public void bind(final RGBAProperty property) {
        this.colour = property;
    }
}
