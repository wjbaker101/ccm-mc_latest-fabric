package com.wjbaker.ccm.crosshair.computed.properties;

import com.google.common.collect.ImmutableSet;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Set;

public abstract class ComputeVisibility {

    private static final Minecraft mc = Minecraft.getInstance();

    private static final Set<Item> RANGED_WEAPONS = ImmutableSet.of(
        Items.BOW,
        Items.TRIDENT,
        Items.CROSSBOW
    );

    private static final Set<Item> THROWABLE_ITEMS = ImmutableSet.of(
        Items.ENDER_PEARL,
        Items.ENDER_EYE
    );

    private ComputeVisibility() {}

    public static boolean compute(final CustomCrosshair crosshair) {
        if (mc.player == null)
            return false;

        if (!crosshair.isVisibleDefault.get())
            return false;

        if (!crosshair.isVisibleHiddenGui.get() && mc.gui.hud.isHidden())
            return false;

        var pov = mc.options.getCameraType();
        var isThirdPerson = (pov == CameraType.THIRD_PERSON_BACK || pov == CameraType.THIRD_PERSON_FRONT);
        if (!crosshair.isVisibleThirdPerson.get() && isThirdPerson)
            return false;

        if (!crosshair.isVisibleDebug.get() && mc.gui.hud.getDebugOverlay().showDebugScreen())
            return false;

        if (!crosshair.isVisibleSpectator.get() && mc.player.isSpectator())
            return false;

        if (!crosshair.isVisibleHoldingRangedWeapon.get() && isHoldingItem(mc.player, RANGED_WEAPONS))
            return false;

        if (!crosshair.isVisibleHoldingThrowableItem.get() && isHoldingItem(mc.player, THROWABLE_ITEMS))
            return false;

        if (!crosshair.isVisibleUsingSpyglass.get() && mc.player.isScoping())
            return false;

        return true;
    }

    private static boolean isHoldingItem(final LocalPlayer player, final Set<Item> items) {
        var mainHandItem = player.getMainHandItem();

        var isMainHand = items.contains(mainHandItem.getItem());
        var isOffhand = items.contains(player.getOffhandItem().getItem());

        return isMainHand || (isOffhand && mainHandItem.isEmpty());
    }
}