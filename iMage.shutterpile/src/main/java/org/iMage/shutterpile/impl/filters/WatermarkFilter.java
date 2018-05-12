package org.iMage.shutterpile.impl.filters;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile.port.IWatermarkSupplier;

/**
 * This {@link IFilter Filter} adds a watermark ({@link BufferedImage}) to an
 * image.
 *
 * @author Dominic Wolff
 *
 */
public final class WatermarkFilter implements IFilter {
	private BufferedImage watermark;
	private int watermarksPerRow;

	/**
	 * Create a the WatermarkFilter.
	 *
	 * @param watermark
	 *            the watermark image as provided by a {@link IWatermarkSupplier}.
	 * @param watermarksPerRow
	 *            the number of watermarks in a line (this is meant as desired
	 *            value. the possible surplus is drawn)
	 */
	public WatermarkFilter(BufferedImage watermark, int watermarksPerRow) {
		this.watermark = watermark;
		this.watermarksPerRow = watermarksPerRow;
	}

	@Override
	public BufferedImage apply(BufferedImage input) {
		BufferedImage result = this.deepCopy(input);

		resizeWatermark(result);
		putWatermarkOnImg(result);

		return result;

	}

	private void resizeWatermark(BufferedImage bimage) {
		int newWidth = bimage.getWidth() / this.watermarksPerRow;

		Image image = this.watermark.getScaledInstance(newWidth, -1, Image.SCALE_SMOOTH);

		this.watermark = toBufferedImage(image);
	}

	private BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;

	}

	private void putWatermarkOnImg(BufferedImage image) {
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				alphaBlend(i, j, this.watermark, image);
			}
		}
	}

	private void alphaBlend(int x, int y, BufferedImage watermark, BufferedImage image) {
		int rgba1 = watermark.getRGB(x % watermark.getWidth(), y % watermark.getHeight());
		int rgba2 = image.getRGB(x, y);

		int alpha1 = (rgba1 >>> 24) & 0xFF;
		int red1 = (rgba1 >>> 16) & 0xFF;
		int green1 = (rgba1 >>> 8) & 0xFF;
		int blue1 = rgba1 & 0xFF;

		int alpha2 = (rgba2 >>> 24) & 0xFF;
		int red2 = (rgba2 >>> 16) & 0xFF;
		int green2 = (rgba2 >>> 8) & 0xFF;
		int blue2 = rgba2 & 0xFF;

		int newAlpha = alpha1 + (((255 - alpha1) * alpha2) / 255);
		int newRed = (alpha1 * red1 + ((255 - alpha1) * alpha2 * red2) / 255) / newAlpha;
		int newGreen = (alpha1 * green1 + ((255 - alpha1) * alpha2 * green2) / 255) / newAlpha;
		int newBlue = (alpha1 * blue1 + ((255 - alpha1) * alpha2 * blue2) / 255) / newAlpha;
		int newRgba = newBlue | (newGreen << 8) | (newRed << 16) | (newAlpha << 24);

		image.setRGB(x, y, newRgba);

	}

	private BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
