package org.jrender.shader;

public final class ColorUtils {
    private ColorUtils() {
    }

    public static Color multiply(Color original, double factor) {
        return new Color(original.getR() * factor, original.getG() * factor, original.getB() * factor);
    }

    public static Color removeExtra(Color original) {
        double r = Math.min(255, original.getR());
        double g = Math.min(255, original.getG());
        double b = Math.min(255, original.getB());
        return new Color(r, g, b);
    }
}
