package org.jrender.scene;

import org.jrender.space.Ray3D;
import org.jrender.space.Vector3D;

public class Camera {
    private Vector3D origin;
    private Vector3D topLeft;
    private Vector3D topRight;
    private Vector3D bottomLeft;
    private Vector3D horizontal, vertical;
    // rotation in radians
    private double rotationZ;
    private double rotationX;
    private double width;
    private double height;
    private double lensDistance;

    public Camera(int width, int height, double lensDistance) {
        origin = new Vector3D(0, 0, 0);
        rotationX = 0;
        rotationZ = 0;
        this.width = width;
        this.height = height;
        this.lensDistance = lensDistance;
        initCorners();
    }

    public Camera(int width, int height, double lensDistance, Vector3D origin, double rotationX, double rotationZ) {
        this(width, height, lensDistance);
        this.origin = origin;
        this.rotationZ = rotationZ;
        this.rotationX = rotationX;
        this.width = width;
        this.height = height;
    }

    public double getLensDistance() {
        return lensDistance;
    }

    public void setLensDistance(double lensDistance) {
        this.lensDistance = lensDistance;
    }

    public Ray3D getRay(int x, int y) {
        Vector3D direction = topLeft.copy()
                .add(horizontal.copy().multiply(x / width))
                .add(vertical.copy().multiply(y / height));
        double sin = Math.sin(rotationX);
        double cos = Math.cos(rotationX);
        double origX = direction.getX();
        double origY = direction.getY();
        double origZ = direction.getZ();
        direction.setY(origY * cos - origZ * sin);
        direction.setZ(origY * sin + origZ * cos);
        sin = Math.sin(rotationZ);
        cos = Math.cos(rotationZ);
        origY = direction.getY();
        direction.setX(origX * cos - origY * sin);
        direction.setY(origX * sin + origY * cos);
        return new Ray3D(origin, direction);
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    public double getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(double rotationZ) {
        this.rotationZ = rotationZ;
    }

    public double getRotationX() {
        return rotationX;
    }

    public void setRotationX(double rotationX) {
        this.rotationX = rotationX;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        initCorners();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        initCorners();
    }

    private void initCorners() {
        double halfWidth = width / height / 2;
        topLeft = new Vector3D(-halfWidth, lensDistance, 0.5);
        topRight = new Vector3D(halfWidth, lensDistance, 0.5);
        bottomLeft = new Vector3D(-halfWidth, lensDistance, -0.5);
        horizontal = topRight.copy().subtract(topLeft);
        vertical = bottomLeft.copy().subtract(topLeft);
    }
}
