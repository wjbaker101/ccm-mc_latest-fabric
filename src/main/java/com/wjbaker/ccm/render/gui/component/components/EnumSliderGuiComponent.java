package com.wjbaker.ccm.render.gui.component.components;

import com.wjbaker.ccm.crosshair.property.EnumProperty;
import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.render.gui.component.type.IBindableGuiComponent;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;
import org.apache.commons.lang3.StringUtils;

public final class EnumSliderGuiComponent<TEnum extends Enum<TEnum>>
    extends SliderGuiComponent
    implements IBindableGuiComponent<EnumProperty<TEnum>> {

    private EnumProperty<TEnum> value;

    public EnumSliderGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final String label,
        final TEnum defaultValue) {

        super(parentGuiScreen, x, y, width, label, 0, defaultValue.getClass().getEnumConstants().length - 1, -1);

        this.value = new EnumProperty<>("fake_value", defaultValue);

        this.thumbPosition = (this.value.get().ordinal() - this.minValue)
            * (this.width - this.thumbSize)
            / (this.maxValue - this.minValue);
    }

    @Override
    protected void calculateValue() {
        float positionRatio = this.thumbPosition / (float)(this.width - this.thumbSize);
        int newValue = (int)(this.minValue + ((this.maxValue + 1 - this.minValue) * positionRatio));

        this.value.setFromOrdinal(Math.min(this.maxValue, Math.max(this.minValue, newValue)));
    }

    @Override
    protected void drawLabel() {
        int posY = this.y + 8 + this.titleSpacing;

        this.renderManager.drawText(
            StringUtils.capitalize(this.value.get().name().toLowerCase()),
            this.x + this.width + this.titleSpacing,
            posY + (this.thumbSize / 2) - 3,
            ModTheme.WHITE,
            false);
    }

    @Override
    public void bind(final EnumProperty<TEnum> property) {
        this.value = property;
    }

    public TEnum getValue() {
        return this.value.get();
    }
}
