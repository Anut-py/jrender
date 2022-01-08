package org.anut.jrender.space;

import org.anut.jrender.shader.Shader;

import java.util.Set;

public class Solid3D {
    private Set<Point3D> points;
    private Shader shader;

    public Solid3D(Set<Point3D> points, Shader shader) {
        this.points = points;
        this.shader = shader;
    }

    public Set<Point3D> getPoints() {
        return points;
    }

    public void setPoints(Set<Point3D> points) {
        this.points = points;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }
}
