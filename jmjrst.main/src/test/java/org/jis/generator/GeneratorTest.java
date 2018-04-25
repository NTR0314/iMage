package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.jis.generator.Generator;
import org.jis.options.Options;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.net.httpserver.Authenticator.Result;

/**
 * Testclass for method rotateImage() in org.jis.generator.Generator.java
 * 
 * @author Oswald
 *
 */
public class GeneratorTest {

	private Generator generator;
	private BufferedImage image;
	private static final String IMAGEPATH = "src/test/resources/picture.jpg";

	/**
	 * Initialize generator and load picture
	 * 
	 * @throws IOException
	 *             on invalid path
	 */
	@Before
	public void setUp() throws IOException {
		this.generator = new Generator(null, 0);
		this.image = ImageIO.read(new File(IMAGEPATH));
	}

	/**
	 * Reset generator
	 */
	@After
	public void tearDown() throws IOException {
		// save image
		String datestring = new SimpleDateFormat("HHmmss_SSS").format(new Date());
		File outputFile = new File("target/dataTest/rotatedPicture_" + datestring + ".jpg");
		ImageIO.write(this.image, "jpg", outputFile);
		// reset
		this.generator = null;
		this.image = null;
	}

	/**
	 * Test if rotating by 0 degrees results in the same image
	 */
	@Test
	public void notRotating() {
		assertEquals(this.image, this.generator.rotateImage(this.image, 0.0));
		this.image = this.generator.rotateImage(this.image, 0.0);
	}

	/**
	 * Test if method works with null as image
	 */
	@Test
	public void nullImage() {
		assertNull(this.generator.rotateImage(null, 0.0));
	}

	/**
	 * Trivial illegal arguments test
	 */
	@Test(expected = IllegalArgumentException.class)
	public void argumentTest() {
		this.generator.rotateImage(this.image, 0.5);
	}

	/**
	 * Test if test image rotated by 360 degrees results is the same image, (it
	 * should be) testing is done by comparing each pixel asserting they are the
	 * same
	 */
	@Test
	public void rotateThreeSixty() {
		BufferedImage rotatedImage;

		rotatedImage = this.generator.rotateImage(this.image, Math.toRadians(90.0)); // rotate by 90 degrees
		rotatedImage = this.generator.rotateImage(rotatedImage, Math.toRadians(270.0)); // rotate by another
																						// 270 degrees

		// Test if height and wdith are still the same
		assertEquals(this.image.getHeight(), rotatedImage.getHeight());
		assertEquals(this.image.getWidth(), rotatedImage.getWidth());

		// Test if all pixels are the same color as before
		for (int i = 0; i < this.image.getWidth(); i++) {
			for (int j = 0; j < this.image.getHeight(); j++) {
				assertEquals(this.image.getRGB(i, j), rotatedImage.getRGB(i, j));
			}
		}

		this.image = rotatedImage;
	}

	/**
	 * Test if rotating picture by 180degrees results in upside down as expected. We
	 * test if each pixel is mirrored at x-axis middle
	 */
	@Test
	public void upsideDown() {
		BufferedImage rotatedImage = this.generator.rotateImage(this.image, Math.toRadians(180.0)); // rotate 180 degree

		// Making sure height and width of picture are staying the same
		assertEquals(this.image.getHeight(), rotatedImage.getHeight());
		assertEquals(this.image.getWidth(), rotatedImage.getWidth());

		// comparing pixels
		for (int i = 0; i < this.image.getWidth(); i++) {
			for (int j = 0; j < this.image.getHeight(); j++) {
				assertEquals(this.image.getRGB(i, j),
						rotatedImage.getRGB(this.image.getWidth() - i - 1, this.image.getHeight() - j - 1));
			}
		}
		this.image = rotatedImage;
	}

	/**
	 * Test for resizing Image to 1/4
	 */
	@Test
	public void resize_modusQuality() {
		Options.getInstance().setModus(Options.MODUS_QUALITY);
		int height = this.image.getHeight() / 2;
		int width = this.image.getWidth() / 2;
		File picFile = new File(GeneratorTest.IMAGEPATH);
		File outFile = new File("src/test/resources/scalino");

		try {
			this.generator.generateImage(picFile, outFile, false, width, height, "scaled-");
			BufferedImage result = ImageIO.read(outFile);

			assertEquals(this.image.getHeight() / 2, result.getHeight());
			assertEquals(this.image.getWidth() / 2, result.getWidth());

			this.image = result; // so image gets written in teardown();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}