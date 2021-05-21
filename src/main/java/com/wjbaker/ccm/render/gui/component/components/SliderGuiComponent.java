package com.wjbaker.ccm.render.gui.component.components;

import com.wjbaker.ccm.render.gui.component.GuiComponent;
import com.wjbaker.ccm.render.gui.component.event.IGuiComponentEvent;
import com.wjbaker.ccm.render.gui.component.event.IOnValueChangedEvent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;
import com.wjbaker.ccm.type.RGBA;

public abstract class SliderGuiComponent extends GuiComponent {

    protected final int thumbSize;
    protected final int titleSpacing;

    protected final String label;
    protected final int minValue;
    protected final int maxValue;
    protected final int defaultValue;

    protected int thumbPosition;
    protected int grabOffset;
    protected boolean isDragging;

    private RGBA baseThumbColour;
    private RGBA hoverThumbColour;
    private RGBA currentThumbColour;

    public SliderGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final String label,
        final int minValue,
        final int maxValue,
        final int defaultValue) {

        super(parentGuiScreen, x, y, width, 25);

        this.label = label;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;

        this.thumbPosition = 0;
        this.grabOffset = 0;
        this.thumbSize = 11;
        this.titleSpacing = 3;

        this.baseThumbColour = this.baseBackgroundColour;
        this.hoverThumbColour = this.hoverBackgroundColour;
        this.currentThumbColour = this.baseBackgroundColour;
    }

    @Override
    public void draw() {
        super.draw();

        this.renderManager.drawText(this.label, this.x, this.y, this.currentTextColour, false);

        int posY = this.y + 8 + this.titleSpacing;

        this.renderManager.drawBorderedRectangle(
            this.x, posY + (this.thumbSize / 2.0F) - 2,
            this.x + this.width, posY + (this.thumbSize / 2.0F) - 1 + 3,
            2.0F,
            this.baseBorderColour,
            this.baseBackgroundColour);

        this.renderManager.drawBorderedRectangle(
            this.x + this.thumbPosition, posY,
            this.x + this.thumbPosition + this.thumbSize, posY + this.thumbSize,
            2.0F,
            this.currentBorderColour,
            this.currentThumbColour);

        this.drawLabel();
    }

    @Override
    public void onMouseDown(final int mouseX, final int mouseY, final int button) {
        super.onMouseDown(mouseX, mouseY, button);

        this.isDragging = this.isInsideThumb(mouseX, mouseY);

        if (this.isDragging)
            this.grabOffset = mouseX - this.x - this.thumbPosition;
    }

    @Override
    public void onMouseMove(final int mouseX, final int mouseY) {
        super.onMouseMove(mouseX, mouseY);

        if (this.isInsideThumb(mouseX, mouseY)) {
            this.currentThumbColour = this.hoverThumbColour;
        }
        else {
            this.currentThumbColour = this.baseThumbColour;
        }
    }

    @Override
    public void onMouseUp(final int mouseX, final int mouseY, final int button) {
        super.onMouseUp(mouseX, mouseY, button);

        this.isDragging = false;
    }

    private boolean isInsideThumb(final int mouseX, final int mouseY) {
        int posY = this.y + 8 + this.titleSpacing;

        return mouseX > this.x + this.thumbPosition && mouseX <= this.x + this.thumbPosition + this.thumbSize
            && mouseY > posY && mouseY <= posY + this.thumbSize;
    }

    @Override
    public void onMouseDrag(final int startX, final int startY, final int mouseX, final int mouseY) {
        super.onMouseDrag(startX, startY, mouseX, mouseY);

        if (!this.isDragging)
            return;

        int minPosition = 0;
        int maxPosition = this.width - this.thumbSize;
        int newPosition = mouseX - this.x - this.grabOffset;

        this.thumbPosition = Math.min(maxPosition, Math.max(minPosition, newPosition));

        this.calculateValue();

        for (IGuiComponentEvent event : this.events(IOnValueChangedEvent.class))
            event.invoke();
    }

    protected abstract void calculateValue();
    protected abstract void drawLabel();

    public void setBaseThumbColour(final RGBA baseThumbColour) {
        this.baseThumbColour = baseThumbColour;
        this.currentThumbColour = this.baseThumbColour;
    }

    public void setHoverThumbColour(final RGBA hoverThumbColour) {
        this.hoverThumbColour = hoverThumbColour;
    }
}
