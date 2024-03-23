package com.wjbaker.ccm.crosshair.rendering.computed;

import com.google.common.collect.ImmutableSet;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Set;

public final class ComputeVisibility {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static final Set<Item> RANGED_WEAPONS = ImmutableSet.of(
        Items.BOW,
        Items.TRIDENT,
        Items.CROSSBOW
    );

    private static final Set<Item> THROWABLE_ITEMS = ImmutableSet.of(
        Items.ENDER_PEARL,
        Items.ENDER_EYE
    );

    public static boolean compute(final CustomCrosshair crosshair) {
        if (mc.player == null)
            return false;

        if (!crosshair.isVisibleDefault.get())
            return false;

        if (!crosshair.isVisibleHiddenGui.get() && mc.options.hudHidden)
            return false;

        var pov = mc.options.getPerspective();
        var isThirdPerson = (pov == Perspective.THIRD_PERSON_BACK || pov == Perspective.THIRD_PERSON_FRONT);
        if (!crosshair.isVisibleThirdPerson.get() && isThirdPerson)
            return false;

        if (!crosshair.isVisibleDebug.get() && mc.inGameHud.getDebugHud().shouldShowDebugHud())
            return false;

        if (!crosshair.isVisibleSpectator.get() && mc.player.isSpectator())
            return false;

        if (!crosshair.isVisibleHoldingRangedWeapon.get() && isHoldingItem(mc.player, RANGED_WEAPONS))
            return false;

        if (!crosshair.isVisibleHoldingThrowableItem.get() && isHoldingItem(mc.player, THROWABLE_ITEMS))
            return false;

        if (!crosshair.isVisibleUsingSpyglass.get() && mc.player.isUsingSpyglass())
            return false;

        return true;
    }

    private static boolean isHoldingItem(final ClientPlayerEntity player, final Set<Item> items) {
        var mainHandItem = player.getMainHandStack();

        var isMainHand = items.contains(mainHandItem.getItem());
        var isOffhand = items.contains(player.getOffHandStack().getItem());

        return isMainHand || (isOffhand && mainHandItem.isEmpty());
    }
}