package org.jrender.scene;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jrender.space.Vector3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CameraController {
    private static final Logger log = LogManager.getLogger(CameraController.class);
    private static final double halfPi = Math.PI / 2;
    private final Cursor blankCursor;
    private Window window;
    private Camera camera;
    private Robot robot;
    private volatile boolean fixed;
    private volatile boolean autofix;
    private double sensitivity;
    private double newRotX, newRotZ;
    private double newPosX, newPosY, newPosZ;
    private boolean wPressed;
    private boolean aPressed;
    private boolean sPressed;
    private boolean dPressed;
    private boolean qPressed;
    private boolean ePressed;
    private volatile boolean movementLoop;
    private volatile boolean rendering;
    private volatile double fps;

    public CameraController(Camera camera, double sensitivity) {
        this.camera = camera;
        fixed = false;
        autofix = true;
        this.sensitivity = sensitivity;
        movementLoop = false;
        newRotX = camera.getRotationX();
        newRotZ = camera.getRotationZ();
        newPosX = camera.getOrigin().getX();
        newPosY = camera.getOrigin().getY();
        newPosZ = camera.getOrigin().getZ();
        rendering = false;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            log.error("Exception occurred: ");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            log.error(sw.toString());
        }
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (fixed) rotate(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (fixed) rotate(e);
            }
        });
        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (autofix) {
                    fixed = true;
                }
                if (fixed) {
                    frame.getContentPane().setCursor(blankCursor);
                    robot.mouseMove(
                            frame.getX() + frame.getWidth() / 2,
                            frame.getY() + frame.getHeight() / 2);
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                fixed = false;
                frame.getContentPane().setCursor(Cursor.getDefaultCursor());
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ALT) {
                    e.consume(); // prevent window from losing focus when alt is pressed
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    autofix = false;
                    fixed = false;
                    frame.getContentPane().setCursor(Cursor.getDefaultCursor());
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autofix = true;
                    fixed = true;
                    frame.getContentPane().setCursor(blankCursor);
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    wPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    aPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    sPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    dPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    qPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    ePressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    wPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    aPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    sPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    dPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    qPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    ePressed = false;
                }
            }
        });
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
    }

    public void autofixMouse() {
        autofix = true;
        fixed = true;
        frame.requestFocus();
        frame.getContentPane().setCursor(blankCursor);
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    private void rotate(MouseEvent e) {
        int midX = frame.getWidth() / 2;
        int midY = frame.getHeight() / 2;
        if (e.getX() == midX &&
                e.getY() == midY) return;
        robot.mouseMove(
                frame.getX() + midX,
                frame.getY() + midY);
        int moveX = e.getX() - midX;
        int moveY = e.getY() - midY;
        newRotZ = newRotZ - moveX * sensitivity / 1000;
        newRotX = newRotX - moveY * sensitivity / 1000;
        if (newRotX > halfPi) newRotX = halfPi;
        if (newRotX < -halfPi) newRotX = -halfPi;
    }

    public void startMovementLoop() {
        movementLoop = true;
        long lastFrame = System.currentTimeMillis();
        long curTime;
        double diff;
        double sin;
        double cos;
        while (movementLoop) {
            curTime = System.currentTimeMillis();
            diff = (curTime - lastFrame) / 1000.0;
            sin = Math.sin(camera.getRotationZ());
            cos = Math.cos(camera.getRotationZ());
            if (wPressed) {
                newPosY += cos * diff;
                newPosX -= sin * diff;
            }
            if (sPressed) {
                newPosY -= cos * diff;
                newPosX += sin * diff;
            }
            if (aPressed) {
                newPosY -= sin * diff;
                newPosX -= cos * diff;
            }
            if (dPressed) {
                newPosY += sin * diff;
                newPosX += cos * diff;
            }
            if (qPressed) {
                newPosZ += diff;
            }
            if (ePressed) {
                newPosZ -= diff;
            }
            lastFrame = curTime;
        }
    }

    public void stopMovementLoop() {
        movementLoop = false;
    }

    public void renderLoop(Scene scene, double resolution) {
        int blockSize = (int) (1 / resolution);
        long time = System.currentTimeMillis();
        rendering = true;
        while (rendering) {
            long curTime = System.currentTimeMillis();
            camera.setRotationZ(newRotZ);
            camera.setRotationX(newRotX);
            camera.setOrigin(new Vector3D(newPosX, newPosY, newPosZ));
            scene.render(blockSize);
            fps = (curTime - time);
            time = curTime;
        }
    }

    public void stopRenderLoop() {
        rendering = false;
    }

    public double getFps() {
        return fps;
    }
}
