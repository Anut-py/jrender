package org.anut.jrender.shader.impl;

import org.anut.jrender.shader.Color;
import org.anut.jrender.shader.ColorRay;
import org.anut.jrender.shader.Shader;
import org.anut.jrender.space.Point3D;
import org.anut.jrender.space.Solid3D;

import java.util.function.Consumer;

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
    public void color(Point3D ray, Point3D point, Consumer<ColorRay> next) {
        next.accept(new ColorRay(color, point.getNormal(), roughness));
    }
}
