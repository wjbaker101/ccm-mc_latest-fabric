package com.wjbaker.ccm.crosshair;

import com.google.common.collect.Lists;
import com.wjbaker.ccm.crosshair.property.*;
import com.wjbaker.ccm.crosshair.style.CrosshairStyle;
import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.type.RGBA;

import java.util.List;

public final class CustomCrosshair {

    public final EnumProperty<CrosshairStyle> style;
    public final RGBAProperty colour;
    public final IntegerProperty width;
    public final IntegerProperty height;
    public final IntegerProperty gap;
    public final IntegerProperty thickness;
    public final IntegerProperty rotation;
    public final IntegerProperty scale;

    public final BooleanProperty isVisibleDefault;
    public final BooleanProperty isVisibleHiddenGui;
    public final BooleanProperty isVisibleDebug;
    public final BooleanProperty isVisibleThirdPerson;
    public final BooleanProperty isVisibleSpectator;
    public final BooleanProperty isVisibleHoldingRangedWeapon;
    public final BooleanProperty isVisibleHoldingThrowableItem;

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
            this.style = new EnumProperty<>("crosshair_style", CrosshairStyle.CROSS),
            this.colour = new RGBAProperty("crosshair_colour", ModTheme.WHITE),
            this.width = new IntegerProperty("crosshair_width", 4),
            this.height = new IntegerProperty("crosshair_height", 4),
            this.gap = new IntegerProperty("crosshair_gap", 3),
            this.thickness = new IntegerProperty("crosshair_thickness", 1),
            this.rotation = new IntegerProperty("crosshair_rotation", 0),
            this.scale = new IntegerProperty("crosshair_scale", 100),

            this.isVisibleDefault = new BooleanProperty("visible_default", true),
            this.isVisibleHiddenGui = new BooleanProperty("visible_hiddengui", true),
            this.isVisibleDebug = new BooleanProperty("visible_debug", true),
            this.isVisibleThirdPerson = new BooleanProperty("visible_thirdperson", false),
            this.isVisibleSpectator = new BooleanProperty("visible_spectator", true),
            this.isVisibleHoldingRangedWeapon = new BooleanProperty("visible_holding_ranged_weapon", true),
            this.isVisibleHoldingThrowableItem = new BooleanProperty("visible_holding_throwable_item", true),

            this.isOutlineEnabled = new BooleanProperty("outline_enabled", true),
            this.outlineColour = new RGBAProperty("outline_colour", ModTheme.BLACK),

            this.isDotEnabled = new BooleanProperty("dot_enabled", false),
            this.dotColour = new RGBAProperty("dot_colour", ModTheme.WHITE),

            this.isDynamicAttackIndicatorEnabled = new BooleanProperty("dynamic_attackindicator_enabled", true),
            this.isDynamicBowEnabled = new BooleanProperty("dynamic_bow_enabled", true),

            this.isHighlightHostilesEnabled = new BooleanProperty("highlight_hostile_enabled", true),
            this.isHighlightPassivesEnabled = new BooleanProperty("highlight_passive_enabled", true),
            this.isHighlightPlayersEnabled = new BooleanProperty("highlight_players_enabled", true),

            this.highlightHostilesColour = new RGBAProperty("highlight_hostile_colour", new RGBA(220, 40, 40, 255)),
            this.highlightPassivesColour = new RGBAProperty("highlight_passive_colour", new RGBA(40, 230, 40, 255)),
            this.highlightPlayersColour = new RGBAProperty("highlight_players_colour", new RGBA(60, 60, 240, 255)),

            this.isItemCooldownEnabled = new BooleanProperty("itemcooldown_enabled", true),
            this.itemCooldownColour = new RGBAProperty("itemcooldown_colour", ModTheme.WHITE.setOpacity(80)),

            this.isRainbowEnabled = new BooleanProperty("rainbow_enabled", false),
            this.rainbowSpeed = new IntegerProperty("rainbow_speed", 500)
        );

        this.rainbowTicks = new IntegerProperty("rainbow_ticks", 0);
    }
}
