package org.jrender.light;

import org.jrender.shader.Color;
import org.jrender.space.Face3D;
import org.jrender.space.Vector3D;

public interface Light {
    Color illuminate(Face3D face, Vector3D position, Color originalColor);
}
