package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.style.CrosshairStyle;
import com.wjbaker.ccm.render.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.EnumSliderGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class GeneralSettingsGuiPanel extends PanelGuiComponent {

    public GeneralSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        CustomCrosshair crosshair = CustomCrosshairMod.INSTANCE.properties().getCrosshair();

        HeadingGuiComponent heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "General Settings");

        CheckBoxGuiComponent isModEnabledCheckbox = new CheckBoxGuiComponent(
            this.parentGuiScreen,
            -1, -1,
            "Enable " + CustomCrosshairMod.TITLE,
            CustomCrosshairMod.INSTANCE.properties().getIsModEnabled().get());
        isModEnabledCheckbox.bind(CustomCrosshairMod.INSTANCE.properties().getIsModEnabled());

        EnumSliderGuiComponent<CrosshairStyle> crosshairStyleSlider = new EnumSliderGuiComponent<>(
            this.parentGuiScreen,
            -1, -1,
            50,
            "Crosshair Style",
            crosshair.style.get());
        crosshairStyleSlider.bind(crosshair.style);

        this.addComponent(heading);
        this.addComponent(isModEnabledCheckbox);
        this.addComponent(crosshairStyleSlider);
        this.pack();
    }
}
