package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.ButtonGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.ScrollPanelGuiComponent;
import com.wjbaker.ccm.render.gui.component.custom.CrosshairPreviewGuiComponent;
import com.wjbaker.ccm.render.gui.component.event.IOnClickEvent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;
import com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components.*;
import net.minecraft.client.gui.DrawContext;

public final class EditCrosshairGuiScreen extends GuiScreen {

    private final ScrollPanelGuiComponent mainPanel;
    private final CrosshairPreviewGuiComponent crosshairPreviewPanel;
    private final ButtonGuiComponent resetButton;
    private final int panelWidth;
    private final CustomCrosshair crosshair;

    public EditCrosshairGuiScreen(final CustomCrosshair crosshair) {
        super("Edit Crosshair");

        this.panelWidth = 300;
        this.crosshair = crosshair;

        this.mainPanel = new ScrollPanelGuiComponent(this, 0, this.headerHeight + 1, 1000, 1000);
        this.buildComponents();
        this.components.add(this.mainPanel);

        this.crosshairPreviewPanel = new CrosshairPreviewGuiComponent(
            this,
            -1, -1,
            crosshair);

        this.resetButton = new ButtonGuiComponent(this, -1, -1, 80, 15, "Reset Settings");
        this.resetButton.addEvent(IOnClickEvent.class, () -> {
            crosshair.resetProperties();
            this.buildComponents();
        });
    }

    private void buildComponents() {
        var generalSettingsPanel = new GeneralSettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);
        var shapeSettingsPanel = new ShapeSettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);
        var visibilitySettingsPanel = new VisibilitySettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);
        var outlineSettingsPanel = new OutlineSettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);
        var dotSettingsPanel = new DotSettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);
        var dynamicSettingsPanel = new DynamicSettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);
        var highlightSettingsPanel = new HighlightSettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);
        var itemCooldownSettingsPanel = new ItemCooldownSettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);
        var rainbowSettingsPanel = new RainbowSettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);
        var toolDamageSettingsPanel = new IndicatorSettingsGuiPanel(this, this.crosshair, -1, -1, this.panelWidth, -1);

        this.mainPanel.clearComponents();
        this.mainPanel.addComponent(generalSettingsPanel);
        this.mainPanel.addComponent(shapeSettingsPanel);
        this.mainPanel.addComponent(visibilitySettingsPanel);
        this.mainPanel.addComponent(outlineSettingsPanel);
        this.mainPanel.addComponent(dotSettingsPanel);
        this.mainPanel.addComponent(dynamicSettingsPanel);
        this.mainPanel.addComponent(highlightSettingsPanel);
        this.mainPanel.addComponent(itemCooldownSettingsPanel);
        this.mainPanel.addComponent(rainbowSettingsPanel);
        this.mainPanel.addComponent(toolDamageSettingsPanel);
        this.mainPanel.pack();
    }

    @Override
    public void update() {
        super.update();

        this.mainPanel.setSize(this.width - 1, this.height - this.headerHeight - 1);

        if (this.width > this.panelWidth + this.crosshairPreviewPanel.getWidth() + 50) {
            var x = this.panelWidth + ((this.width - this.panelWidth) / 2) + 15 - (this.crosshairPreviewPanel.getWidth() / 2);
            var y = this.headerHeight + ((this.height - this.headerHeight) / 2) + 7 - (this.crosshairPreviewPanel.getHeight() / 2);

            this.crosshairPreviewPanel.setPosition(x, y);

            this.resetButton.setPosition(
                x + this.crosshairPreviewPanel.getWidth() - this.resetButton.getWidth(),
                y + this.crosshairPreviewPanel.getHeight() + 7);
        }
        else {
            var x = this.width - this.crosshairPreviewPanel.getWidth() - 20;
            var y = this.headerHeight + 7;

            this.crosshairPreviewPanel.setPosition(x, y);

            this.resetButton.setPosition(
                x + this.crosshairPreviewPanel.getWidth() - this.resetButton.getWidth(),
                y + this.crosshairPreviewPanel.getHeight() + 7);
        }
    }

    @Override
    public void draw(final DrawContext drawContext) {
        super.draw(drawContext);

        this.crosshairPreviewPanel.draw(drawContext);
        this.resetButton.draw(drawContext);
    }

    @Override
    public void onMouseMove(int mouseX, int mouseY) {
        super.onMouseMove(mouseX, mouseY);
        this.resetButton.onMouseMove(mouseX, mouseY);
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY, int button) {
        super.onMouseDown(mouseX, mouseY, button);
        this.resetButton.onMouseDown(mouseX, mouseY, button);
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY, int button) {
        super.onMouseUp(mouseX, mouseY, button);
        this.resetButton.onMouseUp(mouseX, mouseY, button);
    }
}