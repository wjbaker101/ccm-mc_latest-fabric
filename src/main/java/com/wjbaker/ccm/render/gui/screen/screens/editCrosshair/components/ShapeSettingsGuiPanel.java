package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.gui.component.components.ColourPickerGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.IntegerSliderGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class ShapeSettingsGuiPanel extends PanelGuiComponent {

    public ShapeSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        CustomCrosshair crosshair = CustomCrosshairMod.INSTANCE.properties().getCrosshair();

        HeadingGuiComponent heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Crosshair Shape Settings");

        ColourPickerGuiComponent colourPicker = new ColourPickerGuiComponent(this.parentGuiScreen, -1, -1, "Crosshair Colour");
        colourPicker.bind(crosshair.colour);

        IntegerSliderGuiComponent widthSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 150, "Width", 0, 50, crosshair.width.get());
        widthSlider.bind(crosshair.width);

        IntegerSliderGuiComponent heightSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 150, "Height", 0, 50, crosshair.height.get());
        heightSlider.bind(crosshair.height);

        IntegerSliderGuiComponent gapSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 150, "Gap", 0, 50, crosshair.gap.get());
        gapSlider.bind(crosshair.gap);

        IntegerSliderGuiComponent thicknessSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 100, "Thickness", 1, 10, crosshair.thickness.get());
        thicknessSlider.bind(crosshair.thickness);

        IntegerSliderGuiComponent rotationSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, "Rotation", 0, 360, crosshair.rotation.get());
        rotationSlider.bind(crosshair.rotation);

        IntegerSliderGuiComponent scaleSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, "Scale", 25, 500, crosshair.scale.get());
        scaleSlider.bind(crosshair.scale);

        this.addComponent(heading);
        this.addComponent(colourPicker);
        this.addComponent(widthSlider);
        this.addComponent(heightSlider);
        this.addComponent(gapSlider);
        this.addComponent(thicknessSlider);
        this.addComponent(rotationSlider);
        this.addComponent(scaleSlider);
        this.pack();
    }
}
