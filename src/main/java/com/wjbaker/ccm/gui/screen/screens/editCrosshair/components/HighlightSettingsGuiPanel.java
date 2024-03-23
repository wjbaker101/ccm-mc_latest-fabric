package com.wjbaker.ccm.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.gui.component.components.ColourPickerGuiComponent;
import com.wjbaker.ccm.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import net.minecraft.client.resource.language.I18n;

public final class HighlightSettingsGuiPanel extends PanelGuiComponent {

    public HighlightSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.highlight_settings"));

        var isHighlightHostilesEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.enable_highlight_hostiles"), crosshair.isHighlightHostilesEnabled.get());
        isHighlightHostilesEnabledCheckBox.bind(crosshair.isHighlightHostilesEnabled);

        var hostilesColourPicker = new ColourPickerGuiComponent(
            this.parentGuiScreen, crosshair, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.highlight_hostiles_colour"));
        hostilesColourPicker.bind(crosshair.highlightHostilesColour);

        var isHighlightPassivesEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.enable_highlight_passives"), crosshair.isHighlightPassivesEnabled.get());;
        isHighlightPassivesEnabledCheckBox.bind(crosshair.isHighlightPassivesEnabled);

        var passivesColourPicker = new ColourPickerGuiComponent(
            this.parentGuiScreen, crosshair, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.highlight_passives_colour"));
        passivesColourPicker.bind(crosshair.highlightPassivesColour);

        var isHighlightPlayersEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.enable_highlight_players"), crosshair.isHighlightPlayersEnabled.get());;
        isHighlightPlayersEnabledCheckBox.bind(crosshair.isHighlightPlayersEnabled);

        var playersColourPicker = new ColourPickerGuiComponent(
            this.parentGuiScreen, crosshair, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.highlight_players_colour"));
        playersColourPicker.bind(crosshair.highlightPlayersColour);

        this.addComponent(heading);
        this.addComponent(isHighlightHostilesEnabledCheckBox);
        this.addComponent(hostilesColourPicker);
        this.addComponent(isHighlightPassivesEnabledCheckBox);
        this.addComponent(passivesColourPicker);
        this.addComponent(isHighlightPlayersEnabledCheckBox);
        this.addComponent(playersColourPicker);
        this.pack();
    }
}