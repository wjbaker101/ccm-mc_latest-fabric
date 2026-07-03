package com.wjbaker.ccm.rendering;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

    private static final RenderPipeline DEBUG_LINES = RenderPipelines.register(
        RenderPipeline
            .builder(RenderPipelines.GUI_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath("custom-crosshair-mod", "pipeline/debug_lines"))
            .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.DEBUG_LINE_STRIP)
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
    }

    @Override
    public TextureSetup textureSetup() {
        return TextureSetup.noTexture();
    }

    @Override
    public @Nullable ScreenRectangle scissorArea() {
        return null;
    }

    @Override
    public ScreenRectangle bounds() {
        var window = Minecraft.getInstance().getWindow();
        return new ScreenRectangle( 0, 0, window.getGuiScaledWidth(), window.getGuiScaledHeight()).transformAxisAligned(pose);
    }
}