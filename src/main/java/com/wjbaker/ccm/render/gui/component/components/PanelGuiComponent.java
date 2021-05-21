package com.wjbaker.ccm.render.gui.component.components;

import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.render.gui.component.GuiComponent;
import com.wjbaker.ccm.render.gui.component.type.PanelOrientation;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;
import com.wjbaker.ccm.render.type.GuiBounds;

import java.util.ArrayList;

public class PanelGuiComponent extends GuiComponent {

    protected final int componentSpacing;
    protected final int padding;
    private final PanelOrientation orientation;

    protected boolean isBorderVisible;
    protected boolean isScissoringEnabled;

    public PanelGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height,
        final PanelOrientation orientation) {

        super(parentGuiScreen, x, y, width, height);

        this.baseBorderColour = ModTheme.DARK_GREY;
        this.hoverBorderColour = this.baseBorderColour;
        this.currentBorderColour = this.baseBorderColour;

        this.baseBackgroundColour = ModTheme.BLACK.setOpacity(200);
        this.hoverBackgroundColour = this.baseBackgroundColour;
        this.currentBackgroundColour = this.baseBackgroundColour;

        this.components = new ArrayList<>();

        this.componentSpacing = 9;
        this.padding = 7;
        this.orientation = orientation;

        this.isBorderVisible = true;
        this.isScissoringEnabled = false;
    }

    public PanelGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height) {

        this(parentGuiScreen, x, y, width, height, PanelOrientation.VERTICAL);
    }

    @Override
    public void update() {
        super.update();

        this.components.forEach(GuiComponent::update);
    }

    @Override
    public void draw() {
        this.renderManager.drawBorderedRectangle(
            this.x, this.y,
            this.x + this.width, this.y + this.height,
            2.0F,
            this.isBorderVisible ? this.currentBorderColour : ModTheme.TRANSPARENT,
            this.currentBackgroundColour);

        if (this.isScissoringEnabled) {
            this.renderManager.drawInsideBounds(
                new GuiBounds(this.x, this.y, this.width, this.height),
                () -> this.components
                    .stream()
                    .filter(x -> !this.isComponentOutside(x))
                    .forEach(GuiComponent::draw));
        }
        else {
            this.components
                .stream()
                .filter(x -> !this.isComponentOutside(x))
                .forEach(GuiComponent::draw);
        }
    }

    protected GuiBounds bounds() {
        return new GuiBounds(this.x, this.y, this.width, this.height);
    }

    private boolean isComponentOutside(final GuiComponent component) {
        return (component.getX() + component.getWidth() < this.bounds().x()
            || component.getX() > this.bounds().x() + this.bounds().width())
            || (component.getY() + component.getHeight() < this.bounds().y()
            || component.getY() > this.bounds().y() + this.bounds().height());
    }

    public void addComponent(final GuiComponent component) {
        this.components.add(component);
    }

    public void removeComponent(final GuiComponent component) {
        this.components.remove(component);
    }

    public void pack() {
        if (this.orientation == PanelOrientation.VERTICAL)
            this.packVertically();
        else
            this.packHorizontally();
    }

    private void packVertically() {
        int height = this.padding;

        for (int index = 0; index < this.components.size(); ++index) {
            GuiComponent component = this.components.get(index);

            component.setPosition(this.x + this.padding, this.y + height);

            height += component.getHeight();

            if (index != this.components.size() - 1)
                height += this.componentSpacing;
        }

        this.height = height + this.padding;
    }

    private void packHorizontally() {
        int width = 0;

        for (int index = 0; index < this.components.size(); ++index) {
            GuiComponent component = this.components.get(index);

            component.setPosition(this.x + width, this.y);

            width += component.getWidth();

            if (index != this.components.size() - 1)
                width += this.componentSpacing;
        }

        this.width = width;

        int maxHeight = 0;
        for (GuiComponent component : this.components) {
            maxHeight = Math.max(maxHeight, component.getHeight());
        }

        this.height = maxHeight;
    }

    @Override
    public void setPosition(final int x, final int y) {
        super.setPosition(x, y);

        this.pack();
    }

    public void setScissoringEnabled(final boolean isScissoringEnabled) {
        this.isScissoringEnabled = isScissoringEnabled;
    }
}
