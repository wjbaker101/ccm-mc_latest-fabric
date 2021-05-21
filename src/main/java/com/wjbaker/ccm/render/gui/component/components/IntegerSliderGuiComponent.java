package com.wjbaker.ccm.render.gui.component.components;

import com.wjbaker.ccm.crosshair.property.IntegerProperty;
import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.render.gui.component.type.IBindableGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

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
        float positionRatio = this.thumbPosition / (float)(this.width - this.thumbSize);
        int newValue = (int)(this.minValue + ((this.maxValue - this.minValue) * positionRatio));

        this.value.set(newValue);
    }

    @Override
    protected void drawLabel() {
        int posY = this.y + 8 + this.titleSpacing;

        this.renderManager.drawText(
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
