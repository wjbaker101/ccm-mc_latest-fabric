package com.wjbaker.ccm.render.gui.component;

import com.wjbaker.ccm.render.gui.event.IMouseEvents;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiComponentWithComponents extends GuiComponentTheme implements IMouseEvents {

    protected List<GuiComponent> components = new ArrayList<>();

    public void draw() {
        this.components.forEach(GuiComponent::draw);
    }

    @Override
    public void onMouseDown(final int mouseX, final int mouseY, final int button) {
        this.components
            .stream()
            .filter(x -> x.isInsideComponent(mouseX, mouseY))
            .forEach(x -> x.onMouseDown(mouseX, mouseY, button));
    }

    @Override
    public void onMouseUp(final int mouseX, final int mouseY, final int button) {
        this.components.forEach(x -> x.onMouseUp(mouseX, mouseY, button));
    }

    @Override
    public void onMouseMove(final int mouseX, int mouseY) {
        this.components.forEach(x -> x.onMouseMove(mouseX, mouseY));
    }

    @Override
    public void onMouseDrag(final int startX, final int startY, final int mouseX, final int mouseY) {
        this.components.forEach(x -> x.onMouseDrag(startX, startY, mouseX, mouseY));
    }

    @Override
    public void onMouseScrollUp() {
        this.components.forEach(GuiComponent::onMouseScrollUp);
    }

    @Override
    public void onMouseScrollDown() {
        this.components.forEach(GuiComponent::onMouseScrollDown);
    }
}
