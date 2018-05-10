package org.iMage.shutterpile.impl.filters;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.port.IFilter;


/**
 * This {@link IFilter Filter} makes a Pixel of the picture transparent if the the average rbg is above a certain value
 * 
 * @author Oswald
 *
 */
public class ThresholdFilter implements IFilter {
	private int threshold;

	@Override
	public BufferedImage apply(BufferedImage arg0) {
		BufferedImage image = arg0;
		
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				if (getAvg(i, j, image) > this.threshold) {
					makePixelTransparent(i, j, image);
				}
			}
		}
		
		return image;
		
	}
	
	/**
	 * Constructor with threshold init.
	 * @param threshold value for threshold
	 */
	public ThresholdFilter(int threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * Standard Constructor sets threshold value to default Value, 127
	 */
	public ThresholdFilter() {
		this.threshold = 127;
	}
	
	private int getAvg(int x, int y, BufferedImage image) {
		int rgb = image.getRGB(x, y);
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = rgb & 0xFF;

		int avg = (red + green + blue) / 3;

		return avg;
	}
	
	private void makePixelTransparent(int x, int y, BufferedImage image) {
		int rgb = image.getRGB(x, y);
		rgb = rgb & 0xFFFFFF;
		rgb = rgb + 0xFF000000;
		
		image.setRGB(x, y, rgb);
	}

}
