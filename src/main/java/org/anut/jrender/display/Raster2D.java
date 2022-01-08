package org.anut.jrender.display;

import java.awt.image.WritableRaster;

public class Raster2D {
    private final Window window;
    private final WritableRaster raster;
    private final int width;
    private final int height;

    public Raster2D(Window window) {
        this.window = window;
        raster = window.getRaster();
        width = window.getWinWidth();
        height = window.getWinHeight();
    }

    public void write(int x, int y, double[] rgb) {
        raster.setPixel(x, y, rgb);
    }
}
