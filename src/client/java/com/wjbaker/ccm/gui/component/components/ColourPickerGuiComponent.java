package com.wjbaker.ccm.gui.component.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.properties.RgbaProperty;
import com.wjbaker.ccm.gui.component.GuiComponent;
import com.wjbaker.ccm.gui.component.type.IBindableGuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import com.wjbaker.ccm.gui.screen.screens.editColour.EditColourGuiScreen;
import com.wjbaker.ccm.rendering.ModTheme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public final class ColourPickerGuiComponent extends GuiComponent implements IBindableGuiComponent<RgbaProperty> {

    private final int labelSpacing;
    private final int boxSize;
    private final String label;
    private final CustomCrosshair crosshair;

    private RgbaProperty colour;

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

        this.colour = new RgbaProperty("fake_colour", ModTheme.WHITE);

        this.width = this.boxSize + this.labelSpacing + this.renderManager.textWidth(this.label);
    }

    @Override
    public void draw(final DrawContext drawContext) {
        super.draw(drawContext);

        var matrixStack = drawContext.getMatrices();

        this.renderManager.drawBorderedRectangle(
            matrixStack,
            this.x, this.y,
            this.x + this.boxSize, this.y + this.boxSize,
            2.0F,
            this.currentBorderColour,
            this.currentBackgroundColour);

        this.renderManager.drawFilledRectangle(
            matrixStack,
            this.x + 2, this.y + 2,
            this.x + this.boxSize - 2, this.y + this.boxSize - 2,
            this.colour.get());

        this.renderManager.drawText(
            drawContext,
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

        MinecraftClient.getInstance().setScreen(new EditColourGuiScreen(this.crosshair, this.colour));

        this.isMouseDownInside = false;
    }

    private boolean isInsideBox(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX <= this.x + this.boxSize
            && mouseY > this.y && mouseY <= this.y + this.boxSize;
    }

    @Override
    public void bind(final RgbaProperty property) {
        this.colour = property;
    }
}