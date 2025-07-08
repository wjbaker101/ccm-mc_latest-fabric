package com.wjbaker.ccm.crosshair.types;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.rendering.RenderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;

public abstract class CrosshairStyle {

    public enum Styles {

        VANILLA,
        CROSS,
        CIRCLE,
        SQUARE,
        TRIANGLE,
        ARROW,
        DEBUG,
        DRAWN,
    }

    protected final CustomCrosshair crosshair;
    protected final Matrix3x2fStack matrixStack;
    protected final RenderManager renderManager;
    protected final MinecraftClient mc;

    public CrosshairStyle(final Matrix3x2fStack matrixStack, final CustomCrosshair crosshair) {
        this.crosshair = crosshair;
        this.matrixStack = matrixStack;
        this.renderManager = new RenderManager();
        this.mc = MinecraftClient.getInstance();
    }

    public abstract void draw(
        final DrawContext drawContext,
        final int x,
        final int y,
        final ComputedProperties computedProperties);
}