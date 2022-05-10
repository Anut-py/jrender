package org.jrender.space;

public final class VectorUtils {
    private VectorUtils() {
    }

    public static double distance(Vector3D one, Vector3D two) {
        double diffX = one.getX() - two.getX();
        double diffY = one.getY() - two.getY();
        double diffZ = one.getZ() - two.getZ();
        return Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
    }

    public static Vector3D sum(Vector3D... vectors) {
        Vector3D toReturn = new Vector3D(0, 0, 0);
        for (Vector3D v : vectors) {
            toReturn.add(v);
        }
        return toReturn;
    }

    public static Vector3D crossProduct(Vector3D first, Vector3D second) {
        return new Vector3D(
                first.getY() * second.getZ() - first.getZ() * second.getY(),
                first.getZ() * second.getX() - first.getX() * second.getZ(),
                first.getX() * second.getY() - first.getY() * second.getX()
        );
    }

    public static double dotProduct(Vector3D first, Vector3D second) {
        return first.getX() * second.getX() +
                first.getY() * second.getY() +
                first.getZ() * second.getZ();
    }

    public static Vector3D reflect(Vector3D incoming, Vector3D normal) {
        incoming = incoming.copy();
        normal = normal.copy();
        return incoming.subtract(normal.multiply(2 * dotProduct(incoming, normal)));
    }
}
