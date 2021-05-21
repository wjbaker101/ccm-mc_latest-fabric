package com.wjbaker.ccm.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wjbaker.ccm.render.type.GuiBounds;
import com.wjbaker.ccm.render.type.IDrawInsideWindowCallback;
import com.wjbaker.ccm.type.RGBA;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public final class RenderManager {

    private void setGlProperty(final int property, final boolean isEnabled) {
        if (isEnabled)
            GL11.glEnable(property);
        else
            GL11.glDisable(property);
    }

    private void setColour(final RGBA colour) {
        GL11.glColor4f(
            colour.getRed() / 255.0F,
            colour.getGreen() / 255.0F,
            colour.getBlue() / 255.0F,
            colour.getOpacity() / 255.0F);
    }

    private void preRender() {
        GL11.glPushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
    }

    private void postRender() {
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        GL11.glPopMatrix();
    }

    public void drawLines(float[] points, final float thickness, final RGBA colour) {
        this.setGlProperty(GL11.GL_LINE_SMOOTH, false);

        GL11.glLineWidth(thickness);

        float r = colour.getRed() / 255.0F;
        float g = colour.getGreen() / 255.0F;
        float b = colour.getBlue() / 255.0F;
        float a = colour.getOpacity() / 255.0F;

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_LINES, VertexFormats.POSITION_COLOR);

        this.preRender();

        for (int i = 0; i < points.length; i += 2) {
            bufferBuilder
                .vertex(points[i], points[i + 1], 0.0F)
                .color(r, g, b, a)
                .next();
        }

        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

        this.postRender();
    }

    public void drawFilledShape(final float[] points, final RGBA colour) {
        this.setGlProperty(GL11.GL_LINE_SMOOTH, false);

        float r = colour.getRed() / 255.0F;
        float g = colour.getGreen() / 255.0F;
        float b = colour.getBlue() / 255.0F;
        float a = colour.getOpacity() / 255.0F;

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_POLYGON, VertexFormats.POSITION_COLOR);

        this.preRender();

        for (int i = 0; i < points.length; i += 2) {
            bufferBuilder
                .vertex(points[i], points[i + 1], 0.0F)
                .color(r, g, b, a)
                .next();
        }

        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

        this.postRender();
    }

    public void drawLine(
        final float x1, final float y1,
        final float x2, final float y2,
        final float thickness,
        final RGBA colour) {

        this.drawLines(new float[] {
            x1, y1,
            x2, y2
        }, thickness, colour);
    }

    public void drawRectangle(
        final float x1, final float y1,
        final float x2, final float y2,
        final float thickness,
        final RGBA colour) {

        this.drawLines(new float[] {
            x1, y1, x2, y1,
            x2, y1, x2, y2,
            x1, y2, x2, y2,
            x1, y1, x1, y2
        }, thickness, colour);
    }

    public void drawFilledRectangle(
        final float x1, final float y1,
        final float x2, final float y2,
        final RGBA colour) {

        this.drawFilledShape(new float[] {
            x1, y1,
            x1, y2,
            x2, y2,
            x2, y1
        }, colour);
    }

    public void drawBorderedRectangle(
        final float x1, final float y1,
        final float x2, final float y2,
        final float borderThickness,
        final RGBA borderColour,
        final RGBA fillColour) {

        this.drawFilledRectangle(x1, y1, x2, y2, fillColour);
        this.drawRectangle(x1, y1, x2, y2, borderThickness, borderColour);
    }

    public void drawPartialCircle(
        final float x, final float y,
        final float radius,
        final int startAngleAt,
        final int endAngleAt,
        final float thickness,
        final RGBA colour) {

        this.setGlProperty(GL11.GL_LINE_SMOOTH, true);

        int startAngle = Math.max(0, Math.min(startAngleAt, endAngleAt));
        int endAngle = Math.min(360, Math.max(startAngleAt, endAngleAt));

        GL11.glLineWidth(thickness);

        float ratio = (float)Math.PI / 180.F;

        float r = colour.getRed() / 255.0F;
        float g = colour.getGreen() / 255.0F;
        float b = colour.getBlue() / 255.0F;
        float a = colour.getOpacity() / 255.0F;

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_LINES, VertexFormats.POSITION_COLOR);

        this.preRender();

        for (int i = startAngle; i <= endAngle; ++i) {
            float radians = (i - 90) * ratio;

            bufferBuilder
                .vertex(x + (float)Math.cos(radians) * radius, y + (float)Math.sin(radians) * radius, 0.0F)
                .color(r, g, b, a)
                .next();
        }

        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

        this.postRender();
    }

    public void drawCircle(
        final float x, final float y,
        final float radius,
        final float thickness,
        final RGBA colour) {

        this.drawPartialCircle(x, y, radius, 0, 360, thickness, colour);
    }

    public void drawFilledCircle(
        final float x, final float y,
        final float radius,
        final RGBA colour) {

        float[] points = new float[361 * 2];

        float ratio = (float)Math.PI / 180.F;

        for (int i = 0; i <= 360; ++i) {
            float radians = (i - 90) * ratio;

            points[i * 2] = x + (float)Math.cos(radians) * radius;
            points[i * 2 + 1] = y + (float)Math.sin(radians) * radius;
        }

        this.drawFilledShape(points, colour);
    }

    public void drawTorus(
        final int x, final int y,
        final int innerRadius,
        final int outerRadius,
        final RGBA colour) {

        this.setGlProperty(GL11.GL_LINE_SMOOTH, true);
        this.setColour(colour);

        float ratio = (float)Math.PI / 180.F;

        float r = colour.getRed() / 255.0F;
        float g = colour.getGreen() / 255.0F;
        float b = colour.getBlue() / 255.0F;
        float a = colour.getOpacity() / 255.0F;

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_LINES, VertexFormats.POSITION_COLOR);

        this.preRender();

        for (int i = 0; i <= 360; ++i) {
            float radians = (i - 90) * ratio;

            bufferBuilder
                .vertex(x + (float)Math.cos(radians) * innerRadius, y + (float)Math.sin(radians) * innerRadius, 0.0F)
                .color(r, g, b, a)
                .next();

            bufferBuilder
                .vertex(x + (float)Math.cos(radians) * outerRadius, y + (float)Math.sin(radians) * outerRadius, 0.0F)
                .color(r, g, b, a)
                .next();
        }

        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

        this.postRender();
    }

    public void drawText(final String text, final int x, final int y, final RGBA colour, final boolean hasShadow) {
        int colourAsInt = this.rgbaAsInt(colour);

        if (hasShadow)
            MinecraftClient.getInstance().textRenderer.drawWithShadow(new MatrixStack(), text, x, y, colourAsInt);
        else
            MinecraftClient.getInstance().textRenderer.draw(new MatrixStack(), text, x, y, colourAsInt);
    }

    public void drawSmallText(final String text, final int x, final int y, final RGBA colour, final boolean hasShadow) {
        GL11.glScalef(0.5F, 0.5F, 1.0F);
        this.drawText(text, x * 2, y * 2, colour, hasShadow);
        GL11.glScalef(2.0F, 2.0F, 1.0F);
    }

    public void drawBigText(final String text, final int x, final int y, final RGBA colour, final boolean hasShadow) {
        GL11.glScalef(1.5F, 1.5F, 1.0F);
        this.drawText(text, (int)(x * 0.666F), (int)(y * 0.666F), colour, hasShadow);

        float scale = 1 / 1.5F;
        GL11.glScalef(scale, scale, 1.0F);
    }

    private int rgbaAsInt(final RGBA colour) {
        return (colour.getOpacity() << 24) + (colour.getRed() << 16) + (colour.getGreen() << 8) + colour.getBlue();
    }

    public int textWidth(final String text) {
        return MinecraftClient.getInstance().textRenderer.getWidth(text);
    }

    public void drawInsideBounds(final GuiBounds bounds, final IDrawInsideWindowCallback callback) {
        this.setGlProperty(GL11.GL_SCISSOR_TEST, true);

        Window window = MinecraftClient.getInstance().getWindow();
        double scale = window.getScaleFactor();

        GL11.glScissor(
            (int)Math.round(bounds.x() * scale),
            (int)Math.round(window.getHeight() - (bounds.y() * scale) - (bounds.height() * scale)),
            (int)Math.round(bounds.width() * scale),
            (int)Math.round(bounds.height() * scale));

        callback.draw();

        this.setGlProperty(GL11.GL_SCISSOR_TEST, false);
    }
}
