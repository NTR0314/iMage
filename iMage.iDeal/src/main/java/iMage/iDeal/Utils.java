package iMage.iDeal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for GUI
 */
public class Utils {
    /**
     * Class for loading Images in correct size
     *
     * @param imageName name of the Image without file-ending, png is required
     * @param width     image width
     * @param height    image height
     * @return loaded picture
     */
    public static BufferedImage getImage(String imageName, int width, int height) throws IOException {
        String path = "/" + imageName + ".png";

        Class<?> clazz = Utils.class;

        BufferedImage image = ImageIO.read(clazz.getResourceAsStream(path));
        if (scaleByWidth(image.getWidth(), image.getHeight(), width, height)) {
            image = scaleWidth(image, width);
        } else {
            image = scaleHeight(image, height);
        }

        return image;


    }

    @SuppressWarnings("Duplicates")
    private static BufferedImage scaleWidth(BufferedImage input, int width) {


        if (width <= 0) {
            throw new IllegalArgumentException("width cannot be <= 0");
        }
        Image scaled = input.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
        int height = scaled.getHeight(null);
        if (height <= 0) {
            throw new IllegalArgumentException("height would be 0");
        }
        BufferedImage res = new BufferedImage(width, height, input.getType());
        Graphics2D g2d = res.createGraphics();
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();
        res.flush();
        return res;
    }

    private static BufferedImage scaleHeight(BufferedImage input, int height) {

        if (height <= 0) {
            throw new IllegalArgumentException("height must be bigger than 0");
        }
        Image scaled = input.getScaledInstance(-1, height, Image.SCALE_SMOOTH);
        int width = scaled.getWidth(null);
        if (width <= 0) {
            throw new IllegalArgumentException("width would be zero");
        }
        BufferedImage res = new BufferedImage(width, height, input.getType());
        Graphics2D g2d = res.createGraphics();
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();
        res.flush();
        return res;
    }

    // returns true if you should scale by width, else false
    private static boolean scaleByWidth(int imageX, int imageY, int goalX, int goalY) {
        int multX = imageX * goalX;
        int multY = imageY * goalY;

        return multX < multY;
    }
}
