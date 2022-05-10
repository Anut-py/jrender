package org.jrender.shader;

import org.jrender.space.Face3D;
import org.jrender.space.Ray3D;
import org.jrender.space.Solid3D;
import org.jrender.space.Vector3D;

public interface Shader {
    void init(Solid3D solid);

    ColorRay color(Ray3D ray, Vector3D point, Face3D face);
}
