package com.wjbaker.ccm.gui.component.components;

import com.wjbaker.ccm.gui.component.GuiComponent;
import com.wjbaker.ccm.gui.component.event.IGuiComponentEvent;
import com.wjbaker.ccm.gui.component.event.IOnClickEvent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public final class ButtonGuiComponent extends GuiComponent {

    private final String label;
    private final List<IOnClickEvent> onClickEvents;

    public ButtonGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height,
        final String label) {

        super(parentGuiScreen, x, y, width, height);

        this.label = label;
        this.onClickEvents = new ArrayList<>();
    }

    @Override
    public void draw(final DrawContext drawContext) {
        super.draw(drawContext);

        var matrixStack = drawContext.getMatrices();

        this.renderManager.drawBorderedRectangle(
            matrixStack,
            this.x, this.y,
            this.x + this.width, this.y + this.height,
            2.0F,
            this.currentBorderColour,
            this.currentBackgroundColour);

        var centreX = this.x + (this.width / 2) - (this.renderManager.textWidth(this.label) / 2);
        var centreY = this.y + (this.height / 2) - (7 / 2);

        this.renderManager.drawText(drawContext, this.label, centreX, centreY, this.currentTextColour, false);
    }

    @Override
    public void onMouseDown(final int mouseX, final int mouseY, final int button) {
        super.onMouseDown(mouseX, mouseY, button);

        this.isMouseDownInside = true;
    }

    @Override
    public void onMouseUp(final int mouseX, final int mouseY, final int button) {
        super.onMouseUp(mouseX, mouseY, button);

        if (button != 0 || !this.isInsideComponent(mouseX, mouseY) || !this.isMouseDownInside)
            return;

        for (IGuiComponentEvent onClickEvent : this.events(IOnClickEvent.class)) {
            onClickEvent.invoke();
        }

        this.isMouseDownInside = false;
    }
}
