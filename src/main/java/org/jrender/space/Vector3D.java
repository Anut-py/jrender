package org.jrender.space;

import java.util.Objects;

public class Vector3D {
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3D normalize() {
        return divide(Math.sqrt(x * x + y * y + z * z));
    }

    public Vector3D add(Vector3D v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public Vector3D subtract(Vector3D v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    public Vector3D multiply(double fac) {
        x *= fac;
        y *= fac;
        z *= fac;
        return this;
    }

    public Vector3D divide(double fac) {
        x /= fac;
        y /= fac;
        z /= fac;
        return this;
    }

    public Vector3D copy() {
        return new Vector3D(x, y, z);
    }

    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3D vector3D = (Vector3D) o;
        return Double.compare(vector3D.x, x) == 0 && Double.compare(vector3D.y, y) == 0 && Double.compare(vector3D.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
