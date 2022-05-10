package org.jrender.shader;

import org.jrender.space.Ray3D;

public class ColorRay {
    private Color color;
    private Ray3D ray;
    private double blendFactor; // 1 = color; 0 = otherColor

    public ColorRay(Color color, Ray3D ray, double blendFactor) {
        this.color = color;
        this.ray = ray;
        this.blendFactor = blendFactor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Ray3D getRay() {
        return ray;
    }

    public void setRay(Ray3D ray) {
        this.ray = ray;
    }

    public double getBlendFactor() {
        return blendFactor;
    }

    public void setBlendFactor(double blendFactor) {
        this.blendFactor = blendFactor;
    }
}
