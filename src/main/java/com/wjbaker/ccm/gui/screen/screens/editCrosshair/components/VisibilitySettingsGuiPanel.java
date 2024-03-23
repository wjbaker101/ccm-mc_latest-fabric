package com.wjbaker.ccm.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;

public final class VisibilitySettingsGuiPanel extends PanelGuiComponent {

    public VisibilitySettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Visibility Settings");

        var isVisibleDefaultCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible by Default", crosshair.isVisibleDefault.get());
        isVisibleDefaultCheckBox.bind(crosshair.isVisibleDefault);

        var isVisibleHiddenGuiCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible Hidden Gui", crosshair.isVisibleHiddenGui.get());
        isVisibleHiddenGuiCheckBox.bind(crosshair.isVisibleHiddenGui);

        var isVisibleDebugCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible Debug Gui", crosshair.isVisibleDebug.get());
        isVisibleDebugCheckBox.bind(crosshair.isVisibleDebug);

        var isVisibleThirdPersonCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible in Third Person", crosshair.isVisibleThirdPerson.get());
        isVisibleThirdPersonCheckBox.bind(crosshair.isVisibleThirdPerson);

        var isVisibleSpectatorCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible in Spectator Mode", crosshair.isVisibleSpectator.get());
        isVisibleSpectatorCheckBox.bind(crosshair.isVisibleSpectator);

        var isVisibleHoldingRangedWeaponCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible Holding Ranged Weapon", crosshair.isVisibleHoldingRangedWeapon.get());
        isVisibleHoldingRangedWeaponCheckBox.bind(crosshair.isVisibleHoldingRangedWeapon);

        var isVisibleHoldingThrowableItemCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible Holding Throwable Item", crosshair.isVisibleHoldingThrowableItem.get());
        isVisibleHoldingThrowableItemCheckBox.bind(crosshair.isVisibleHoldingThrowableItem);

        var isVisibleUsingSpyglassCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible Using Spyglass", crosshair.isVisibleUsingSpyglass.get());
        isVisibleUsingSpyglassCheckBox.bind(crosshair.isVisibleUsingSpyglass);

        this.addComponent(heading);
        this.addComponent(isVisibleDefaultCheckBox);
        this.addComponent(isVisibleHiddenGuiCheckBox);
        this.addComponent(isVisibleDebugCheckBox);
        this.addComponent(isVisibleThirdPersonCheckBox);
        this.addComponent(isVisibleSpectatorCheckBox);
        this.addComponent(isVisibleHoldingRangedWeaponCheckBox);
        this.addComponent(isVisibleHoldingThrowableItemCheckBox);
        this.addComponent(isVisibleUsingSpyglassCheckBox);
        this.pack();
    }
}
