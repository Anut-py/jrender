package org.anut.jrender.space;

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

    public void normalize() {
        divide(1 / Math.sqrt(x * x + y * y + z * z));
    }

    public void add(Vector3D v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void subtract(Vector3D v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public void multiply(double fac) {
        x *= fac;
        y *= fac;
        z *= fac;
    }

    public void divide(double fac) {
        x /= fac;
        y /= fac;
        z /= fac;
    }

    public Vector3D copy() {
        return new Vector3D(x, y, z);
    }
}
