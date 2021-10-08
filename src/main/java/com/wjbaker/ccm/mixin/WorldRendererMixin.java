package com.wjbaker.ccm.mixin;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.type.RGBA;
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
    private float setOutlineRed(final float originalRed) {
        return setColour(originalRed, RGBA::getRed);
    }

    @ModifyArg(
        method = BLOCK_OUTLINE_COLOUR_METHOD_NAME,
        at = @At(
            value = "INVOKE",
            target = BLOCK_OUTLINE_COLOUR_INNER_METHOD),
        index = 7)
    private float setOutlineGreen(final float originalGreen) {
        return setColour(originalGreen, RGBA::getGreen);
    }

    @ModifyArg(
        method = BLOCK_OUTLINE_COLOUR_METHOD_NAME,
        at = @At(
            value = "INVOKE",
            target = BLOCK_OUTLINE_COLOUR_INNER_METHOD),
        index = 8)
    private float setOutlineBlue(final float originalBlue) {
        return setColour(originalBlue, RGBA::getBlue);
    }

    @ModifyArg(
        method = BLOCK_OUTLINE_COLOUR_METHOD_NAME,
        at = @At(
            value = "INVOKE",
            target = BLOCK_OUTLINE_COLOUR_INNER_METHOD),
        index = 9)
    private float setOutlineOpacity(final float originalOpacity) {
        return setColour(originalOpacity, RGBA::getOpacity);
    }

    private static float setColour(float originalColour, IColourSelector colourSelector) {
        if (!CustomCrosshairMod.INSTANCE.properties().getIsModEnabled().get())
            return originalColour;

        return colourSelector.get(CustomCrosshairMod.INSTANCE.properties().getBlockOutlineColour().get()) / 255.0F;
    }

    private interface IColourSelector {

        int get(final RGBA colour);
    }
}
