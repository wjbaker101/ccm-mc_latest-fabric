package com.wjbaker.ccm.crosshair.computed.properties;

import com.google.common.collect.ImmutableMap;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import java.util.Map;

public abstract class ComputeGap {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

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

        var isHoldingItem = !mc.player.getStackInHand(Hand.OFF_HAND).isEmpty()
            || !mc.player.getStackInHand(Hand.MAIN_HAND).isEmpty();

        var isDynamicBowEnabled = crosshair.isDynamicBowEnabled.get();
        var isDynamicAttackIndicatorEnabled = crosshair.isDynamicAttackIndicatorEnabled.get();

        if (isSpectator || !isHoldingItem || (!isDynamicAttackIndicatorEnabled && !isDynamicBowEnabled))
            return baseGap;

        var gapModifier = 2;
        var usageItemDuration = ITEM_DURATIONS.get(mc.player.getActiveItem().getItem());

        if (isDynamicBowEnabled && usageItemDuration != null) {
            if (mc.player.getActiveItem().getItem() == Items.CROSSBOW)
                usageItemDuration = (float) CrossbowItem.getPullTime(mc.player.getActiveItem());

            var progress = Math.min(usageItemDuration, mc.player.getItemUseTime());

            return baseGap + Math.round((usageItemDuration - progress) * gapModifier);
        }
        else if (isDynamicAttackIndicatorEnabled) {
            var currentAttackUsage = mc.player.getAttackCooldownProgress(1.0F);
            var maxAttackUsage = 1.0F;

            if (mc.player.getAttackCooldownProgressPerTick() > 5.0F && currentAttackUsage < maxAttackUsage)
                return baseGap + Math.round((maxAttackUsage - currentAttackUsage) * gapModifier * 20);
        }

        return baseGap;
    }
}