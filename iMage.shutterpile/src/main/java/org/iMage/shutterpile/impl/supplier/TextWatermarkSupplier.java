package org.iMage.shutterpile.impl.supplier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextWatermarkSupplier extends AbstractWatermarkSupplier {
    public void setText(String text) {
        this.text = text;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setPxHeight(int pxHeight) {
        this.pxHeight = pxHeight;
    }

    private String text = "Default";
    private String font = "Comic Sans MS";
    private int pxHeight = 100;


    public BufferedImage createWatermark() {
        int[] sizes = calcSizes(pxHeight, this.text);
        int size = sizes[0];
        int width = sizes[1];
        int height = sizes[2];


        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(new Font(this.text,Font.PLAIN, size));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();

        return result;
    }

    public TextWatermarkSupplier(String text, int pxHeight, String font) {
        this.text = text;
        this.font = font;
        this.pxHeight = pxHeight;
    }

    public TextWatermarkSupplier(){}

    private int[] calcSizes(int desiredPxHeight, String text) {
        int size = 50;
        int width;
        int height;

        do {
            BufferedImage result = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = result.createGraphics();
            Font font = new Font(this.font, Font.PLAIN, size);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            height = fm.getHeight();
            width = fm.stringWidth(text);
            g2d.dispose();
            size++;

        } while (height < desiredPxHeight);

        return new int[]{size, width, height};
    }
}
