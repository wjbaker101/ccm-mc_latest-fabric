package com.wjbaker.ccm.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.gui.component.components.*;
import com.wjbaker.ccm.gui.component.event.IOnClickEvent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import com.wjbaker.ccm.gui.screen.screens.drawCrosshair.DrawCrosshairGuiScreen;
import net.minecraft.client.MinecraftClient;

public final class GeneralSettingsGuiPanel extends PanelGuiComponent {

    public GeneralSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "General Settings");

        var isModEnabledCheckbox = new CheckBoxGuiComponent(
            this.parentGuiScreen,
            -1, -1,
            "Enable " + CustomCrosshairMod.TITLE,
            CustomCrosshairMod.INSTANCE.properties().getIsModEnabled().get());
        isModEnabledCheckbox.bind(CustomCrosshairMod.INSTANCE.properties().getIsModEnabled());

        var crosshairStyleSlider = new EnumSliderGuiComponent<>(
            this.parentGuiScreen,
            -1, -1,
            50,
            "Crosshair Style",
            crosshair.style.get());
        crosshairStyleSlider.bind(crosshair.style);

        var isKeepDebugEnabledCheckbox = new CheckBoxGuiComponent(
            this.parentGuiScreen,
            -1, -1,
            "Keep Default Debug Crosshair When HUD Is Visible",
            crosshair.isKeepDebugEnabled.get());
        isKeepDebugEnabledCheckbox.bind(crosshair.isKeepDebugEnabled);

        var drawCrosshairButton = new ButtonGuiComponent(this.parentGuiScreen, -1, -1, 90, 15, "Draw Crosshair");
        drawCrosshairButton.addEvent(IOnClickEvent.class, () -> {
            MinecraftClient.getInstance().setScreen(new DrawCrosshairGuiScreen());
        });

        this.addComponent(heading);
        this.addComponent(isModEnabledCheckbox);
        this.addComponent(crosshairStyleSlider);
        this.addComponent(isKeepDebugEnabledCheckbox);
        this.addComponent(drawCrosshairButton);
        this.pack();
    }
}
