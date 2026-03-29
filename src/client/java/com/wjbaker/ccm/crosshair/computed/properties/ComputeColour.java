package com.wjbaker.ccm.crosshair.computed.properties;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.rendering.types.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public abstract class ComputeColour {

    private static final Minecraft mc = Minecraft.getInstance();

    private ComputeColour() {}

    public static RGBA compute(final CustomCrosshair crosshair) {
        var target = mc.crosshairPickEntity;

        var isHighlightPlayersEnabled = crosshair.isHighlightPlayersEnabled.get();
        if (isHighlightPlayersEnabled && target instanceof Player)
            return crosshair.highlightPlayersColour.get();

        var isHighlightHostilesEnabled = crosshair.isHighlightHostilesEnabled.get();
        if (isHighlightHostilesEnabled && (target instanceof Monster || target instanceof NeutralMob))
            return crosshair.highlightHostilesColour.get();

        var isHighlightPassivesEnabled = crosshair.isHighlightPassivesEnabled.get();
        if (isHighlightPassivesEnabled && target instanceof Animal)
            return crosshair.highlightPassivesColour.get();

        if (crosshair.isRainbowEnabled.get())
            return getRainbowColour(crosshair);

        return crosshair.colour.get();
    }

    private static RGBA getRainbowColour(final CustomCrosshair crosshair) {
        var ticks = crosshair.rainbowTicks.get() + 1;

        if (ticks > 125000)
            ticks = 0;

        crosshair.rainbowTicks.set(ticks);

        var opacity = crosshair.colour.get().getOpacity();
        var speed = crosshair.rainbowSpeed.get();

        return new RGBA(255, 255, 255, opacity)
            .setRed(getRainbowColourComponent(ticks, 0.0F, speed))
            .setGreen(getRainbowColourComponent(ticks, 2.0F, speed))
            .setBlue(getRainbowColourComponent(ticks, 4.0F, speed));
    }

    private static int getRainbowColourComponent(final int ticks, final float offset, final int speed) {
        return (int)(Math.sin((ticks * speed / 20000.0F) + offset) * 127 + 128);
    }
}