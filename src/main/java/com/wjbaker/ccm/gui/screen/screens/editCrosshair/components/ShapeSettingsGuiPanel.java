package com.wjbaker.ccm.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.gui.component.components.*;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import net.minecraft.client.resource.language.I18n;

public final class ShapeSettingsGuiPanel extends PanelGuiComponent {

    public ShapeSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.shape_settings"));

        var colourPicker = new ColourPickerGuiComponent(this.parentGuiScreen, crosshair, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.crosshair_colour"));
        colourPicker.bind(crosshair.colour);

        var isBlendEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen,
            -1, -1,
            I18n.translate("custom_crosshair_mod.screen.edit_crosshair.enable_adaptive_colour"),
            CustomCrosshairMod.INSTANCE.properties().getCrosshair().isAdaptiveColourEnabled.get());
        isBlendEnabledCheckBox.bind(CustomCrosshairMod.INSTANCE.properties().getCrosshair().isAdaptiveColourEnabled);

        var widthSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 150, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.width"), 0, 50, crosshair.width.get());
        widthSlider.bind(crosshair.width);

        var heightSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 150, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.height"), 0, 50, crosshair.height.get());
        heightSlider.bind(crosshair.height);

        var gapSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 150, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.gap"), 0, 50, crosshair.gap.get());
        gapSlider.bind(crosshair.gap);

        var thicknessSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 100, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.thickness"), 1, 10, crosshair.thickness.get());
        thicknessSlider.bind(crosshair.thickness);

        var rotationSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.rotation"), 0, 360, crosshair.rotation.get());
        rotationSlider.bind(crosshair.rotation);

        var scaleSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.scale") + " (%)", 25, 500, crosshair.scale.get());
        scaleSlider.bind(crosshair.scale);

        var offsetXSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 251, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.offset") + " (X)", -500, 500, crosshair.offsetX.get());
        offsetXSlider.bind(crosshair.offsetX);

        var offsetYSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 251, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.offset") + " (Y)", -500, 500, crosshair.offsetY.get());
        offsetYSlider.bind(crosshair.offsetY);

        this.addComponent(heading);
        this.addComponent(colourPicker);
        this.addComponent(isBlendEnabledCheckBox);
        this.addComponent(widthSlider);
        this.addComponent(heightSlider);
        this.addComponent(gapSlider);
        this.addComponent(thicknessSlider);
        this.addComponent(rotationSlider);
        this.addComponent(scaleSlider);
        this.addComponent(offsetXSlider);
        this.addComponent(offsetYSlider);
        this.pack();
    }
}