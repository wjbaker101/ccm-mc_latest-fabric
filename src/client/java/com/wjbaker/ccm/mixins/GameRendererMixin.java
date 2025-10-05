package com.wjbaker.ccm.mixins;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.rendering.CrosshairRenderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    private GuiRenderState guiState;

    private final CrosshairRenderManager crosshairRenderManager = new CrosshairRenderManager();

    @Inject(
        at = @At(
            value = "CONSTANT",
            args = "stringValue=guiExtraction"
        ),
        method = "render"
    )
    private void render(final CallbackInfo info) {
        if (!CustomCrosshairMod.INSTANCE.properties().getIsModEnabled().get())
            return;

        var mc = MinecraftClient.getInstance();

        if (mc.currentScreen != null) {
            return;
        }

        var window = mc.getWindow();
        var x = window.getScaledWidth() / 2;
        var y = window.getScaledHeight() / 2;

        var crosshair = CustomCrosshairMod.INSTANCE.properties().getCrosshair();
        var drawContext = new DrawContext(mc, this.guiState);

        this.crosshairRenderManager.draw(crosshair, drawContext, x, y);
    }
}