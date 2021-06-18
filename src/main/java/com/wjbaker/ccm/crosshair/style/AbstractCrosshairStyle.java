package com.wjbaker.ccm.crosshair.style;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.render.RenderManager;
import net.minecraft.client.util.math.MatrixStack;

public abstract class AbstractCrosshairStyle implements ICrosshairStyle {

    protected final CustomCrosshair crosshair;
    protected final MatrixStack matrixStack;
    protected final RenderManager renderManager;

    public AbstractCrosshairStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        this.crosshair = crosshair;
        this.matrixStack = matrixStack;
        this.renderManager = new RenderManager();
    }
}
