package com.wjbaker.ccm.crosshair.style.styles;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.render.ComputedProperties;
import com.wjbaker.ccm.crosshair.style.AbstractCrosshairStyle;
import net.minecraft.client.util.math.MatrixStack;

public final class DrawnStyle extends AbstractCrosshairStyle {

    public DrawnStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final int x, final int y, final ComputedProperties computedProperties) {
        var image = CustomCrosshairMod.INSTANCE.properties().getCustomCrosshairDrawer();
        var baseColour = computedProperties.colour();

        this.renderManager.drawImage(this.matrixStack, x, y, image, baseColour);
    }
}