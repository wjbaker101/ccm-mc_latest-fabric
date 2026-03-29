package com.wjbaker.ccm.crosshair.computed.properties;

import com.google.common.collect.ImmutableMap;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

public abstract class ComputeGap {

    private static final Minecraft mc = Minecraft.getInstance();

    private static final Map<Item, Float> ITEM_DURATIONS = ImmutableMap.of(
        Items.BOW, 20.0F,
        Items.TRIDENT, 10.0F,
        Items.CROSSBOW, 0.0F
    );

    private ComputeGap() {}

    public static int compute(final CustomCrosshair crosshair) {
        var baseGap = crosshair.gap.get();

        if (mc.player == null)
            return baseGap;

        var isSpectator = mc.player.isSpectator();

        var isHoldingItem = !mc.player.getOffhandItem().isEmpty() || !mc.player.getMainHandItem().isEmpty();

        var isDynamicBowEnabled = crosshair.isDynamicBowEnabled.get();
        var isDynamicAttackIndicatorEnabled = crosshair.isDynamicAttackIndicatorEnabled.get();

        if (isSpectator || !isHoldingItem || (!isDynamicAttackIndicatorEnabled && !isDynamicBowEnabled))
            return baseGap;

        var gapModifier = 2;
        var usageItemDuration = ITEM_DURATIONS.get(mc.player.getActiveItem().getItem());

        if (isDynamicBowEnabled && usageItemDuration != null) {
            if (mc.player.getActiveItem().getItem() == Items.CROSSBOW)
                usageItemDuration = (float) CrossbowItem.getChargeDuration(mc.player.getActiveItem(), mc.player);

            var progress = Math.min(usageItemDuration, mc.player.getActiveItem().getUseDuration(mc.player) - mc.player.getUseItemRemainingTicks());

            return baseGap + Math.round((usageItemDuration - progress) * gapModifier);
        }

        if (isDynamicAttackIndicatorEnabled) {
            var currentAttackUsage = mc.player.getAttackStrengthScale(1.0F);
            var maxAttackUsage = 1.0F;

            if (mc.player.getCurrentItemAttackStrengthDelay() > 5.0F && currentAttackUsage < maxAttackUsage)
                return baseGap + Math.round((maxAttackUsage - currentAttackUsage) * gapModifier * 20);
        }

        return baseGap;
    }
}