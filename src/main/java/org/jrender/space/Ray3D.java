package org.jrender.space;

import java.util.Objects;

public class Ray3D {
    private Vector3D position;
    private Vector3D direction;

    public Ray3D(Vector3D position, Vector3D direction) {
        this.position = position;
        this.direction = direction;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Ray3D{" +
                "position=" + position +
                ", direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray3D ray3D = (Ray3D) o;
        return Objects.equals(position, ray3D.position) && Objects.equals(direction, ray3D.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction);
    }
}
