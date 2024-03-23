package com.wjbaker.ccm.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.gui.component.components.ColourPickerGuiComponent;
import com.wjbaker.ccm.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import net.minecraft.client.resource.language.I18n;

public final class DotSettingsGuiPanel extends PanelGuiComponent {

    public DotSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.dot_settings"));

        var isDotEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.enable_dot"), crosshair.isDotEnabled.get());
        isDotEnabledCheckBox.bind(crosshair.isDotEnabled);

        var dotColourColourPicker = new ColourPickerGuiComponent(
            this.parentGuiScreen, crosshair, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.dot_colour"));
        dotColourColourPicker.bind(crosshair.dotColour);

        this.addComponent(heading);
        this.addComponent(isDotEnabledCheckBox);
        this.addComponent(dotColourColourPicker);
        this.pack();
    }
}