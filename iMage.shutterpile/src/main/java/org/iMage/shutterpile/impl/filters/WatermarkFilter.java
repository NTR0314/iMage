package org.iMage.shutterpile.impl.filters;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

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
		BufferedImage result = input;
		
		resizeWatermark(result);
		putWatermarkOnImg(result);
		
		return result;
		
		
	}

	private void resizeWatermark(BufferedImage bimage) {
		int newWidth = bimage.getWidth() / this.watermarksPerRow;
		int factor = this.watermark.getWidth() / newWidth;
		
		int newHeight = this.watermark.getHeight() * factor;

		Image image = this.watermark.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		
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
				for (int k = 0; k < this.watermarksPerRow; k++) {
					for (int l = 0; l < this.watermarksPerRow; l++) {
						alphaBlend(i * k, j *l, this.watermark, image);
					}
				}
			}
		}
	}
	
	//img1:= watermark img2:= pic
	private void alphaBlend(int x, int y, BufferedImage img1, BufferedImage img2) {
		int rgba1 = img1.getRGB(x, y);
		int rgba2 = img2.getRGB(x, y);
		
		int alpha1 = (rgba1 >> 24) & 0xFF;
		int red1 = (rgba1 >> 16) & 0xFF;
		int green1 = (rgba1 >> 8) & 0xFF;
		int blue1 = rgba1 & 0xFF;
		
		int alpha2 = (rgba2 >> 24) & 0xFF;
		int red2 = (rgba2 >> 16) & 0xFF;
		int green2 = (rgba2 >> 8) & 0xFF;
		int blue2 = rgba2 & 0xFF;
		
		int newAlpha = alpha1 + ((1 - alpha1) * alpha2);
		int newRed = (1/newAlpha) * (alpha1 * red1 + ((1 - alpha1) * alpha2 * red2));
		int newGreen = (1/newAlpha) * (alpha1 * green1 + ((1 - alpha1) * alpha2 * green2));
		int newBlue = (1/newAlpha) * (alpha1 * blue1 + ((1 - alpha1) * alpha2 * blue2));
		
		int newRgba = newBlue + (newGreen << 8) + (newRed << 16) + (newAlpha << 24);
		
		img2.setRGB(x, y, newRgba);
		
	}
}
