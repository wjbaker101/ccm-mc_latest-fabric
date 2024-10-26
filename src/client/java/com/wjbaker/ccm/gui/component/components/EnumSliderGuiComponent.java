package com.wjbaker.ccm.gui.component.components;

import com.wjbaker.ccm.crosshair.properties.EnumProperty;
import com.wjbaker.ccm.gui.component.type.IBindableGuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import com.wjbaker.ccm.rendering.ModTheme;
import net.minecraft.client.gui.DrawContext;
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
        var positionRatio = this.thumbPosition / (float)(this.width - this.thumbSize);
        var newValue = (int)(this.minValue + ((this.maxValue + 1 - this.minValue) * positionRatio));

        this.value.setFromOrdinal(Math.min(this.maxValue, Math.max(this.minValue, newValue)));
    }

    @Override
    protected void drawLabel(final DrawContext drawContext) {
        var posY = this.y + 8 + this.titleSpacing;

        this.renderManager.drawText(
            drawContext,
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