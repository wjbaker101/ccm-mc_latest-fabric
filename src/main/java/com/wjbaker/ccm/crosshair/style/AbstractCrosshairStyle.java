package com.wjbaker.ccm.crosshair.style;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.types.ICrosshairStyle;
import com.wjbaker.ccm.rendering.RenderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public abstract class AbstractCrosshairStyle implements ICrosshairStyle {

    protected final CustomCrosshair crosshair;
    protected final MatrixStack matrixStack;
    protected final RenderManager renderManager;
    protected final MinecraftClient mc;

    public AbstractCrosshairStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        this.crosshair = crosshair;
        this.matrixStack = matrixStack;
        this.renderManager = new RenderManager();
        this.mc = MinecraftClient.getInstance();
    }
}
