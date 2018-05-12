package org.iMage.shutterpile.impl.filters;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Util {
	static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	static int getAlpha(int argb) {
		return (argb >>> 24) & 0xFF;
	}
	
	static int getRed(int argb) {
		return (argb >> 16) & 0xFF;
	}
	
	static int getGreen(int argb) {
		return (argb >> 8) & 0xFF;
	}
	
	static int getBlue(int argb) {
		return argb & 0xFF;
	}
}
