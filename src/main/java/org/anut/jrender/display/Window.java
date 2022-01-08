package org.anut.jrender.display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Window extends JPanel {
    private final transient BufferedImage buffer;
    private final transient WritableRaster raster;
    private final JFrame frame;
    private final int winWidth;
    private final int winHeight;
    private transient Graphics graphics;
    private volatile boolean updating;

    public Window(int winWidth, int winHeight, String title) {
        this.winWidth = winWidth;
        this.winHeight = winHeight;
        buffer = new BufferedImage(winWidth, winHeight, BufferedImage.TYPE_INT_RGB);
        raster = buffer.getRaster();
        frame = new JFrame(title);
        frame.setBounds(0, 0, winWidth, winHeight);
        setBounds(0, 0, winWidth, winHeight);
        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        updating = false;
    }

    public void showWindow() {
        frame.setVisible(true);
        setVisible(true);
        graphics = getGraphics();
    }

    public void updateLoop() {
        updating = true;
        while (updating) {
            graphics.drawImage(buffer, 0, 0, winWidth, winHeight, null);
        }
    }

    public void stopUpdateLoop() {
        updating = false;
    }

    public int getWinWidth() {
        return winWidth;
    }

    public int getWinHeight() {
        return winHeight;
    }

    public WritableRaster getRaster() {
        return raster;
    }
}
