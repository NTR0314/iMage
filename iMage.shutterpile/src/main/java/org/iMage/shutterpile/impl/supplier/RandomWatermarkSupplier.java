package org.iMage.shutterpile.impl.supplier;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class RandomWatermarkSupplier extends AbstractWatermarkSupplier {
    private String font = "Comic Sans MS";

    @Override
    public BufferedImage createWatermark() {
        int randomInt = ThreadLocalRandom.current().nextInt(0, 9 + 1);
        String stringInt = Integer.toString(randomInt);

        int size = calcSize(randomInt);

        BufferedImage result = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(new Font(stringInt, Font.PLAIN, size));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(stringInt, 0, fm.getAscent());
        g2d.dispose();

        return result;




    }

    private int calcSize(int number) {
        int size = 20;
        int width;
        int height;

        do {
            BufferedImage result = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = result.createGraphics();
            Font font = new Font(this.font, Font.PLAIN, size);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            height = fm.getHeight();
            width = fm.stringWidth(Integer.toString(number));
            g2d.dispose();
            size++;

        } while (height < 100 && width < 100);

        return size;
    }
}
