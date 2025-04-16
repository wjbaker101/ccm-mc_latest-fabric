package com.wjbaker.ccm.crosshair.styles;

import com.mojang.blaze3d.buffers.BufferType;
import com.mojang.blaze3d.buffers.BufferUsage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.computed.ComputedProperties;
import com.wjbaker.ccm.crosshair.types.CrosshairStyle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public final class DebugStyle extends CrosshairStyle {

    public DebugStyle(final MatrixStack matrixStack, final CustomCrosshair crosshair) {
        super(matrixStack, crosshair);
    }

    @Override
    public void draw(final DrawContext drawContext, final int x, final int y, final ComputedProperties computedProperties) {
        var camera = this.mc.gameRenderer.getCamera();

        var matrixStack = RenderSystem.getModelViewStack();
        matrixStack.pushMatrix();
        matrixStack.translate(x, y, 0.0F);
        matrixStack.rotateX(-camera.getPitch() * 0.017453292F);
        matrixStack.rotateY(camera.getYaw() * 0.017453292F);
        matrixStack.scale(-this.crosshair.scale.get() / 100F, -this.crosshair.scale.get() / 100F, -this.crosshair.scale.get() / 100F);

        try (var bufferAllocator = new BufferAllocator(VertexFormats.POSITION_COLOR_NORMAL.getVertexSize() * 12)) {
            var bufferBuilder = new BufferBuilder(bufferAllocator, VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR_NORMAL);
            bufferBuilder.vertex(0.0F, 0.0F, 0.0F).color(-65536).normal(1.0F, 0.0F, 0.0F);
            bufferBuilder.vertex(10.0F, 0.0F, 0.0F).color(-65536).normal(1.0F, 0.0F, 0.0F);
            bufferBuilder.vertex(0.0F, 0.0F, 0.0F).color(-16711936).normal(0.0F, 1.0F, 0.0F);
            bufferBuilder.vertex(0.0F, 10.0F, 0.0F).color(-16711936).normal(0.0F, 1.0F, 0.0F);
            bufferBuilder.vertex(0.0F, 0.0F, 0.0F).color(-8421377).normal(0.0F, 0.0F, 1.0F);
            bufferBuilder.vertex(0.0F, 0.0F, 10.0F).color(-8421377).normal(0.0F, 0.0F, 1.0F);

            try (var builtBuffer = bufferBuilder.end()) {
                var vertexBuffer = RenderSystem.getDevice().createBuffer(() -> "Custom Crosshair Mod vertex buffer", BufferType.VERTICES, BufferUsage.STATIC_WRITE, builtBuffer.getBuffer());
                var buffer = RenderSystem.getSequentialBuffer(VertexFormat.DrawMode.LINES);

                var renderPipeline = RenderPipelines.LINES;
                RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);

                var framebuffer = MinecraftClient.getInstance().getFramebuffer();
                var gpuTexture = framebuffer.getColorAttachment();
                var gpuTexture2 = framebuffer.getDepthAttachment();
                var gpuBuffer = buffer.getIndexBuffer(18);

                try (var renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(gpuTexture, OptionalInt.empty(), gpuTexture2, OptionalDouble.empty())) {
                    renderPass.setPipeline(renderPipeline);
                    RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                    RenderSystem.lineWidth(4.0F);
                    renderPass.setVertexBuffer(0, vertexBuffer);
                    renderPass.setIndexBuffer(gpuBuffer, buffer.getIndexType());
                    renderPass.drawIndexed(0, 18);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderSystem.lineWidth(2.0F);
                    renderPass.drawIndexed(0, 18);
                    RenderSystem.lineWidth(1.0F);
                }
            }
        }

        matrixStack.popMatrix();
    }
}