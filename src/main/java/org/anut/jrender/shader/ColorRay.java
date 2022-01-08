package org.anut.jrender.shader;

import org.anut.jrender.space.Vector3D;

public class ColorRay {
    private Color color;
    private Vector3D direction;
    private double blendFactor; // 1 = color; 0 = otherColor

    public ColorRay(Color color, Vector3D direction, double blendFactor) {
        this.color = color;
        this.direction = direction;
        this.blendFactor = blendFactor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    public double getBlendFactor() {
        return blendFactor;
    }

    public void setBlendFactor(double blendFactor) {
        this.blendFactor = blendFactor;
    }
}
