package com.wjbaker.ccm.mixins;

import com.wjbaker.ccm.CustomCrosshairMod;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(
        at = @At("HEAD"),
        method = "extractCrosshair",
        cancellable = true
    )
    private void extractCrosshair(final CallbackInfo info) {
        if (!CustomCrosshairMod.INSTANCE.properties().getIsModEnabled().get())
            return;

        info.cancel();
    }
}