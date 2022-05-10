package org.jrender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jrender.light.Light;
import org.jrender.light.impl.PointLight;
import org.jrender.scene.Camera;
import org.jrender.scene.CameraController;
import org.jrender.scene.Scene;
import org.jrender.shader.Color;
import org.jrender.shader.impl.DiffuseShader;
import org.jrender.space.Face3D;
import org.jrender.space.Ray3D;
import org.jrender.space.Solid3D;
import org.jrender.space.Vector3D;
import org.jrender.tasks.TaskQueue;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        test9();
    }

    public static int getNumForColor(int r, int g, int b) {
        return r << 16 | g << 8 | b;
    }

    public static void test2() {
        Face3D triangle = new Face3D(
                new Vector3D(-1, -1, 0),
                new Vector3D(1, -1, 0),
                new Vector3D(0, 1, 0)
        );
        Ray3D ray = new Ray3D(
                new Vector3D(-0.5, -0.5, 6),
                new Vector3D(0, -0.01, -1).normalize()
        );
        log.info(triangle.getVertices());
        log.info(triangle.getNormal());
        log.info(triangle.collision(ray));
    }

    public static void test3() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            Face3D triangle = new Face3D(
                    new Vector3D(-1, -1, 0),
                    new Vector3D(1, -1, 0),
                    new Vector3D(0, 1, 0)
            );
            Ray3D ray = new Ray3D(
                    new Vector3D(-0.5, -0.5, 6),
                    new Vector3D(0, -0.01, -1).normalize()
            );
            triangle.collision(ray);
        }
        long endTime = System.currentTimeMillis();
        log.info("done in {} ms", endTime - startTime);
    }

    public static void test4() {
        Face3D triangle = new Face3D(
                new Vector3D(100, 0, 0),
                new Vector3D(-100, -100, 0),
                new Vector3D(-100, 100, 0)
        );
        Ray3D ray = new Ray3D(
                new Vector3D(10, 0, 10),
                new Vector3D(-2, 0, -1).normalize()
        );
        log.info(triangle.getNormal());
        log.info(triangle.reflection(ray));
    }

    public static void test5() {
        Face3D triangle = new Face3D(
                new Vector3D(100, 0, 0),
                new Vector3D(-100, -100, 0),
                new Vector3D(-100, 100, 0)
        );
        Ray3D ray = new Ray3D(
                new Vector3D(10, 0, 10),
                new Vector3D(-2, 0, -1).normalize()
        );
        log.info(triangle.reflection(ray));
    }

    public static void test6() {
        Camera cam = new Camera(1000, 800, 0.5);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 800000; j++) {
                cam.getRay(j / 800, j - (j / 800 * 800));
            }
        }
        log.info(System.currentTimeMillis() - start);
    }

    public static void test7() {
        Vector3D top = new Vector3D(0, 2, 1);
        Vector3D front = new Vector3D(0, 3, 0);
        Vector3D left = new Vector3D(-1, 1, 0);
        Vector3D right = new Vector3D(1, 1, 0);

        Vector3D cor1 = new Vector3D(100, 100, 0);
        Vector3D cor2 = new Vector3D(-100, 100, 0);
        Vector3D cor3 = new Vector3D(-100, -100, 0);
        Vector3D cor4 = new Vector3D(100, -100, 0);
        HashSet<Face3D> faces = new HashSet<>();
        faces.add(new Face3D(top, front, left));
        faces.add(new Face3D(top, front, right));
        faces.add(new Face3D(top, left, right));
        faces.add(new Face3D(front, left, right));

        HashSet<Face3D> faces1 = new HashSet<>();
        faces1.add(new Face3D(cor1, cor2, cor3, cor4));
        Solid3D pyramid = new Solid3D(
                faces, new DiffuseShader(new Color(0, 255, 0), 1)
        );
        Solid3D square = new Solid3D(
                faces1, new DiffuseShader(new Color(255, 0, 0), 1)
        );
        Set<Solid3D> solids = new HashSet<>();
        solids.add(pyramid);
        solids.add(square);
        Light l = new PointLight(0.5, new Vector3D(0, 0, 2));
        Camera c = new Camera(1000, 800, 0.5, new Vector3D(0, 0, 1), 0, 0);
        Scene scene = new Scene(solids, l, c, 30);
        Window window = new Window(1000, 800, "test");
        window.showWindow();
        window.getFrame().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                c.setHeight(e.getComponent().getHeight());
                c.setWidth(e.getComponent().getWidth());
                window.setDims(e.getComponent().getWidth(), e.getComponent().getHeight());
            }
        });
        CameraController controller = new CameraController(window, c, 50);
        controller.autofixMouse();
        new Thread(() -> controller.renderLoop(scene, 0.5)).start();
        new Thread(controller::startMovementLoop).start();
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(controller.getFps());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }

    public static void test8() throws InterruptedException {
        TaskQueue taskQueue = new TaskQueue(50);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 41; i++) {
            taskQueue.submitTask(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
        }
        taskQueue.awaitCompletion();
        log.info("Time elapsed: {} seconds", (System.currentTimeMillis() - start) / 1000.0);
    }

    public static void test9() {
        long window;

        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (w, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(w, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        glViewport(0, 0, 300, 300);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 300, 300, 0, -10, 10);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glBegin(GL_POINTS);

            for (int i = 0; i < 300; i ++) {
                for (int j = 0; j < 300; j ++) {
                    glColor3f(i/300f, j/300f, 0);
                    glVertex2d(i, j);
                }
            }

            glEnd();
            glFlush();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
