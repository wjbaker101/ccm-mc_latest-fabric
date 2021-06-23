package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.ColourPickerGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class ItemCooldownSettingsGuiPanel extends PanelGuiComponent {

    public ItemCooldownSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Item Cooldown Settings");

        var isItemCooldownEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Enable Item Cooldown Indicator", crosshair.isItemCooldownEnabled.get());
        isItemCooldownEnabledCheckBox.bind(crosshair.isItemCooldownEnabled);

        var itemCooldownColourColourPicker = new ColourPickerGuiComponent(
            this.parentGuiScreen, crosshair, -1, -1, "Indicator Colour");
        itemCooldownColourColourPicker.bind(crosshair.itemCooldownColour);

        this.addComponent(heading);
        this.addComponent(isItemCooldownEnabledCheckBox);
        this.addComponent(itemCooldownColourColourPicker);
        this.pack();
    }
}
