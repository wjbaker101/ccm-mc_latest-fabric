package com.wjbaker.ccm.gui.component.components;

import com.wjbaker.ccm.crosshair.properties.IntegerProperty;
import com.wjbaker.ccm.gui.component.type.IBindableGuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import com.wjbaker.ccm.rendering.ModTheme;
import net.minecraft.client.gui.DrawContext;

public class IntegerSliderGuiComponent
    extends SliderGuiComponent
    implements IBindableGuiComponent<IntegerProperty> {

    private IntegerProperty value;

    public IntegerSliderGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final String label,
        final int minValue,
        final int maxValue,
        final int defaultValue) {

        super(parentGuiScreen, x, y, width, label, minValue, maxValue, defaultValue);

        this.value = new IntegerProperty("fake_value", this.defaultValue);

        this.thumbPosition = (this.value.get() - this.minValue)
            * (this.width - this.thumbSize)
            / (this.maxValue - this.minValue);
    }

    @Override
    protected void calculateValue() {
        var positionRatio = this.thumbPosition / (float)(this.width - this.thumbSize);
        var newValue = (int)(this.minValue + ((this.maxValue - this.minValue) * positionRatio));

        this.value.set(newValue);
    }

    @Override
    protected void drawLabel(final DrawContext drawContext) {
        var posY = this.y + 8 + this.titleSpacing;

        this.renderManager.drawText(
            drawContext,
            "" + this.value.get(),
            this.x + this.width + this.titleSpacing,
            posY + (this.thumbSize / 2) - 3,
            ModTheme.WHITE,
            false);
    }

    @Override
    public void bind(final IntegerProperty property) {
        this.value = property;
    }

    public int getValue() {
        return this.value.get();
    }
}