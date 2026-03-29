package com.wjbaker.ccm.crosshair.styles;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

public final class VanillaStyle extends CrosshairStyle {

    private static final Identifier CROSSHAIR_SPRITE = Identifier.withDefaultNamespace("hud/crosshair");

    public VanillaStyle(final Matrix3x2fStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final GuiGraphicsExtractor graphics, final int x, final int y, final ComputedProperties computedProperties) {
        var crosshairSize = 15;

        graphics.blitSprite(
            RenderPipelines.CROSSHAIR,
            CROSSHAIR_SPRITE,
            -crosshairSize / 2,
            -crosshairSize / 2,
            crosshairSize,
            crosshairSize);
    }
}