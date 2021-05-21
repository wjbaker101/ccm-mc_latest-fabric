package com.wjbaker.ccm.mixin;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.render.CrosshairRenderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    private final CrosshairRenderManager crosshairRenderManager = new CrosshairRenderManager(
        CustomCrosshairMod.INSTANCE.properties().getCrosshair());

    @Inject(
            at = @At(
                    value = "CONSTANT",
                    args = "stringValue=gui"
            ),
            method = "render"
    )
    private void render(final CallbackInfo info) {
        if (!CustomCrosshairMod.INSTANCE.properties().getIsModEnabled().get())
            return;

        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        int x = Math.round(width / 2.0F);
        int y = Math.round(height / 2.0F);

        this.crosshairRenderManager.draw(x, y);
    }
}
