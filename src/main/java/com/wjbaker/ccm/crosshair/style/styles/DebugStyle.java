package com.wjbaker.ccm.crosshair.style.styles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public final class DebugStyle extends AbstractCrosshairStyle {

    public DebugStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        var camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        var window = MinecraftClient.getInstance().getWindow();

        var matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(window.getScaledWidth() / 2.0D, window.getScaledHeight() / 2.0D, 1);
        matrixStack.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(camera.getPitch()));
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(camera.getYaw()));
        matrixStack.scale(-1, -1, -1);

        RenderSystem.applyModelViewMatrix();

        RenderSystem.renderCrosshair(10);

        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
    }
}
