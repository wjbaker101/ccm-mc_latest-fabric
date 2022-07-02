package com.wjbaker.ccm.render.gui.screen.screens.drawCrosshair;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.property.IntegerProperty;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.IntegerSliderGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.component.event.IOnValueChangedEvent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;
import com.wjbaker.ccm.render.gui.screen.screens.editColour.components.DrawCrosshairGuiComponent;

public final class DrawCrosshairGuiScreen extends GuiScreen {

    public DrawCrosshairGuiScreen() {
        super("Draw Crosshair");

        var titleHeading = new HeadingGuiComponent(this, -1, -1, "Draw Crosshair");

        var customCrosshairDrawer = CustomCrosshairMod.INSTANCE.properties().getCustomCrosshairDrawer();
        var imageSize = new IntegerProperty("fake_image_size", customCrosshairDrawer.getWidth());
        var drawCrosshair = new DrawCrosshairGuiComponent(this, -1, -1, imageSize);

        var mainPanel = new PanelGuiComponent(this, 7, this.headerHeight + 8, 300, 300);

        var imageSizeSlider = new IntegerSliderGuiComponent(this, -1, -1, 100, "Size (Resets the crosshair)", 5, 57, imageSize.get());
        imageSizeSlider.bind(imageSize);
        imageSizeSlider.addEvent(IOnValueChangedEvent.class, () -> {
            var size = imageSize.get();
            drawCrosshair.updateSize();
            customCrosshairDrawer.reset(size, size);
            mainPanel.pack();
        });

        mainPanel.addComponent(titleHeading);
        mainPanel.addComponent(imageSizeSlider);
        mainPanel.addComponent(drawCrosshair);
        mainPanel.pack();

        this.components.add(mainPanel);
    }

    @Override
    public void close() {
        super.close();
        CustomCrosshairMod.INSTANCE.properties().getCustomCrosshairDrawer().saveImage();
    }
}