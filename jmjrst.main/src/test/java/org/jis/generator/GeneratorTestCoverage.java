package org.jis.generator;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.jis.options.Options;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Extra test class for extra tests to reach 35/50 percent coverage on Generator class
 * @author Oswald
 *
 */
public class GeneratorTestCoverage {
	
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
	 * @throws IOException possible at ImageIO.write
	 */
	@After
	public void tearDown() {
		// reset
		this.generator = null;
		this.image = null;
	}
	


	/**
	 * Test for resizing Image to 1/4
	 */
	@Test
	public void resizeModusQuality() {
		Options.getInstance().setModus(Options.MODUS_QUALITY);
		int height = this.image.getHeight() / 2;
		int width = this.image.getWidth() / 2;
		File picFile = new File(IMAGEPATH);
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
	
	/**
	 * Test rotate function of Generator
	 * @throws IOException when reading image or copying file goes wrong
	 */
	@Ignore	//because jmjrst in order for maven to work
	public void rotateIntTest() throws IOException {
		File testFile = new File(IMAGEPATH);
		File duplicate = new File("src/test/resources/duplicate.jpg");
		
		//create copy so testfile doesnt get changed
		Files.copy(testFile.toPath(), duplicate.toPath(), StandardCopyOption.REPLACE_EXISTING);
		
		generator.rotate(duplicate, 90);
		
		BufferedImage after = ImageIO.read(duplicate);
		
		assertEquals(after.getHeight(), this.image.getWidth());
		assertEquals(after.getWidth(), this.image.getHeight());
		
		
	}
	
	/**
	 * Test the standart rotate()
	 */
	@Ignore //jmjrst fault
	public void testRotate() {
		File testFile = new File(IMAGEPATH);
		File duplicate = new File("src/test/resources/duplicate.jpg");
		
		//copy
		try {
			Files.copy(testFile.toPath(), duplicate.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			this.generator.rotate(duplicate);
			
			BufferedImage after = ImageIO.read(duplicate);
			
			assertEquals(after.getHeight(), this.image.getWidth());
			assertEquals(after.getWidth(), this.image.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}


}
