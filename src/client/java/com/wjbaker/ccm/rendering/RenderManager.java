package com.wjbaker.ccm.rendering;

import com.wjbaker.ccm.crosshair.custom.CustomCrosshairDrawer;
import com.wjbaker.ccm.gui.types.GuiBounds;
import com.wjbaker.ccm.gui.types.IDrawInsideWindowCallback;
import com.wjbaker.ccm.rendering.types.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public final class RenderManager {

    private void setGlProperty(final int property, final boolean isEnabled) {
        if (isEnabled)
            GL11.glEnable(property);
        else
            GL11.glDisable(property);
    }

    public void drawLines(final GuiGraphicsExtractor graphics, Float[] points, final float thickness, final RGBA colour) {
        this.drawLines(graphics, points, thickness, colour, false);
    }

    public void drawLines(
        final GuiGraphicsExtractor graphics,
        Float[] points,
        final float thickness,
        final RGBA colour,
        final boolean isBlendEnabled) {

        graphics.guiRenderState.addGuiElement(new ModLinesGuiElementRenderState(new Matrix3x2f(graphics.pose()), points, colour));
    }

    public void drawFilledShape(final GuiGraphicsExtractor graphics, final float[] points, final RGBA colour) {
        this.drawFilledShape(graphics, points, colour, false);
    }

    public void drawFilledShape(
        final GuiGraphicsExtractor graphics,
        final float[] points,
        final RGBA colour,
        final boolean isBlendEnabled) {

         graphics.guiRenderState.addGuiElement(new ModFilledGuiElementRenderState(new Matrix3x2f(graphics.pose()), points, colour));
    }

    public void drawLine(
        final GuiGraphicsExtractor graphics,
        final float x1, final float y1,
        final float x2, final float y2,
        final float thickness,
        final RGBA colour) {

        this.drawLines(graphics, new Float[] {
            x1, y1,
            x2, y2
        }, thickness, colour);
    }

    public void drawRectangle(
        final GuiGraphicsExtractor graphics,
        final float x1, final float y1,
        final float x2, final float y2,
        final float thickness,
        final RGBA colour) {

        this.drawLines(graphics, new Float[] {
            x1, y1,
            x2, y1,
            x2, y2,
            x1, y2,
            x1, y1,
        }, thickness, colour);
    }

    public void drawFilledRectangle(
        final GuiGraphicsExtractor graphics,
        final float x1, final float y1,
        final float x2, final float y2,
        final RGBA colour) {

        this.drawFilledRectangle(graphics, x1, y1, x2, y2, colour, false);
    }

    public void drawFilledRectangle(
        final GuiGraphicsExtractor graphics,
        final float x1, final float y1,
        final float x2, final float y2,
        final RGBA colour,
        final boolean isBlend) {

        this.drawFilledShape(graphics, new float[] {
            x1, y1,
            x1, y2,
            x2, y2,
            x2, y1
        }, colour, isBlend);
    }

    public void drawBorderedRectangle(
        final GuiGraphicsExtractor graphics,
        final float x1, final float y1,
        final float x2, final float y2,
        final float borderThickness,
        final RGBA borderColour,
        final RGBA fillColour) {

        this.drawBorderedRectangle(graphics, x1, y1, x2, y2, borderThickness, borderColour, fillColour, false);
    }

    public void drawBorderedRectangle(
        final GuiGraphicsExtractor graphics,
        final float x1, final float y1,
        final float x2, final float y2,
        final float borderThickness,
        final RGBA borderColour,
        final RGBA fillColour,
        final boolean isBlend) {

        this.drawFilledRectangle(graphics, x1, y1, x2, y2, fillColour, isBlend);
        this.drawRectangle(graphics, x1, y1, x2, y2, borderThickness, borderColour);
    }

    public void drawPartialCircle(
        final GuiGraphicsExtractor graphics,
        final float x, final float y,
        final float radius,
        final int startAngleAt,
        final int endAngleAt,
        final float thickness,
        final RGBA colour) {

        var startAngle = Math.max(0, Math.min(startAngleAt, endAngleAt));
        var endAngle = Math.min(360, Math.max(startAngleAt, endAngleAt));
        var ratio = (float)Math.PI / 180.F;

        var points = new ArrayList<Float>();
        for (var i = startAngle; i <= endAngle; ++i) {
            var radians = (i - 90) * ratio;

            points.add(x + (float)Math.cos(radians) * radius);
            points.add(y + (float)Math.sin(radians) * radius);
        }

        graphics.guiRenderState.addGuiElement(new ModLinesGuiElementRenderState(new Matrix3x2f(graphics.pose()), points.toArray(new Float[0]), colour));
    }

    public void drawCircle(
        final GuiGraphicsExtractor graphics,
        final float x, final float y,
        final float radius,
        final float thickness,
        final RGBA colour) {

        this.drawPartialCircle(graphics, x, y, radius, 0, 360, thickness, colour);
    }

    public void drawTorus(
        final Matrix3x2fStack matrixStack,
        final int x, final int y,
        final int innerRadius,
        final int outerRadius,
        final RGBA colour) {

//        var ratio = (float)Math.PI / 180.F;
//
//        var bufferBuilders = Minecraft.getInstance().getBufferBuilders();
//        var immediate = bufferBuilders.getEntityVertexConsumers();
//        var vertexConsumer = immediate.getBuffer(RenderLayer.getDebugQuads());
//
//        for (var i = 0; i <= 360; ++i) {
//            var radians = (i - 90) * ratio;
//
//            vertexConsumer
//                .vertex(matrixStack, x + (float)Math.cos(radians) * innerRadius, y + (float)Math.sin(radians) * innerRadius, 0.0F)
//                .color(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getOpacity())
//                .normal(1.0F, 0.0F, 0.0F);
//
//            vertexConsumer
//                .vertex(matrixStack, x + (float)Math.cos(radians) * outerRadius, y + (float)Math.sin(radians) * outerRadius, 0.0F)
//                .color(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getOpacity())
//                .normal(1.0F, 0.0F, 0.0F);
//        }
//
//        immediate.drawCurrentLayer();
    }

    public void drawImage(
        final GuiGraphicsExtractor graphics,
        final int x, final int y,
        final CustomCrosshairDrawer image,
        final RGBA colour,
        final boolean isCentered) {

        var offsetX = isCentered ? image.getWidth() / 2.0F : 0;
        var offsetY = isCentered ? image.getHeight() / 2.0F : 0;

        var width = image.getWidth();
        var height = image.getHeight();

        for (int imageX = 0; imageX < width; ++imageX) {
            for (int imageY = 0; imageY < height; ++imageY) {
                if (image.getAt(imageX, imageY) == 1) {
                    var drawX = x + imageX - offsetX;
                    var drawY = y + imageY - offsetY;

                    this.drawFilledRectangle(graphics, drawX, drawY, drawX + 1, drawY + 1, colour);
                }
            }
        }
    }

    public void drawText(final GuiGraphicsExtractor graphics, final String text, final int x, final int y, final RGBA colour, final boolean hasShadow) {
        var colourAsInt = this.rgbaAsInt(colour);

        if (hasShadow)
            graphics.text(Minecraft.getInstance().font, text, x, y, colourAsInt, true);
        else
            graphics.text(Minecraft.getInstance().font, text, x, y, colourAsInt, false);
    }

    public void drawSmallText(final GuiGraphicsExtractor graphics, final String text, final int x, final int y, final RGBA colour, final boolean hasShadow) {
        var matrixStack = graphics.pose();
        matrixStack.pushMatrix();
        matrixStack.scale(0.5F, 0.5F);

        this.drawText(graphics, text, x * 2, y * 2, colour, hasShadow);
        matrixStack.popMatrix();
    }

    public void drawBigText(final GuiGraphicsExtractor graphics, final String text, final int x, final int y, final RGBA colour, final boolean hasShadow) {
        var matrixStack = graphics.pose();
        matrixStack.pushMatrix();
        matrixStack.scale(1.5F, 1.5F);

        this.drawText(graphics, text, (int)(x * 0.666F), (int)(y * 0.666F), colour, hasShadow);
        matrixStack.popMatrix();
    }

    private int rgbaAsInt(final RGBA colour) {
        return (colour.getOpacity() << 24) + (colour.getRed() << 16) + (colour.getGreen() << 8) + colour.getBlue();
    }

    public int textWidth(final String text) {
        return Minecraft.getInstance().font.width(text);
    }

    public void drawInsideBounds(final GuiBounds bounds, final IDrawInsideWindowCallback callback) {
        this.setGlProperty(GL11.GL_SCISSOR_TEST, true);

        var window = Minecraft.getInstance().getWindow();
        var scale = window.getGuiScale();

        GL11.glScissor(
            Math.round(bounds.x() * scale),
            Math.round(window.getHeight() - (bounds.y() * scale) - (bounds.height() * scale)),
            Math.round(bounds.width() * scale),
            Math.round(bounds.height() * scale));

        callback.draw();

        this.setGlProperty(GL11.GL_SCISSOR_TEST, false);
    }
}