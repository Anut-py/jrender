package org.jrender.scene;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jrender.light.Light;
import org.jrender.shader.Color;
import org.jrender.shader.ColorRay;
import org.jrender.space.*;
import org.jrender.tasks.TaskQueue;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

public class Scene {
    private static final Logger log = LogManager.getLogger(Scene.class);

    private Set<Solid3D> solids;
    private Light light;
    private Camera camera;
    private final TaskQueue taskQueue;

    public Scene(Set<Solid3D> solids, Light light, Camera camera, int numThreads) {
        this.solids = solids;
        this.light = light;
        this.camera = camera;
        this.taskQueue = new TaskQueue(numThreads);
    }

    public Set<Solid3D> getSolids() {
        return solids;
    }

    public void setSolids(Set<Solid3D> solids) {
        this.solids = solids;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Set<SolidIntersection> collisions(Ray3D ray) {
        Set<SolidIntersection> toReturn = new HashSet<>();
        for (Solid3D solid : solids) {
            for (Intersection intersection : solid.collisions(ray))
                toReturn.add(
                        new SolidIntersection(
                                solid,
                                intersection.getFace(),
                                intersection.getPoint()));
        }
        return toReturn;
    }

    public void render(int blockSize) {
        int height = (int) camera.getHeight();
        int width = (int) camera.getWidth();
        glBegin(GL_POINTS);
        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                int finalX = x;
                int finalY = y;
                taskQueue.submitTask(() -> {
                    Ray3D ray = camera.getRay(finalX, finalY);
                    SolidIntersection closest = null;
                    double distance = Double.MAX_VALUE;
                    for (SolidIntersection si : collisions(ray)) {
                        double tempDist = VectorUtils.distance(si.getPoint(), camera.getOrigin());
                        if (tempDist < distance) {
                            closest = si;
                            distance = tempDist;
                        }
                    }
                    if (closest != null) {
                        ColorRay colRay = closest.getSolid().getShader().color(ray, closest.getPoint(), closest.getFace());
                        Color col = light.illuminate(closest.getFace(), closest.getPoint(), colRay.getColor());
                        glColor3d(col.getR() / 255.0, col.getG() / 255.0, col.getB() / 255.0);
                        for (int j = 0; j < blockSize && finalY + j < height; j++)
                            for (int k = 0; k < blockSize && finalX + k < width; k++)
                                glVertex2d(finalX + (double) k, finalY + (double) j);
                    } else {
                        glColor3d(135, 206, 235);
                        for (int j = 0; j < blockSize && finalY + j < height; j++)
                            for (int k = 0; k < blockSize && finalX + k < width; k++)
                                glVertex2d(finalX + (double) k, finalY + (double) j);
                    }
                });
            }
        }
        try {
            taskQueue.awaitCompletion();
            glEnd();
        } catch (InterruptedException e) {
            log.error(e);
            Thread.currentThread().interrupt();
        }
    }
}
