package org.anut.jrender.shader;

import org.anut.jrender.space.Point3D;
import org.anut.jrender.space.Solid3D;
import org.anut.jrender.space.Vector3D;

import java.util.function.Consumer;

public interface Shader {
    void init(Solid3D solid);

    void color(Point3D ray, Point3D point, Consumer<ColorRay> next);
}
