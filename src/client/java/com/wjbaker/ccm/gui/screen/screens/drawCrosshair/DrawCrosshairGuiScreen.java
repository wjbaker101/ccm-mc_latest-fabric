package com.wjbaker.ccm.gui.screen.screens.drawCrosshair;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.properties.IntegerProperty;
import com.wjbaker.ccm.gui.component.components.ButtonGuiComponent;
import com.wjbaker.ccm.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.gui.component.event.IOnClickEvent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import com.wjbaker.ccm.gui.screen.screens.drawCrosshair.components.DrawCrosshairGuiComponent;
import net.minecraft.client.resources.language.I18n;

public final class DrawCrosshairGuiScreen extends GuiScreen {

    public DrawCrosshairGuiScreen() {
        super("Draw Crosshair");

        var titleHeading = new HeadingGuiComponent(this, -1, -1, I18n.get("custom_crosshair_mod.screen.draw_crosshair.heading"));

        var customCrosshairDrawer = CustomCrosshairMod.INSTANCE.properties().getCustomCrosshairDrawer();
        var imageSize = new IntegerProperty("fake_image_size", customCrosshairDrawer.getWidth());
        var drawCrosshair = new DrawCrosshairGuiComponent(this, -1, -1, imageSize);

        var resetButton = new ButtonGuiComponent(this, -1, -1, 50, 15, I18n.get("custom_crosshair_mod.screen.draw_crosshair.reset"));
        resetButton.addEvent(
            IOnClickEvent.class,
            () -> customCrosshairDrawer.reset(customCrosshairDrawer.getWidth(), customCrosshairDrawer.getHeight()));

        var mainPanel = new PanelGuiComponent(this, 7, this.headerHeight + 8, 300, 300);
        mainPanel.addComponent(titleHeading);
        mainPanel.addComponent(drawCrosshair);
        mainPanel.addComponent(resetButton);
        mainPanel.pack();

        this.components.add(mainPanel);
    }

    @Override
    public void close() {
        super.close();
        CustomCrosshairMod.INSTANCE.properties().getCustomCrosshairDrawer().saveImage();
    }
}