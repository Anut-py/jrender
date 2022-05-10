package org.jrender.space;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jrender.Constants;

import java.util.*;

public class Face3D {
    private static final Logger log = LogManager.getLogger(Face3D.class);

    private List<Vector3D> vertices;
    private Set<Face3D> triangles;
    private Vector3D normal;

    public Face3D(List<Vector3D> vertices) {
        this.vertices = vertices;

        Vector3D norm = new Vector3D(0, 0, 0);
        int size = vertices.size();
        for (int i = 0; i < vertices.size(); i++) {
            norm.add(
                    VectorUtils.crossProduct(
                            vertices.get(i),
                            vertices.get((i + 1) % size)
                    )
            );
        }
        normal = norm.normalize();
        if (size > 3) {
            triangles = new HashSet<>();
            Vector3D v0 = vertices.get(0);
            for (int i = 1; i < vertices.size() - 1; i ++) {
                triangles.add(new Face3D(v0, vertices.get(i), vertices.get(i + 1)));
            }
        }
    }

    public Face3D(Vector3D... vertices) {
        this(Arrays.asList(vertices));
    }

    public List<Vector3D> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3D> vertices) {
        this.vertices = vertices;

        Vector3D norm = new Vector3D(0, 0, 0);
        int size = vertices.size();
        for (int i = 0; i < vertices.size(); i++) {
            norm.add(
                    VectorUtils.crossProduct(
                            vertices.get(i),
                            vertices.get((i + 1) % size)
                    )
            );
        }
        normal = norm.normalize();
        if (size > 3) {
            triangles = new HashSet<>();
            Vector3D v0 = vertices.get(0);
            for (int i = 1; i < vertices.size() - 1; i ++) {
                triangles.add(new Face3D(v0, vertices.get(i), vertices.get(i + 1)));
            }
        }
    }

    public Vector3D getNormal() {
        return normal;
    }

    public Vector3D collision(Ray3D ray) {
        if (vertices.size() > 3) {
            Vector3D col;
            for (Face3D triangle : triangles) {
                col = triangle.collision(ray);
                if (col != null) return col;
            }
            return null;
        }
        Vector3D v0 = vertices.get(0);
        Vector3D v1 = vertices.get(1);
        Vector3D v2 = vertices.get(2);
        double angle = VectorUtils.dotProduct(normal, ray.getDirection());
        if (Math.abs(angle) < Constants.EPSILON) return null;

        double d = VectorUtils.dotProduct(normal, v0);
        double t = (-VectorUtils.dotProduct(normal, ray.getPosition()) + d) / angle;
        if (t < 0) return null;

        Vector3D intersection = ray.getPosition().copy().add(ray.getDirection().copy().multiply(t));
        Vector3D perpendicular;
        Vector3D edge;
        Vector3D distIntersection;

        edge = v1.copy().subtract(v0);
        distIntersection = intersection.copy().subtract(v0);
        perpendicular = VectorUtils.crossProduct(edge, distIntersection);
        if (VectorUtils.dotProduct(normal, perpendicular) < 0) return null;

        edge = v2.copy().subtract(v1);
        distIntersection = intersection.copy().subtract(v1);
        perpendicular = VectorUtils.crossProduct(edge, distIntersection);
        if (VectorUtils.dotProduct(normal, perpendicular) < 0) return null;

        edge = v0.copy().subtract(v2);
        distIntersection = intersection.copy().subtract(v2);
        perpendicular = VectorUtils.crossProduct(edge, distIntersection);
        if (VectorUtils.dotProduct(normal, perpendicular) < 0) return null;

        return intersection;
    }

    public Ray3D reflection(Ray3D ray) {
        Vector3D col = collision(ray);
        if (col == null) return null;
        return new Ray3D(col, VectorUtils.reflect(ray.getDirection(), normal));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Face3D face3D = (Face3D) o;
        return Objects.equals(vertices, face3D.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }
}
