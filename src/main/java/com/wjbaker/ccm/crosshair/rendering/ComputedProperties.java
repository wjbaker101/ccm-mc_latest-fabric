package com.wjbaker.ccm.crosshair.rendering;

import com.google.common.collect.ImmutableSet;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.rendering.computed.ComputeColour;
import com.wjbaker.ccm.crosshair.rendering.computed.ComputeGap;
import com.wjbaker.ccm.crosshair.rendering.computed.ComputeIndicators;
import com.wjbaker.ccm.type.RGBA;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;
import java.util.Set;

public final class ComputedProperties {

    private final MinecraftClient mc;
    private final CustomCrosshair crosshair;

    private final int gap;
    private final RGBA colour;
    private final boolean isVisible;

    private final Set<Item> rangedWeapons = ImmutableSet.of(
        Items.BOW,
        Items.TRIDENT,
        Items.CROSSBOW
    );

    private final Set<Item> throwableItems = ImmutableSet.of(
        Items.ENDER_PEARL,
        Items.ENDER_EYE
    );

    public ComputedProperties(final CustomCrosshair crosshair) {
        this.mc = MinecraftClient.getInstance();
        this.crosshair = crosshair;

        this.gap = ComputeGap.compute(crosshair);
        this.colour = ComputeColour.compute(crosshair);
        this.isVisible = this.calculateIsVisible();
    }

    public int gap() {
        return this.gap;
    }

    public RGBA colour() {
        return this.colour;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    private boolean calculateIsVisible() {
        if (this.mc.player == null)
            return false;

        if (!this.crosshair.isVisibleDefault.get())
            return false;

        if (!this.crosshair.isVisibleHiddenGui.get() && this.mc.options.hudHidden)
            return false;

        var pov = this.mc.options.getPerspective();
        var isThirdPerson = (pov == Perspective.THIRD_PERSON_BACK || pov == Perspective.THIRD_PERSON_FRONT);
        if (!this.crosshair.isVisibleThirdPerson.get() && isThirdPerson)
            return false;

        if (!this.crosshair.isVisibleDebug.get() && this.mc.inGameHud.getDebugHud().shouldShowDebugHud())
            return false;

        if (!this.crosshair.isVisibleSpectator.get() && this.mc.player.isSpectator())
            return false;

        if (!this.crosshair.isVisibleHoldingRangedWeapon.get() && this.isHoldingItem(this.mc.player, this.rangedWeapons))
            return false;

        if (!this.crosshair.isVisibleHoldingThrowableItem.get() && this.isHoldingItem(this.mc.player, this.throwableItems))
            return false;

        if (!this.crosshair.isVisibleUsingSpyglass.get() && this.mc.player.isUsingSpyglass())
            return false;

        return true;
    }

    private boolean isHoldingItem(final ClientPlayerEntity player, final Set<Item> items) {
        var mainHandItem = player.getMainHandStack();

        var isMainHand = items.contains(mainHandItem.getItem());
        var isOffhand = items.contains(player.getOffHandStack().getItem());

        return isMainHand || (isOffhand && mainHandItem.isEmpty());
    }

    public List<ComputeIndicators.IndicatorItem> getIndicatorItems() {
        return ComputeIndicators.compute(this.crosshair);
    }
}