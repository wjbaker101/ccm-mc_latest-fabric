package com.wjbaker.ccm.gui.screen.screens.editColour;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.properties.IntegerProperty;
import com.wjbaker.ccm.crosshair.properties.RgbaProperty;
import com.wjbaker.ccm.gui.component.components.*;
import com.wjbaker.ccm.gui.component.custom.ColourPreviewGuiComponent;
import com.wjbaker.ccm.gui.component.custom.CrosshairPreviewGuiComponent;
import com.wjbaker.ccm.gui.component.event.IOnClickEvent;
import com.wjbaker.ccm.gui.component.event.IOnValueChangedEvent;
import com.wjbaker.ccm.gui.component.type.PanelOrientation;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import com.wjbaker.ccm.gui.screen.screens.editCrosshair.EditCrosshairGuiScreen;
import com.wjbaker.ccm.rendering.ModTheme;
import com.wjbaker.ccm.rendering.types.RGBA;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;

public final class EditColourGuiScreen extends GuiScreen {

    private final int panelWidth;
    private final ScrollPanelGuiComponent mainPanel;
    private final CrosshairPreviewGuiComponent crosshairPreviewPanel;

    public EditColourGuiScreen(final CustomCrosshair crosshair, final RgbaProperty colour) {
        super("Edit Colour");

        this.panelWidth = 300;

        var titleHeading = new HeadingGuiComponent(this, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_colour.heading"));

        var red = new IntegerProperty("fake_red", colour.get().getRed());
        var redSlider = new IntegerSliderGuiComponent(this, -1, -1, 260, I18n.translate("custom_crosshair_mod.screen.edit_colour.red"), 0, 255, red.get());
        redSlider.setBaseThumbColour(new RGBA(240, 20, 20, 255));
        redSlider.setHoverThumbColour(new RGBA(210, 40, 40, 255));
        redSlider.bind(red);
        redSlider.addEvent(IOnValueChangedEvent.class, () -> colour.set(colour.get().setRed(redSlider.getValue())));

        var green = new IntegerProperty("fake_green", colour.get().getGreen());
        var greenSlider = new IntegerSliderGuiComponent(this, -1, -1, 260, I18n.translate("custom_crosshair_mod.screen.edit_colour.green"), 0, 255, green.get());
        greenSlider.setBaseThumbColour(new RGBA(20, 240, 20, 255));
        greenSlider.setHoverThumbColour(new RGBA(40, 210, 40, 255));
        greenSlider.bind(green);
        greenSlider.addEvent(IOnValueChangedEvent.class, () -> colour.set(colour.get().setGreen(greenSlider.getValue())));

        var blue = new IntegerProperty("fake_blue", colour.get().getBlue());
        var blueSlider = new IntegerSliderGuiComponent(this, -1, -1, 260, I18n.translate("custom_crosshair_mod.screen.edit_colour.blue"), 0, 255, blue.get());
        blueSlider.setBaseThumbColour(new RGBA(20, 20, 240, 255));
        blueSlider.setHoverThumbColour(new RGBA(40, 40, 210, 255));
        blueSlider.bind(blue);
        blueSlider.addEvent(IOnValueChangedEvent.class, () -> colour.set(colour.get().setBlue(blueSlider.getValue())));

        var opacity = new IntegerProperty("fake_opacity", colour.get().getOpacity());
        var opacitySlider = new IntegerSliderGuiComponent(this, -1, -1, 260, I18n.translate("custom_crosshair_mod.screen.edit_colour.opacity"), 0, 255, opacity.get());
        opacitySlider.setBaseThumbColour(new RGBA(250, 250, 250, 255));
        opacitySlider.setHoverThumbColour(new RGBA(240, 240, 240, 255));
        opacitySlider.bind(opacity);
        opacitySlider.addEvent(IOnValueChangedEvent.class, () -> colour.set(colour.get().setOpacity(opacitySlider.getValue())));

        var doneButton = new ButtonGuiComponent(this, -1, -1, 50, 35, I18n.translate("custom_crosshair_mod.screen.edit_colour.done"));
        doneButton.addEvent(
            IOnClickEvent.class,
            () -> MinecraftClient.getInstance().setScreen(new EditCrosshairGuiScreen(crosshair)));

        var colourPreview = new ColourPreviewGuiComponent(this, -1, -1, colour);

        var donePanel = new PanelGuiComponent(this, -1, -1, 200, -1, PanelOrientation.HORIZONTAL);
        donePanel.setBaseBorderColour(ModTheme.TRANSPARENT);
        donePanel.setHoverBorderColour(ModTheme.TRANSPARENT);
        donePanel.addComponent(doneButton);
        donePanel.addComponent(colourPreview);
        donePanel.pack();

        PanelGuiComponent colourPickerPanel = new PanelGuiComponent(this, -1, -1, this.panelWidth, -1);
        colourPickerPanel.addComponent(titleHeading);
        colourPickerPanel.addComponent(redSlider);
        colourPickerPanel.addComponent(greenSlider);
        colourPickerPanel.addComponent(blueSlider);
        colourPickerPanel.addComponent(opacitySlider);
        colourPickerPanel.addComponent(donePanel);
        colourPickerPanel.pack();

        this.mainPanel = new ScrollPanelGuiComponent(this, 0, this.headerHeight + 1, -1, -1);
        this.mainPanel.addComponent(colourPickerPanel);
        this.mainPanel.pack();

        this.components.add(this.mainPanel);

        this.crosshairPreviewPanel = new CrosshairPreviewGuiComponent(
            this,
            -1, -1,
            crosshair);
    }

    @Override
    public void update() {
        super.update();

        this.mainPanel.setSize(this.width - 1, this.height - this.headerHeight - 1);

        if (this.width > this.panelWidth + this.crosshairPreviewPanel.getWidth() + 50) {
            this.crosshairPreviewPanel.setPosition(
                this.panelWidth + ((this.width - this.panelWidth) / 2) + 15 - (this.crosshairPreviewPanel.getWidth() / 2),
                this.headerHeight + ((this.height - this.headerHeight) / 2) + 7 - (this.crosshairPreviewPanel.getHeight() / 2));
        }
        else {
            this.crosshairPreviewPanel.setPosition(
                this.width - this.crosshairPreviewPanel.getWidth() - 20,
                this.headerHeight + 7);
        }
    }

    @Override
    public void draw(final DrawContext drawContext) {
        super.draw(drawContext);

        this.crosshairPreviewPanel.draw(drawContext);
    }
}