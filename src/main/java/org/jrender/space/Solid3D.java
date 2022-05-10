package org.jrender.space;

import org.jrender.shader.Shader;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Solid3D {
    private Set<Face3D> faces;
    private Shader shader;

    public Solid3D(Set<Face3D> faces, Shader shader) {
        this.faces = faces;
        this.shader = shader;
    }

    public Set<Intersection> collisions(Ray3D ray) {
        Set<Intersection> toReturn = new HashSet<>();
        for (Face3D face : faces) {
            Vector3D col = face.collision(ray);
            if (col != null) toReturn.add(new Intersection(face, col));
        }
        return toReturn;
    }

    public Set<Face3D> getFaces() {
        return faces;
    }

    public void setFaces(Set<Face3D> faces) {
        this.faces = faces;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solid3D solid3D = (Solid3D) o;
        return Objects.equals(faces, solid3D.faces) && Objects.equals(shader, solid3D.shader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faces, shader);
    }
}
