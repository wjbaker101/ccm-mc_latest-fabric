package com.wjbaker.ccm.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import net.minecraft.client.resources.language.I18n;

public final class IndicatorSettingsGuiPanel extends PanelGuiComponent {

    public IndicatorSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, I18n.get("custom_crosshair_mod.screen.edit_crosshair.indicator_settings"));

        var isToolDamageEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, I18n.get("custom_crosshair_mod.screen.edit_crosshair.enable_tool_damage_indicator"), crosshair.isToolDamageEnabled.get());
        isToolDamageEnabledCheckBox.bind(crosshair.isToolDamageEnabled);

        var isProjectileIndicatorEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, I18n.get("custom_crosshair_mod.screen.edit_crosshair.enable_projectiles_indicator"), crosshair.isProjectileIndicatorEnabled.get());
        isProjectileIndicatorEnabledCheckBox.bind(crosshair.isProjectileIndicatorEnabled);

        this.addComponent(heading);
        this.addComponent(isToolDamageEnabledCheckBox);
        this.addComponent(isProjectileIndicatorEnabledCheckBox);
        this.pack();
    }
}