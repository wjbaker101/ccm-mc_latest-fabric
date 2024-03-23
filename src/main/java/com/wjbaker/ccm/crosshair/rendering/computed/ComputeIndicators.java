package com.wjbaker.ccm.crosshair.rendering.computed;

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
        var indicatorItems = new ArrayList<IndicatorItem>();

        if (crosshair.isToolDamageEnabled.get()) {
            if (mc.player != null && mc.player.getMainHandStack() != null) {
                var tool = mc.player.getMainHandStack();
                if (tool.isDamageable()) {
                    var remainingDamage = tool.getMaxDamage() - tool.getDamage();
                    if (remainingDamage <= 10) {
                        indicatorItems.add(new IndicatorItem("" + remainingDamage, tool));
                    }
                }
            }
        }

        if (mc.player != null && mc.player.getMainHandStack() != null) {
            var tool = mc.player.getMainHandStack();
            var projectile = mc.player.getProjectileType(tool);
            if (projectile != ItemStack.EMPTY) {
                indicatorItems.add(new IndicatorItem("" + projectile.getCount(), projectile));
            }
        }

        return indicatorItems;
    }
}