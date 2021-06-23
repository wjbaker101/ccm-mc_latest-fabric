package com.wjbaker.ccm.mixin;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.render.CrosshairRenderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
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

        var window = MinecraftClient.getInstance().getWindow();
        var x = window.getScaledWidth() / 2;
        var y = window.getScaledHeight() / 2;

        this.crosshairRenderManager.draw(new MatrixStack(), x, y);
    }
}
