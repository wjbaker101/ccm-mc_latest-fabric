package com.wjbaker.ccm.rendering;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.wjbaker.ccm.rendering.types.RGBA;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;

@Environment(EnvType.CLIENT)
public record ModLinesGuiElementRenderState(Matrix3x2f pose, Float[] points, RGBA colour) implements GuiElementRenderState {

    private static final RenderPipeline DEBUG_LINES = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.GUI_SNIPPET)
        .withLocation(Identifier.fromNamespaceAndPath("custom-crosshair-mod", "pipeline/debug_lines"))
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.DEBUG_LINE_STRIP)
        .build()
    );

    @Override
    public RenderPipeline pipeline() {
        return DEBUG_LINES;
    }

    @Override
    public void buildVertices(final VertexConsumer vertices) {
        for (var i = 0; i < points.length; i += 2) {
            vertices
                .addVertexWith2DPose(this.pose, points[i], points[i + 1])
                .setColor(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getOpacity())
                .setLineWidth(2.0F);
        }

        vertices
            .addVertex(points[points.length - 2], points[points.length - 1], 1.0F)
            .setColor(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getOpacity())
            .setLineWidth(2.0F);
    }

    @Override
    public TextureSetup textureSetup() {
        return TextureSetup.noTexture();
    }

    @Override
    public ScreenRectangle scissorArea() {
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

        var window = Minecraft.getInstance().getWindow();
        return new ScreenRectangle(-minX.intValue(), -minY.intValue(), window.getGuiScaledWidth() + maxX.intValue(), window.getGuiScaledHeight() + maxY.intValue());
    }

    @Override
    public ScreenRectangle bounds() {
        var window = Minecraft.getInstance().getWindow();
        return new ScreenRectangle( 0, 0, window.getGuiScaledWidth(), window.getGuiScaledHeight()).transformAxisAligned(pose);
    }
}