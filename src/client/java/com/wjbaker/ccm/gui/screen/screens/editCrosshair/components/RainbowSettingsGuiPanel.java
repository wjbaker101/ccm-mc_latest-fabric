package com.wjbaker.ccm.gui.screen.screens.editCrosshair.components;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.gui.component.components.CheckBoxGuiComponent;
import com.wjbaker.ccm.gui.component.components.HeadingGuiComponent;
import com.wjbaker.ccm.gui.component.components.IntegerSliderGuiComponent;
import com.wjbaker.ccm.gui.component.components.PanelGuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import com.wjbaker.ccm.rendering.ModTheme;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;

public final class RainbowSettingsGuiPanel extends PanelGuiComponent {

    public RainbowSettingsGuiPanel(
        final GuiScreen parentGuiScreen,
        final CustomCrosshair crosshair,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        var heading = new HeadingGuiComponent(this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.rainbow_settings"));

        var isRainbowEnabledCheckBox = new CheckBoxGuiComponent(
            this.parentGuiScreen, -1, -1, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.enable_rainbow"), crosshair.isRainbowEnabled.get());
        isRainbowEnabledCheckBox.bind(crosshair.isRainbowEnabled);

        var rainbowSpeedSlider = new IntegerSliderGuiComponent(
            this.parentGuiScreen, -1, -1, 250, I18n.translate("custom_crosshair_mod.screen.edit_crosshair.rainbow_speed"), 0, 1000, crosshair.rainbowSpeed.get()) {

            @Override
            protected void drawLabel(final DrawContext drawContext) {
                String label = this.getValue() < 333
                    ? I18n.translate("custom_crosshair_mod.screen.edit_crosshair.rainbow_speed.slow")
                    : this.getValue() < 666
                        ? I18n.translate("custom_crosshair_mod.screen.edit_crosshair.rainbow_speed.medium")
                        : I18n.translate("custom_crosshair_mod.screen.edit_crosshair.rainbow_speed.fast");

                int posY = this.y + 8 + this.titleSpacing;

                this.renderManager.drawText(
                    drawContext,
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