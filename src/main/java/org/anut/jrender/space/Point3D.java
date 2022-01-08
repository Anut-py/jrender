package org.anut.jrender.space;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Point3D extends Vector3D {
    private Vector3D normal;
    private Set<Point3D> connected;

    public Point3D(double x, double y, double z, Vector3D normal) {
        this(x, y, z, normal, new HashSet<>());
    }

    public Point3D(double x, double y, double z, Vector3D normal, Set<Point3D> connected) {
        super(x, y, z);
        this.normal = normal;
        this.connected = connected;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }

    public Set<Point3D> getConnected() {
        return connected;
    }

    public void setConnected(Set<Point3D> connected) {
        this.connected = connected;
    }

    public void connect(Point3D other) {
        other.connected.add(this);
        connected.add(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return Objects.equals(normal, point3D.normal) && Objects.equals(connected, point3D.connected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(normal, connected);
    }
}
