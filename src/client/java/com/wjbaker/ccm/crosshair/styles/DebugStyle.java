package com.wjbaker.ccm.crosshair.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.DebugCrosshairRenderer;
import org.joml.Matrix3x2fStack;

public final class DebugStyle extends CrosshairStyle {

    private final DebugCrosshairRenderer debugCrosshairRenderer = new DebugCrosshairRenderer();

    public DebugStyle(final Matrix3x2fStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final GuiGraphicsExtractor graphics, final int x, final int y, final ComputedProperties computedProperties) {
        this.debugCrosshairRenderer.render(
            this.mc.gameRenderer.gameRenderState().levelRenderState.cameraRenderState,
            this.mc.gameRenderer.gameRenderState().windowRenderState.guiScale);
    }
}