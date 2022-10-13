package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class ToolDamageSettingsGuiPanel extends PanelGuiComponent {

    public ToolDamageSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Tool Damage Settings");

        var isToolDamageEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Enabled", crosshair.isToolDamageEnabled.get());
        isToolDamageEnabledCheckBox.bind(crosshair.isToolDamageEnabled);

        this.addComponent(heading);
        this.addComponent(isToolDamageEnabledCheckBox);
        this.pack();
    }
}
