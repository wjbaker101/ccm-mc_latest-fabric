package com.wjbaker.ccm.crosshair.custom;

import com.wjbaker.ccm.CustomCrosshairMod;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public final class CustomCrosshairDrawer {

    private final String file = "crosshair_drawn.ccmpng";

    private int width;
    private int height;
    private int[][] pixels;

    public CustomCrosshairDrawer() {
        this.reset(17, 17);
    }

    private void reset(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[this.width][this.height];

        for (var row : this.pixels) {
            Arrays.fill(row, 0);
        }
    }

    public void togglePixel(final int x, final int y) {
        var currentPixel = this.pixels[x][y];
        var newPixel = currentPixel == 0 ? 1 : 0;

        this.pixels[x][y] = newPixel;
    }

    public void loadImage() {
        try {
            CustomCrosshairMod.INSTANCE.log("Drawn Crosshair (Read)", "Started reading drawn crosshair image.");

            var image = ImageIO.read(new File(this.file));

            this.reset(image.getWidth(), image.getHeight());

            for (var x = 0; x < this.width; ++x)
            for (var y = 0; y < this.height; ++y) {
                if (Math.abs(image.getRGB(x, y)) > 1) {
                    this.togglePixel(x, y);
                }
            }

            CustomCrosshairMod.INSTANCE.log("Drawn Crosshair (Read)", "Finished reading drawn crosshair image.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveImage() {
        try {
            CustomCrosshairMod.INSTANCE.log("Drawn Crosshair (Write)", "Started saving drawn crosshair image.");

            var bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
            var graphics = bufferedImage.createGraphics();

            graphics.setPaint(Color.black);

            for (var x = 0; x < this.width; ++x)
            for (var y = 0; y < this.height; ++y) {
                if (this.pixels[x][y] == 1) {
                    graphics.drawRect(x, y, 0, 0);
                }
            }

            ImageIO.write(bufferedImage, "PNG", new File(this.file));

            CustomCrosshairMod.INSTANCE.log("Drawn Crosshair (Write)", "Finished saving drawn crosshair image.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getAt(final int x, final int y) {
        return this.pixels[x][y];
    }
}