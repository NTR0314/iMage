package org.iMage.shutterpile.impl.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.iMage.shutterpile.port.IFilter;

/**
 * This {@link IFilter Filter} converts a picture into a gray picture, by making
 * all rbg values equal.
 * 
 * @author Oswald
 *
 */
public class GrayscaleFilter implements IFilter {

	@Override
	public BufferedImage apply(BufferedImage arg0) {
		BufferedImage image = arg0;
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int grayrbg = calcRGB(i, j, image);

				result.setRGB(i, j, grayrbg);
			}
		}

		return result;
	}

	private int calcRGB(int x, int y, BufferedImage image) {
		int rgb = image.getRGB(x, y);
		int alpha = Util.getAlpha(rgb);
		int red = Util.getRed(rgb);
		int green = Util.getGreen(rgb);
		int blue = Util.getBlue(rgb);

		int avg = (red + green + blue) / 3;

		Color c = new Color(avg, avg, avg, alpha);

		return c.getRGB();
	}

	/**
	 * Standard Constructor
	 */
	public GrayscaleFilter() {

	}

}
