package com.wjbaker.ccm.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import net.minecraft.client.resource.language.I18n;

public final class DynamicSettingsGuiPanel extends PanelGuiComponent {

    public DynamicSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.dynamic_crosshair_settings"));

        var isDynamicAttackIndicatorEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen,
            -1, -1,
            I18n.translate("custom_crosshair_mod.screen.edit_crosshair.enable_dynamic_attack"),
            crosshair.isDynamicAttackIndicatorEnabled.get());
        isDynamicAttackIndicatorEnabledCheckBox.bind(crosshair.isDynamicAttackIndicatorEnabled);

        var isDynamicBowEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen,
            -1, -1,
            I18n.translate("custom_crosshair_mod.screen.edit_crosshair.enable_dynamic_pull_progress"),
            crosshair.isDynamicBowEnabled.get());
        isDynamicBowEnabledCheckBox.bind(crosshair.isDynamicBowEnabled);

        this.addComponent(heading);
        this.addComponent(isDynamicAttackIndicatorEnabledCheckBox);
        this.addComponent(isDynamicBowEnabledCheckBox);
        this.pack();
    }
}