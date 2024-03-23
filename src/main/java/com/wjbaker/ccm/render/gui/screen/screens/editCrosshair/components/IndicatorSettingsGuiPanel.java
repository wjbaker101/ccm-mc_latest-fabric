package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class IndicatorSettingsGuiPanel extends PanelGuiComponent {

    public IndicatorSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Indicator Settings");

        var isToolDamageEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Tool Damage", crosshair.isToolDamageEnabled.get());
        isToolDamageEnabledCheckBox.bind(crosshair.isToolDamageEnabled);

        var isProjectileIndicatorEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Projectiles", crosshair.isProjectileIndicatorEnabled.get());
        isProjectileIndicatorEnabledCheckBox.bind(crosshair.isProjectileIndicatorEnabled);

        this.addComponent(heading);
        this.addComponent(isToolDamageEnabledCheckBox);
        this.addComponent(isProjectileIndicatorEnabledCheckBox);
        this.pack();
    }
}
