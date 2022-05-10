package org.jrender.space;

import java.util.Objects;

public class Intersection {
    private Face3D face;
    private Vector3D point;

    public Intersection(Face3D face, Vector3D point) {
        this.face = face;
        this.point = point;
    }

    public Face3D getFace() {
        return face;
    }

    public void setFace(Face3D face) {
        this.face = face;
    }

    public Vector3D getPoint() {
        return point;
    }

    public void setPoint(Vector3D point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intersection that = (Intersection) o;
        return Objects.equals(face, that.face) && Objects.equals(point, that.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(face, point);
    }
}
