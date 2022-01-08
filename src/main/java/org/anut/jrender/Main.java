package org.anut.jrender;

import org.anut.jrender.display.Raster2D;
import org.anut.jrender.display.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Window window = new Window(1000, 800, "test");
        window.showWindow();
        Raster2D r = new Raster2D(window);
        Thread.sleep(1000);
        Thread t = new Thread(window::updateLoop);
        t.start();
        double[] pixel = new double[3];
        long time = System.currentTimeMillis();
        for (int b = 0; b < 255; b++) {
            for (int i = 0; i < 800; i++) {
                for (int j = 0; j < 1000; j++) {
                    pixel[0] = i / 800.0 * 255;
                    pixel[1] = j / 1000.0 * 255;
                    pixel[2] = b;
                    r.write(j, i, pixel);
                }
            }
        }
        log.info(255000 / (System.currentTimeMillis() - time));
    }
}
