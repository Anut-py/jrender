package org.jrender.shader.impl;

import org.jrender.shader.Color;
import org.jrender.shader.ColorRay;
import org.jrender.shader.Shader;
import org.jrender.space.*;

public class DiffuseShader implements Shader {
    private Solid3D solid;
    private double roughness;
    private Color color;

    public DiffuseShader(Color color, double roughness) {
        this.roughness = roughness;
        this.color = color;
    }

    public Solid3D getSolid() {
        return solid;
    }

    public void setSolid(Solid3D solid) {
        this.solid = solid;
    }

    public double getRoughness() {
        return roughness;
    }

    public void setRoughness(double roughness) {
        this.roughness = roughness;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void init(Solid3D solid) {
        this.solid = solid;
    }

    @Override
    public ColorRay color(Ray3D ray, Vector3D point, Face3D face) {
        return new ColorRay(color, new Ray3D(point, VectorUtils.reflect(ray.getDirection(), face.getNormal())), roughness);
    }
}
