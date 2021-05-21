package com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.render.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.IntegerSliderGuiComponent;
import com.wjbaker.ccm.render.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

public final class RainbowSettingsGuiPanel extends PanelGuiComponent {

    public RainbowSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        CustomCrosshair crosshair = CustomCrosshairMod.INSTANCE.properties().getCrosshair();

        HeadingGuiComponent heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, "Rainbow Settings");

        CheckBoxGuiComponent isRainbowEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, "Enable Rainbow", crosshair.isRainbowEnabled.get());
        isRainbowEnabledCheckBox.bind(crosshair.isRainbowEnabled);

        IntegerSliderGuiComponent rainbowSpeedSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, "Rainbow Speed", 0, 1000, crosshair.rainbowSpeed.get()) {

            @Override
            protected void drawLabel() {
                String label = this.getValue() < 333
                    ? "Slow"
                    : this.getValue() < 666
                        ? "Medium"
                        : "Fast";

                int posY = this.y + 8 + this.titleSpacing;

                this.renderManager.drawText(
                    label,
                    this.x + this.width + this.titleSpacing,
                    posY + (this.thumbSize / 2) - 3,
                    ModTheme.WHITE,
                    false);
            }
        };
        rainbowSpeedSlider.bind(crosshair.rainbowSpeed);

        this.addComponent(heading);
        this.addComponent(isRainbowEnabledCheckBox);
        this.addComponent(rainbowSpeedSlider);
        this.pack();
    }
}
