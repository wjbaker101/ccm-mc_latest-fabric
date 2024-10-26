package com.wjbaker.ccm.crosshair.styles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public final class DebugStyle extends CrosshairStyle {

    public DebugStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
        var camera = this.mc.gameRenderer.getCamera();

        var matrixStack = RenderSystem.getModelViewStack();
        matrixStack.pushMatrix();
        matrixStack.translate(x, y, 0.0F);
        matrixStack.rotateX(-camera.getPitch() * 0.017453292F);
        matrixStack.rotateY(camera.getYaw() * 0.017453292F);
        matrixStack.scale(-this.crosshair.scale.get() / 100F, -this.crosshair.scale.get() / 100F, -this.crosshair.scale.get() / 100F);

        RenderSystem.renderCrosshair(10);

        matrixStack.popMatrix();
    }
}