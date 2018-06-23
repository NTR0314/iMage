package org.iMage.shutterpile.impl.filters;

import org.iMage.shutterpile.impl.supplier.ImageWatermarkSupplier;
import org.iMage.shutterpile.port.IFilter;

import java.awt.image.BufferedImage;

public class AlphaFilter implements IFilter {

    @Override
    public BufferedImage apply(BufferedImage bufferedImage) {
        BufferedImage result = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                int color = bufferedImage.getRGB(i, j);
                int alpha = color >> 24 & 0x000000FF;
                alpha = (alpha * ImageWatermarkSupplier.DEFAULT_FACTOR) / 100;
                result.setRGB(i, j, (color & 0x00FFFFFF) | (alpha << 24));
            }
        }
        result.flush();

        return result;
    }


}
