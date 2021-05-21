package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.ColourPickerGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class OutlineSettingsGuiPanel extends PanelGuiComponent {

    public OutlineSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        CustomCrosshair crosshair = CustomCrosshairMod.INSTANCE.properties().getCrosshair();

        HeadingGuiComponent heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Outline Settings");

        CheckBoxGuiComponent isOutlineEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Enable Outline", crosshair.isOutlineEnabled.get());
        isOutlineEnabledCheckBox.bind(crosshair.isOutlineEnabled);

        ColourPickerGuiComponent outlineColourColourPicker = new ColourPickerGuiComponent(this.parentGuiScreen, -1, -1, "Outline Colour");
        outlineColourColourPicker.bind(crosshair.outlineColour);

        this.addComponent(heading);
        this.addComponent(isOutlineEnabledCheckBox);
        this.addComponent(outlineColourColourPicker);
        this.pack();
    }
}
