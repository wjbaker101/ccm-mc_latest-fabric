package com.wjbaker.ccm.crosshair;

import com.google.common.collect.Lists;
import com.wjbaker.ccm.crosshair.property.*;
import com.wjbaker.ccm.crosshair.style.CrosshairStyle;
import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.type.RGBA;

import java.util.List;

public final class CustomCrosshair {

    public final EnumProperty<CrosshairStyle> style;
    public final BooleanProperty isKeepDebugEnabled;
    public final RGBAProperty colour;
    public final BooleanProperty isAdaptiveColourEnabled;
    public final IntegerProperty width;
    public final IntegerProperty height;
    public final IntegerProperty gap;
    public final IntegerProperty thickness;
    public final IntegerProperty rotation;
    public final IntegerProperty scale;
    public final IntegerProperty offsetX;
    public final IntegerProperty offsetY;

    public final BooleanProperty isVisibleDefault;
    public final BooleanProperty isVisibleHiddenGui;
    public final BooleanProperty isVisibleDebug;
    public final BooleanProperty isVisibleThirdPerson;
    public final BooleanProperty isVisibleSpectator;
    public final BooleanProperty isVisibleHoldingRangedWeapon;
    public final BooleanProperty isVisibleHoldingThrowableItem;
    public final BooleanProperty isVisibleUsingSpyglass;

    public final BooleanProperty isOutlineEnabled;
    public final RGBAProperty outlineColour;

    public final BooleanProperty isDotEnabled;
    public final RGBAProperty dotColour;

    public final BooleanProperty isDynamicAttackIndicatorEnabled;
    public final BooleanProperty isDynamicBowEnabled;

    public final BooleanProperty isHighlightHostilesEnabled;
    public final BooleanProperty isHighlightPassivesEnabled;
    public final BooleanProperty isHighlightPlayersEnabled;

    public final RGBAProperty highlightHostilesColour;
    public final RGBAProperty highlightPassivesColour;
    public final RGBAProperty highlightPlayersColour;

    public final BooleanProperty isItemCooldownEnabled;
    public final RGBAProperty itemCooldownColour;

    public final BooleanProperty isRainbowEnabled;
    public final IntegerProperty rainbowSpeed;
    public final IntegerProperty rainbowTicks;

    public final List<ICrosshairProperty<?>> propertiesAsList;

    public CustomCrosshair() {
        this.propertiesAsList = Lists.newArrayList(
            this.style = new EnumProperty<>("crosshair_style", null),
            this.isKeepDebugEnabled = new BooleanProperty("crosshair_keep_debug_enabled", null),
            this.colour = new RGBAProperty("crosshair_colour", null),
            this.isAdaptiveColourEnabled = new BooleanProperty("crosshair_adaptive_colour_enabled", null),
            this.width = new IntegerProperty("crosshair_width", null),
            this.height = new IntegerProperty("crosshair_height", null),
            this.gap = new IntegerProperty("crosshair_gap", null),
            this.thickness = new IntegerProperty("crosshair_thickness", null),
            this.rotation = new IntegerProperty("crosshair_rotation", null),
            this.scale = new IntegerProperty("crosshair_scale", null),
            this.offsetX = new IntegerProperty("crosshair_offset_x", null),
            this.offsetY = new IntegerProperty("crosshair_offset_y", null),

            this.isVisibleDefault = new BooleanProperty("visible_default", null),
            this.isVisibleHiddenGui = new BooleanProperty("visible_hiddengui", null),
            this.isVisibleDebug = new BooleanProperty("visible_debug", null),
            this.isVisibleThirdPerson = new BooleanProperty("visible_thirdperson", null),
            this.isVisibleSpectator = new BooleanProperty("visible_spectator", null),
            this.isVisibleHoldingRangedWeapon = new BooleanProperty("visible_holding_ranged_weapon", null),
            this.isVisibleHoldingThrowableItem = new BooleanProperty("visible_holding_throwable_item", null),
            this.isVisibleUsingSpyglass = new BooleanProperty("visible_holding_spyglass", null),

            this.isOutlineEnabled = new BooleanProperty("outline_enabled", null),
            this.outlineColour = new RGBAProperty("outline_colour", null),

            this.isDotEnabled = new BooleanProperty("dot_enabled", null),
            this.dotColour = new RGBAProperty("dot_colour", null),

            this.isDynamicAttackIndicatorEnabled = new BooleanProperty("dynamic_attackindicator_enabled", null),
            this.isDynamicBowEnabled = new BooleanProperty("dynamic_bow_enabled", null),

            this.isHighlightHostilesEnabled = new BooleanProperty("highlight_hostile_enabled", null),
            this.isHighlightPassivesEnabled = new BooleanProperty("highlight_passive_enabled", null),
            this.isHighlightPlayersEnabled = new BooleanProperty("highlight_players_enabled", null),

            this.highlightHostilesColour = new RGBAProperty("highlight_hostile_colour", null),
            this.highlightPassivesColour = new RGBAProperty("highlight_passive_colour", null),
            this.highlightPlayersColour = new RGBAProperty("highlight_players_colour", null),

            this.isItemCooldownEnabled = new BooleanProperty("itemcooldown_enabled", null),
            this.itemCooldownColour = new RGBAProperty("itemcooldown_colour", null),

            this.isRainbowEnabled = new BooleanProperty("rainbow_enabled", null),
            this.rainbowSpeed = new IntegerProperty("rainbow_speed", null)
        );
        this.resetProperties();

        this.rainbowTicks = new IntegerProperty("rainbow_ticks", 0);
    }

    public void resetProperties() {
        this.style.set(CrosshairStyle.CROSS);
        this.isKeepDebugEnabled.set(false);
        this.colour.set(ModTheme.WHITE);
        this.isAdaptiveColourEnabled.set(false);
        this.width.set(4);
        this.height.set(4);
        this.gap.set(3);
        this.thickness.set(1);
        this.rotation.set(0);
        this.scale.set(100);
        this.offsetX.set(0);
        this.offsetY.set(0);

        this.isVisibleDefault.set(true);
        this.isVisibleHiddenGui.set(true);
        this.isVisibleDebug.set(true);
        this.isVisibleThirdPerson.set(false);
        this.isVisibleSpectator.set(true);
        this.isVisibleHoldingRangedWeapon.set(true);
        this.isVisibleHoldingThrowableItem.set(true);
        this.isVisibleUsingSpyglass.set(false);

        this.isOutlineEnabled.set(true);
        this.outlineColour.set(ModTheme.BLACK);

        this.isDotEnabled.set(false);
        this.dotColour.set(ModTheme.WHITE);

        this.isDynamicAttackIndicatorEnabled.set(true);
        this.isDynamicBowEnabled.set(true);

        this.isHighlightHostilesEnabled.set(true);
        this.isHighlightPassivesEnabled.set(true);
        this.isHighlightPlayersEnabled.set(true);

        this.highlightHostilesColour.set(new RGBA(220, 40, 40, 255));
        this.highlightPassivesColour.set(new RGBA(40, 230, 40, 255));
        this.highlightPlayersColour.set(new RGBA(60, 60, 240, 255));

        this.isItemCooldownEnabled.set(true);
        this.itemCooldownColour.set(ModTheme.WHITE.setOpacity(80));

        this.isRainbowEnabled.set(false);
        this.rainbowSpeed.set(500);
    }
}
