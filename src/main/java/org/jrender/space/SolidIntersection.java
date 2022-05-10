package org.jrender.space;

import java.util.Objects;

public class SolidIntersection {
    private Solid3D solid;
    private Face3D face;
    private Vector3D point;

    public SolidIntersection(Solid3D solid, Face3D face, Vector3D point) {
        this.solid = solid;
        this.face = face;
        this.point = point;
    }

    public Solid3D getSolid() {
        return solid;
    }

    public void setSolid(Solid3D solid) {
        this.solid = solid;
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
        SolidIntersection that = (SolidIntersection) o;
        return Objects.equals(solid, that.solid) && Objects.equals(face, that.face) && Objects.equals(point, that.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solid, face, point);
    }
}
