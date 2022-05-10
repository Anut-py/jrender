package org.jrender.light.impl;

import org.jrender.light.Light;
import org.jrender.shader.Color;
import org.jrender.shader.ColorUtils;
import org.jrender.space.Face3D;
import org.jrender.space.Vector3D;
import org.jrender.space.VectorUtils;

public class PointLight implements Light {
    private double intensity;
    private Vector3D position;

    public PointLight(double intensity, Vector3D position) {
        this.intensity = intensity;
        this.position = position;
    }

    @Override
    public Color illuminate(Face3D face, Vector3D position, Color originalColor) {
        double mul = VectorUtils.dotProduct(face.getNormal(), this.position.copy().subtract(position).normalize()) * intensity;
        return ColorUtils.removeExtra(
                ColorUtils.multiply(
                        originalColor,
                        Math.max(mul, 0.1)
                )
        );
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }
}
