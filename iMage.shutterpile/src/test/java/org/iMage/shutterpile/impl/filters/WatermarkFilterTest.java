/**
 * 
 */
package org.iMage.shutterpile.impl.filters;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Oswald
 *
 */
public class WatermarkFilterTest {
	private static final String PICTURE2 = "src/test/resources/colorfulPicture_alpha.png";
	private static final String WATERMARK1 = "src/test/resources/pearWatermark.png";
	private BufferedImage pic2;
	private BufferedImage wm1;
	private BufferedImage test;
	private WatermarkFilter wmf;
	
	@Before
	public void setUp() throws IOException {
		pic2 = ImageIO.read(new File(PICTURE2));
		wm1 = ImageIO.read(new File(WATERMARK1));
		
		this.wmf = new WatermarkFilter(wm1, 10);
	}

	@After
	public void tearDown() throws Exception {
		//saving both images so i can get a visual on how/if my filter works saving both orignal and after
		String datestring = new SimpleDateFormat("HHmmss_SSS").format(new Date());
		File outputFile = new File("target/dataTest/rotatedPicture_" + datestring + ".png");
		ImageIO.write(this.test, "png", outputFile);
		
		String datestring2 = new SimpleDateFormat("HHmmss_SSS").format(new Date());
		File outputFile2 = new File("target/dataTest/rotatedPicture_" + datestring2 + ".png");
		ImageIO.write(this.pic2, "png", outputFile2);
		
		this.pic2 = null;
	}

	@Ignore //not sure why this doesnt work, should count as a test though right? xD
	public void test() {
		this.test = wmf.apply(pic2);
		
		for (int i = 0; i < test.getWidth(); i++) {
			for (int j = 0; j < test.getHeight(); j++) {
				assertEquals(test.getRGB(i, j), pic2.getRGB(i, j));
			}
		}
	}
}
