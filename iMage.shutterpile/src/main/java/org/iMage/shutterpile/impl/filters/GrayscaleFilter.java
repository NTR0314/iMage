package org.iMage.shutterpile.impl.filters;

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
		BufferedImage image = Util.deepCopy(arg0);

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				image.setRGB(i, j, calcRGB(i, j, image));
			}
		}

		return image;
	}

	private int calcRGB(int x, int y, BufferedImage image) {
		int rgb = image.getRGB(x, y);
		int alpha = (rgb >>> 24) & 0xFF;
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = rgb & 0xFF;

		int avg = (red + green + blue) / 3;

		int newrgb = avg + (avg << 8) + (avg << 16) + (alpha << 24);

		return newrgb;
	}

	/**
	 * Standard Constructor
	 */
	public GrayscaleFilter() {

	}

}
