package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.*;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class ShapeSettingsGuiPanel extends PanelGuiComponent {

    public ShapeSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Crosshair Shape Settings");

        var colourPicker = new ColourPickerGuiComponent(this.parentGuiScreen, crosshair, -1, -1, "Crosshair Colour");
        colourPicker.bind(crosshair.colour);

        var isBlendEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen,
            -1, -1,
            "Enable Adaptive Colour",
            CustomCrosshairMod.INSTANCE.properties().getCrosshair().isAdaptiveColourEnabled.get());
        isBlendEnabledCheckBox.bind(CustomCrosshairMod.INSTANCE.properties().getCrosshair().isAdaptiveColourEnabled);

        var widthSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 150, "Width", 0, 50, crosshair.width.get());
        widthSlider.bind(crosshair.width);

        var heightSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 150, "Height", 0, 50, crosshair.height.get());
        heightSlider.bind(crosshair.height);

        var gapSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 150, "Gap", 0, 50, crosshair.gap.get());
        gapSlider.bind(crosshair.gap);

        var thicknessSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 100, "Thickness", 1, 10, crosshair.thickness.get());
        thicknessSlider.bind(crosshair.thickness);

        var rotationSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, "Rotation", 0, 360, crosshair.rotation.get());
        rotationSlider.bind(crosshair.rotation);

        var scaleSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, "Scale (%)", 25, 500, crosshair.scale.get());
        scaleSlider.bind(crosshair.scale);

        var offsetXSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, "Offset (X)", -500, 500, crosshair.offsetX.get());
        offsetXSlider.bind(crosshair.offsetX);

        var offsetYSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, "Offset (Y)", -500, 500, crosshair.offsetY.get());
        offsetXSlider.bind(crosshair.offsetY);

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
