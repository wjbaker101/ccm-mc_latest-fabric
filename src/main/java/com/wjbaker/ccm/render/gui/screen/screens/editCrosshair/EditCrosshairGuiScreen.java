package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.ScrollPanelGuiComponent;
import com.wjbaker.ccm.render.gui.component.custom.CrosshairPreviewGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;
import com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components.*;

public final class EditCrosshairGuiScreen extends GuiScreen {

    private final ScrollPanelGuiComponent mainPanel;
    private final CrosshairPreviewGuiComponent crosshairPreviewPanel;
    private final int panelWidth;

    public EditCrosshairGuiScreen(final CustomCrosshair crosshair) {
        super("Edit Crosshair");

        this.panelWidth = 300;

        GeneralSettingsGuiPanel generalSettingsPanel = new GeneralSettingsGuiPanel(this, crosshair, -1, -1, this.panelWidth, -1);
        ShapeSettingsGuiPanel shapeSettingsPanel = new ShapeSettingsGuiPanel(this, crosshair, -1, -1, this.panelWidth, -1);
        VisibilitySettingsGuiPanel visibilitySettingsPanel = new VisibilitySettingsGuiPanel(this, crosshair, -1, -1, this.panelWidth, -1);
        OutlineSettingsGuiPanel outlineSettingsPanel = new OutlineSettingsGuiPanel(this, crosshair, -1, -1, this.panelWidth, -1);
        DotSettingsGuiPanel dotSettingsPanel = new DotSettingsGuiPanel(this, crosshair, -1, -1, this.panelWidth, -1);
        DynamicSettingsGuiPanel dynamicSettingsPanel = new DynamicSettingsGuiPanel(this, crosshair, -1, -1, this.panelWidth, -1);
        HighlightSettingsGuiPanel highlightSettingsPanel = new HighlightSettingsGuiPanel(this, crosshair, -1, -1, this.panelWidth, -1);
        ItemCooldownSettingsGuiPanel itemCooldownSettingsPanel = new ItemCooldownSettingsGuiPanel(this, crosshair, -1, -1, this.panelWidth, -1);
        RainbowSettingsGuiPanel rainbowSettingsPanel = new RainbowSettingsGuiPanel(this, crosshair, -1, -1, this.panelWidth, -1);

        this.mainPanel = new ScrollPanelGuiComponent(this, 0, this.headerHeight + 1, 1000, 1000);
        this.mainPanel.addComponent(generalSettingsPanel);
        this.mainPanel.addComponent(shapeSettingsPanel);
        this.mainPanel.addComponent(visibilitySettingsPanel);
        this.mainPanel.addComponent(outlineSettingsPanel);
        this.mainPanel.addComponent(dotSettingsPanel);
        this.mainPanel.addComponent(dynamicSettingsPanel);
        this.mainPanel.addComponent(highlightSettingsPanel);
        this.mainPanel.addComponent(itemCooldownSettingsPanel);
        this.mainPanel.addComponent(rainbowSettingsPanel);
        this.mainPanel.pack();

        this.components.add(mainPanel);

        this.crosshairPreviewPanel = new CrosshairPreviewGuiComponent(
            this,
            -1, -1,
            crosshair);
    }

    @Override
    public void update() {
        super.update();

        this.mainPanel.setSize(this.width - 1, this.height - this.headerHeight - 1);

        if (this.width > this.panelWidth + this.crosshairPreviewPanel.getWidth() + 50) {
            this.crosshairPreviewPanel.setPosition(
                this.panelWidth + ((this.width - this.panelWidth) / 2) + 15 - (this.crosshairPreviewPanel.getWidth() / 2),
                this.headerHeight + ((this.height - this.headerHeight) / 2) + 7 - (this.crosshairPreviewPanel.getHeight() / 2));
        }
        else {
            this.crosshairPreviewPanel.setPosition(
                this.width - this.crosshairPreviewPanel.getWidth() - 20,
                this.headerHeight + 7);
        }
    }

    @Override
    public void draw() {
        super.draw();

        this.crosshairPreviewPanel.draw();
    }
}
