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
public record ModLinesGuiElementRenderState(Matrix3x2f pose, Float[] points, RGBA colour) implements SimpleGuiElementRenderState {

    @Override
    public RenderPipeline pipeline() {
        return RenderPipelines.DEBUG_LINE_STRIP;
    }

    public void setupVertices(final VertexConsumer vertices, final float depth) {
        for (var i = 0; i < points.length; i += 2) {
            vertices
                .vertex(this.pose, points[i], points[i + 1], depth)
                .color(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getOpacity());
        }

        vertices
            .vertex(this.pose, points[points.length - 2], points[points.length - 1], depth)
            .color(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getOpacity());
    }

    @Override
    public TextureSetup textureSetup() {
        return TextureSetup.empty();
    }

    @Override
    public @Nullable ScreenRect scissorArea() {
        var minX = points[0];
        var minY = points[1];
        var maxX = points[0];
        var maxY = points[1];

        for (int i = 2; i < points.length; i++) {
            if (i % 2 == 0) {
                minX = Math.min(minX, points[i]);
                maxX = Math.max(maxX, points[i]);
            }
            else {
                minY = Math.min(minY, points[i]);
                maxY = Math.max(maxY, points[i]);
            }
        }

        var window = MinecraftClient.getInstance().getWindow();
        return new ScreenRect(-minX.intValue(), -minY.intValue(), window.getFramebufferWidth() + maxX.intValue(), window.getFramebufferHeight() + maxY.intValue());
    }

    @Override
    public ScreenRect bounds() {
        var window = MinecraftClient.getInstance().getWindow();
        return new ScreenRect(0, 0, window.getFramebufferWidth(), window.getFramebufferHeight()).transformEachVertex(pose);
    }
}