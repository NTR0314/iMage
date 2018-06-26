package org.iMage.shutterpile.impl;

import org.iMage.shutterpile.impl.supplier.RandomWatermarkSupplier;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.Assert.assertEquals;

public class RandomTest {
    @Test
    public void test() {
        RandomWatermarkSupplier rms = new RandomWatermarkSupplier();
        BufferedImage image = rms.getWatermark();

        assertEquals(image.getHeight(), 100);
        assertEquals(image.getWidth(), 100);

//        try {
//            ImageIO.write(rms.getWatermark(), "png", new File("omegakek.png"));
//        } catch (Exception e) {
//            System.out.println("uupsie");
//        }
    }
}
