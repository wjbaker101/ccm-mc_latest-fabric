package com.wjbaker.ccm.render.gui.component.components;

import com.wjbaker.ccm.crosshair.property.BooleanProperty;
import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.render.gui.component.GuiComponent;
import com.wjbaker.ccm.render.gui.component.type.IBindableGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class CheckBoxGuiComponent extends GuiComponent implements IBindableGuiComponent<BooleanProperty> {

    private final int boxSize;
    private final int labelSpacing;
    private final String label;

    private boolean isMouseOverBox;

    private BooleanProperty isChecked;

    public CheckBoxGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final String label,
        final boolean defaultValue) {

        super(parentGuiScreen, x, y, -1, -1);

        this.boxSize = 10;
        this.labelSpacing = 5;
        this.label = label;

        this.isMouseOverBox = false;

        this.isChecked = new BooleanProperty("fake_is_checked", defaultValue);

        this.width = this.boxSize + this.labelSpacing + this.renderManager.textWidth(this.label);
        this.height = this.boxSize;
    }

    @Override
    public void draw() {
        super.draw();

        this.renderManager.drawBorderedRectangle(
            this.x, this.y,
            this.x + this.boxSize, this.y + this.boxSize,
            2.0F,
            ModTheme.DARK_GREY,
            this.currentBackgroundColour);

        float inset = 1.5F;

        if (this.isChecked.get()) {
            this.renderManager.drawFilledRectangle(
                this.x + inset, this.y + inset,
                this.x + this.boxSize - inset, this.y + this.boxSize - inset,
                ModTheme.SUCCESS);
        }

        this.renderManager.drawText(
            this.label,
            this.x + this.boxSize + this.labelSpacing,
            this.y + (this.boxSize / 2) - 3,
            this.currentTextColour,
            false);
    }

    @Override
    public void onMouseMove(final int mouseX, final int mouseY) {
        super.onMouseMove(mouseX, mouseY);

        this.isMouseOverBox = this.isOverBox(mouseX, mouseY);

        if (this.isMouseOverBox)
            this.currentBackgroundColour = this.hoverBackgroundColour;
        else
            this.currentBackgroundColour = this.baseBackgroundColour;
    }

    @Override
    public void onMouseUp(final int mouseX, final int mouseY, final int button) {
        super.onMouseUp(mouseX, mouseY, button);

        if (this.isOverBox(mouseX, mouseY))
            this.isChecked.set(!this.isChecked.get());
    }

    private boolean isOverBox(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX <= this.x + this.boxSize
            && mouseY > this.y && mouseY <= this.y + this.boxSize;
    }

    @Override
    public void bind(final BooleanProperty property) {
        this.isChecked = property;
    }
}
