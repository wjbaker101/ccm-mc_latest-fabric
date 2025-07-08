package com.wjbaker.ccm.crosshair.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;

public final class DebugStyle extends CrosshairStyle {

    public DebugStyle(final Matrix3x2fStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
        this.mc.inGameHud.getDebugHud().renderDebugCrosshair(this.mc.gameRenderer.getCamera());
    }
}