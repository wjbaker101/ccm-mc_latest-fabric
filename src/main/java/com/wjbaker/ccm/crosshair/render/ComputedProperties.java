package com.wjbaker.ccm.crosshair.render;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.types.IndicatorItem;
import com.wjbaker.ccm.type.RGBA;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private final Map<Item, Float> usageItemsDurations = ImmutableMap.of(
        Items.BOW, 20.0F,
        Items.TRIDENT, 10.0F,
        Items.CROSSBOW, 0.0F
    );

    public ComputedProperties(final CustomCrosshair crosshair) {
        this.mc = MinecraftClient.getInstance();
        this.crosshair = crosshair;

        this.gap = this.calculateGap();
        this.colour = this.calculateColour();
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

    private int calculateGap() {
        var baseGap = this.crosshair.gap.get();

        if (this.mc.player == null)
            return baseGap;

        var isSpectator = this.mc.player.isSpectator();

        var isHoldingItem = !this.mc.player.getStackInHand(Hand.OFF_HAND).isEmpty()
            || !this.mc.player.getStackInHand(Hand.MAIN_HAND).isEmpty();

        var isDynamicBowEnabled = this.crosshair.isDynamicBowEnabled.get();
        var isDynamicAttackIndicatorEnabled = this.crosshair.isDynamicAttackIndicatorEnabled.get();

        if (isSpectator || !isHoldingItem || (!isDynamicAttackIndicatorEnabled && !isDynamicBowEnabled))
            return baseGap;

        var gapModifier = 2;
        var usageItemDuration = this.usageItemsDurations.get(this.mc.player.getActiveItem().getItem());

        if (isDynamicBowEnabled && usageItemDuration != null) {
            if (this.mc.player.getActiveItem().getItem() == Items.CROSSBOW)
                usageItemDuration = (float)CrossbowItem.getPullTime(this.mc.player.getActiveItem());

            var progress = Math.min(usageItemDuration, this.mc.player.getItemUseTime());

            return baseGap + Math.round((usageItemDuration - progress) * gapModifier);
        }
        else if (isDynamicAttackIndicatorEnabled) {
            var hasAttackSpeedModifier = this.mc.player.getMainHandStack()
                .getItem()
                .getAttributeModifiers(EquipmentSlot.MAINHAND)
                .entries()
                .stream()
                .anyMatch(x -> x.getKey().equals(EntityAttributes.GENERIC_ATTACK_SPEED));

            if (!hasAttackSpeedModifier)
                return baseGap;

            var currentAttackUsage = this.mc.player.getAttackCooldownProgress(1.0F);
            var maxAttackUsage = 1.0F;

            if (this.mc.player.getAttackCooldownProgressPerTick() > 5.0F && currentAttackUsage < maxAttackUsage)
                return baseGap + Math.round((maxAttackUsage - currentAttackUsage) * gapModifier * 20);
        }

        return baseGap;
    }

    private RGBA calculateColour() {
        var target = this.mc.targetedEntity;

        var isHighlightPlayersEnabled = this.crosshair.isHighlightPlayersEnabled.get();
        if (isHighlightPlayersEnabled && target instanceof PlayerEntity)
            return this.crosshair.highlightPlayersColour.get();

        var isHighlightHostilesEnabled = this.crosshair.isHighlightHostilesEnabled.get();
        if (isHighlightHostilesEnabled && (target instanceof Monster || target instanceof Angerable))
            return this.crosshair.highlightHostilesColour.get();

        var isHighlightPassivesEnabled = this.crosshair.isHighlightPassivesEnabled.get();
        if (isHighlightPassivesEnabled && target instanceof PassiveEntity)
            return this.crosshair.highlightPassivesColour.get();

        if (this.crosshair.isRainbowEnabled.get())
            return this.getRainbowColour();

        return this.crosshair.colour.get();
    }

    private RGBA getRainbowColour() {
        var ticks = this.crosshair.rainbowTicks.get() + 1;

        if (ticks > 125000)
            ticks = 0;

        this.crosshair.rainbowTicks.set(ticks);

        var opacity = this.crosshair.colour.get().getOpacity();
        var speed = this.crosshair.rainbowSpeed.get();

        return new RGBA(255, 255, 255, opacity)
            .setRed(this.getRainbowColourComponent(ticks, 0.0F, speed))
            .setGreen(this.getRainbowColourComponent(ticks, 2.0F, speed))
            .setBlue(this.getRainbowColourComponent(ticks, 4.0F, speed));
    }

    private int getRainbowColourComponent(final int ticks, final float offset, final int speed) {
        return (int)(Math.sin((ticks * speed / 20000.0F) + offset) * 127 + 128);
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

    public List<IndicatorItem> getIndicatorItems() {
        var indicatorItems = new ArrayList<IndicatorItem>();

        if (crosshair.isToolDamageEnabled.get()) {
            if (this.mc.player != null && this.mc.player.getMainHandStack() != null) {
                var tool = mc.player.getMainHandStack();
                if (tool.isDamageable()) {
                    var remainingDamage = tool.getMaxDamage() - tool.getDamage();
                    if (remainingDamage <= 10) {
                        indicatorItems.add(new IndicatorItem("" + remainingDamage, tool));
                    }
                }
            }
        }

        return indicatorItems;
    }
}
