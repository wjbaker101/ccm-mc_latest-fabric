package com.wjbaker.ccm.crosshair.render.types;

import net.minecraft.item.ItemStack;

public final class IndicatorItem {

    private final String text;
    private final ItemStack icon;

    public IndicatorItem(final String text, final ItemStack icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return this.text;
    }

    public ItemStack getIcon() {
        return this.icon;
    }
}