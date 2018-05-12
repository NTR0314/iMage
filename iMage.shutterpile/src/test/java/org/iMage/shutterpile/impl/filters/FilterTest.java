/**
 * 
 */
package org.iMage.shutterpile.impl.filters;

import static org.junit.Assert.assertEquals;

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
public class FilterTest {
	private static final String PATH = "src/test/resources/";
	
	private static final String BUNT = PATH + "colorfulPicture_alpha.png";
	private static final String BUNT_MIT_WM = PATH + "colorfulPicWithPearWatermark10Row.png";
	private static final String PEAR = PATH + "pearWatermark.png";
	private static final String TICHY_ORIGINAL = PATH + "tichyWatermark_input_no_alpha.png";
	
	private BufferedImage picBunt;
	private BufferedImage picBuntMitWm;
	private BufferedImage wmPear;
	private BufferedImage wmTichyOg;
	
	private BufferedImage preTest;
	private BufferedImage result;
	
	/**
	 * Setting up testing by loading images and watermarks
	 * @throws IOException if error while loading images
	 */
	@Before
	public void setUp() throws IOException {
		picBunt = ImageIO.read(new File(BUNT));
		wmPear = ImageIO.read(new File(PEAR));
		wmTichyOg = ImageIO.read(new File(TICHY_ORIGINAL));
		picBuntMitWm = ImageIO.read(new File(BUNT_MIT_WM));
	}

	/**
	 * Teardown: saving both orignal and test image also resetting loaded images
	 * @throws Exception if error while saving images.
	 */
	@After
	public void tearDown() throws Exception {
		//saving both images so i can get a visual on how/if my filter works saving both orignal and after
		String datestring = new SimpleDateFormat("HHmmss_SSS").format(new Date());
		File outputFile = new File("target/dataTest/rotatedPicture_" + datestring + "_result_" + ".png");
		ImageIO.write(this.result, "png", outputFile);
		
		String datestring2 = new SimpleDateFormat("HHmmss_SSS").format(new Date());
		File outputFile2 = new File("target/dataTest/rotatedPicture_" + datestring2 + "_original_" + ".png");
		ImageIO.write(this.preTest, "png", outputFile2);
		
		//reset
		this.picBunt = null;
	}

	/**
	 * Test WatermarkFilter
	 */
	@Test
	public void testWatermarkfilter() {
		this.preTest = picBunt;
		
		WatermarkFilter wmf = new WatermarkFilter(wmPear, 10);
		this.result = wmf.apply(preTest);
		
		for (int i = 0; i < result.getWidth(); i++) {
			for (int j = 0; j < result.getHeight(); j++) {
				assertEquals(result.getRGB(i, j), picBuntMitWm.getRGB(i, j));
			}
		}
	}
	
	/**
	 * Test GrayscaleFilter
	 */
	@Test
	public void testGrayscaleFilter() {
		this.preTest = wmTichyOg;
		GrayscaleFilter gsf = new GrayscaleFilter();
		
		this.result = gsf.apply(wmTichyOg);
		
		for (int i = 0; i < this.preTest.getWidth(); i++) {
			for (int j = 0; j < this.preTest.getHeight(); j++) {
				int rbg = this.preTest.getRGB(i, j);
				int avgShould = ((rbg & 0xFF) + ((rbg >> 8) & 0xFF) + ((rbg >> 16) & 0xFF)) / 3;
				
				//red == avg
				assertEquals(Util.getRed(this.result.getRGB(i, j)), avgShould);
				//green == avg
				assertEquals(Util.getGreen(this.result.getRGB(i, j)), avgShould);
				//blue == avg
				assertEquals(Util.getBlue(this.result.getRGB(i, j)), avgShould);
			}
		}
		
	}
}
