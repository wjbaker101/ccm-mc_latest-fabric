package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.ColourPickerGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class HighlightSettingsGuiPanel extends PanelGuiComponent {

    public HighlightSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        CustomCrosshair crosshair = CustomCrosshairMod.INSTANCE.properties().getCrosshair();

        HeadingGuiComponent heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Highlight Settings");

        CheckBoxGuiComponent isHighlightHostilesEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Enable Hostile Mob Highlighting", crosshair.isHighlightHostilesEnabled.get());
        isHighlightHostilesEnabledCheckBox.bind(crosshair.isHighlightHostilesEnabled);

        ColourPickerGuiComponent hostilesColourPicker = new ColourPickerGuiComponent(
            this.parentGuiScreen, -1, -1, "Hostile Mobs Colour");
        hostilesColourPicker.bind(crosshair.highlightHostilesColour);

        CheckBoxGuiComponent isHighlightPassivesEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Enable Passive Mob Highlighting", crosshair.isHighlightPassivesEnabled.get());;
        isHighlightPassivesEnabledCheckBox.bind(crosshair.isHighlightPassivesEnabled);

        ColourPickerGuiComponent passivesColourPicker = new ColourPickerGuiComponent(
            this.parentGuiScreen, -1, -1, "Passive Mobs Colour");
        passivesColourPicker.bind(crosshair.highlightPassivesColour);

        CheckBoxGuiComponent isHighlightPlayersEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Enable Player Highlighting", crosshair.isHighlightPlayersEnabled.get());;
        isHighlightPlayersEnabledCheckBox.bind(crosshair.isHighlightPlayersEnabled);

        ColourPickerGuiComponent playersColourPicker = new ColourPickerGuiComponent(
            this.parentGuiScreen, -1, -1, "Players Colour");
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
