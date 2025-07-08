package com.wjbaker.ccm.rendering;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.wjbaker.ccm.rendering.types.RGBA;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.SimpleGuiElementRenderState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureSetup;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;

@Environment(EnvType.CLIENT)
public record ModFilledGuiElementRenderState(Matrix3x2f pose, float[] points, RGBA colour) implements SimpleGuiElementRenderState {

    @Override
    public RenderPipeline pipeline() {
        return RenderPipelines.GUI;
    }

    public void setupVertices(final VertexConsumer vertices, final float depth) {
        for (var i = 0; i < points.length; i += 2) {
            vertices
                .vertex(this.pose, points[i], points[i + 1], depth)
                .color(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getOpacity());
        }
    }

    @Override
    public TextureSetup textureSetup() {
        return TextureSetup.empty();
    }

    @Override
    public @Nullable ScreenRect scissorArea() {
        return null;
    }

    @Override
    public ScreenRect bounds() {
        var window = MinecraftClient.getInstance().getWindow();
        return new ScreenRect(0, 0, window.getFramebufferWidth(), window.getFramebufferHeight()).transformEachVertex(pose);
    }
}