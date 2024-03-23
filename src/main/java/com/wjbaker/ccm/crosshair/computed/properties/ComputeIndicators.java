package com.wjbaker.ccm.crosshair.computed.properties;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ComputeIndicators {

    public record IndicatorItem(String text, ItemStack icon) {}

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public ComputeIndicators() {}

    public static List<IndicatorItem> compute(final CustomCrosshair crosshair) {
        var indicators = new ArrayList<IndicatorItem>();

        mutateForToolDamage(indicators, crosshair);
        mutateForProjectiles(indicators, crosshair);

        return indicators;
    }

    private static void mutateForToolDamage(final List<IndicatorItem> indicators, final CustomCrosshair crosshair) {
        if (!crosshair.isToolDamageEnabled.get())
            return;

        if (mc.player == null || mc.player.getMainHandStack() == null)
            return;

        var tool = mc.player.getMainHandStack();
        if (!tool.isDamageable())
            return;

        var remainingDamage = tool.getMaxDamage() - tool.getDamage();
        if (remainingDamage > 10)
            return;

        indicators.add(new IndicatorItem("" + remainingDamage, tool));
    }


    private static void mutateForProjectiles(final List<IndicatorItem> indicators, final CustomCrosshair crosshair) {
        if (!crosshair.isProjectileIndicatorEnabled.get())
            return;

        if (mc.player == null || mc.player.getMainHandStack() == null)
            return;

        var tool = mc.player.getMainHandStack();

        var projectile = mc.player.getProjectileType(tool);
        if (projectile == ItemStack.EMPTY)
            return;

        indicators.add(new IndicatorItem("" + projectile.getCount(), projectile));
    }
}