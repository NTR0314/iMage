package org.iMage.shutterpile.impl.supplier;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.impl.filters.GrayscaleFilter;
import org.iMage.shutterpile.port.IWatermarkSupplier;
import org.iMage.shutterpile.impl.filters.ThresholdFilter;

/**
 * This class realizes a {@link IWatermarkSupplier} which uses a
 * {@link BufferedImage} to generate the watermark.
 *
 * @author Dominic Wolff
 *
 */
public final class ImageWatermarkSupplier implements IWatermarkSupplier {
	BufferedImage watermark;

	/**
	 * Create the {@link IWatermarkSupplier} by base image of watermark.
	 *
	 * @param watermarkInput
	 *            the base image to create the watermark
	 */
	public ImageWatermarkSupplier(BufferedImage watermarkInput) {
		this.watermark = watermarkInput;

	}

	@Override
	public BufferedImage getWatermark() {
		BufferedImage image = this.watermark;

		// apply grayscalefilter
		GrayscaleFilter gsf = new GrayscaleFilter();
		image = gsf.apply(image);
		// apply thresholdfilter
		ThresholdFilter thf = new ThresholdFilter();
		image = thf.apply(image);

		// add alpha channel if not there
		if (!image.getColorModel().hasAlpha()) {
			// TODO implement (add alpha channel)
		}

		// halve the alpha value
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				halveAlpha(i, j, image);
			}
		}
		
		return image;

	}

	private void halveAlpha(int x, int y, BufferedImage image) {
		int rgba = image.getRGB(x, y);

		int alpha = (rgba >> 24);
		alpha = alpha / 2;

		rgba = rgba & 0x00FFFFFF;
		rgba = rgba + (alpha << 24);

		image.setRGB(x, y, rgba);
	}

}
