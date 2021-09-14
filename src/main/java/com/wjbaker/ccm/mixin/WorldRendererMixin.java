package com.wjbaker.ccm.mixin;

import com.wjbaker.ccm.CustomCrosshairMod;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    private static final String BLOCK_OUTLINE_COLOUR_METHOD_NAME = "drawBlockOutline";
    private static final String BLOCK_OUTLINE_COLOUR_INNER_METHOD = "net.minecraft.client.render.WorldRenderer.drawShapeOutline(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/util/shape/VoxelShape;DDDFFFF)V";

    @ModifyArg(
        method = BLOCK_OUTLINE_COLOUR_METHOD_NAME,
        at = @At(
            value = "INVOKE",
            target = BLOCK_OUTLINE_COLOUR_INNER_METHOD),
        index = 6)
    private float setOutlineRed(float originalRed) {
        return CustomCrosshairMod.INSTANCE.properties().getBlockOutlineColour().get().getRed() / 255.0F;
    }

    @ModifyArg(
        method = BLOCK_OUTLINE_COLOUR_METHOD_NAME,
        at = @At(
            value = "INVOKE",
            target = BLOCK_OUTLINE_COLOUR_INNER_METHOD),
        index = 7)
    private float setOutlineGreen(float originalGreen) {
        return CustomCrosshairMod.INSTANCE.properties().getBlockOutlineColour().get().getGreen() / 255.0F;
    }

    @ModifyArg(
        method = BLOCK_OUTLINE_COLOUR_METHOD_NAME,
        at = @At(
            value = "INVOKE",
            target = BLOCK_OUTLINE_COLOUR_INNER_METHOD),
        index = 8)
    private float setOutlineBlue(float originalBlue) {
        return CustomCrosshairMod.INSTANCE.properties().getBlockOutlineColour().get().getBlue() / 255.0F;
    }

    @ModifyArg(
        method = BLOCK_OUTLINE_COLOUR_METHOD_NAME,
        at = @At(
            value = "INVOKE",
            target = BLOCK_OUTLINE_COLOUR_INNER_METHOD),
        index = 9)
    private float setOutlineOpacity(float originalOpacity) {
        return CustomCrosshairMod.INSTANCE.properties().getBlockOutlineColour().get().getOpacity() / 255.0F;
    }
}
