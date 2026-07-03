package com.wjbaker.ccm.mixins;

import com.wjbaker.ccm.CustomCrosshairMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.renderer.DebugCrosshairRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugCrosshairRenderer.class)
public class DebugCrosshairRendererMixin {

    @Inject(
        at = @At("HEAD"),
        method = "render",
        cancellable = true
    )
    private void render(final CallbackInfo info) {
        if (!CustomCrosshairMod.INSTANCE.properties().getIsModEnabled().get()) {
            return;
        }

        var mc = Minecraft.getInstance();

        if (!mc.gui.hud.getDebugOverlay().showDebugScreen()) {
            return;
        }

        if (CustomCrosshairMod.INSTANCE.properties().getCrosshair().isKeepDebugEnabled.get()) {
            return;
        }

        info.cancel();
    }
}