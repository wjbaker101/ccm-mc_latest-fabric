package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class VisibilitySettingsGuiPanel extends PanelGuiComponent {

    public VisibilitySettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        CustomCrosshair crosshair = CustomCrosshairMod.INSTANCE.properties().getCrosshair();

        HeadingGuiComponent heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Visibility Settings");

        CheckBoxGuiComponent isVisibleDefaultCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible by Default", crosshair.isVisibleDefault.get());
        isVisibleDefaultCheckBox.bind(crosshair.isVisibleDefault);

        CheckBoxGuiComponent isVisibleHiddenGuiCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible Hidden Gui", crosshair.isVisibleHiddenGui.get());
        isVisibleHiddenGuiCheckBox.bind(crosshair.isVisibleHiddenGui);

        CheckBoxGuiComponent isVisibleDebugCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible Debug Gui", crosshair.isVisibleDebug.get());
        isVisibleDebugCheckBox.bind(crosshair.isVisibleDebug);

        CheckBoxGuiComponent isVisibleThirdPersonCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible in Third Person", crosshair.isVisibleThirdPerson.get());
        isVisibleThirdPersonCheckBox.bind(crosshair.isVisibleThirdPerson);

        CheckBoxGuiComponent isVisibleSpectatorCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible in Spectator Mode", crosshair.isVisibleSpectator.get());
        isVisibleSpectatorCheckBox.bind(crosshair.isVisibleSpectator);

        CheckBoxGuiComponent isVisibleHoldingRangedWeapon = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible Holding Ranged Weapon", crosshair.isVisibleHoldingRangedWeapon.get());
        isVisibleHoldingRangedWeapon.bind(crosshair.isVisibleHoldingRangedWeapon);

        CheckBoxGuiComponent isVisibleHoldingThrowableItem = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Visible Holding Throwable Item", crosshair.isVisibleHoldingThrowableItem.get());
        isVisibleHoldingThrowableItem.bind(crosshair.isVisibleHoldingThrowableItem);

        this.addComponent(heading);
        this.addComponent(isVisibleDefaultCheckBox);
        this.addComponent(isVisibleHiddenGuiCheckBox);
        this.addComponent(isVisibleDebugCheckBox);
        this.addComponent(isVisibleThirdPersonCheckBox);
        this.addComponent(isVisibleSpectatorCheckBox);
        this.addComponent(isVisibleHoldingRangedWeapon);
        this.addComponent(isVisibleHoldingThrowableItem);
        this.pack();
    }
}
