import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jis.generator.Generator;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testclass for method rotateImage() in org.jis.generator.Generator.java
 * 
 * @author Oswald
 *
 */
public class RotateImageTest {

	private Generator generator;
	private BufferedImage image;
	private static final String IMAGEPATH = "src/test/resources/picture.jpg";

	/**
	 * Load picture from resources
	 * 
	 * @throws IOException
	 *             if invalid path
	 */
	@BeforeClass
	public void getImage() throws IOException {
		this.image = ImageIO.read(new File(IMAGEPATH));
	}

	/**
	 * Initialize generator
	 */
	@Before
	public void setUp() {
		this.generator = new Generator(null, 0);
	}
	
	/**
	 * Reset generator
	 */
	@After
	public void tearDown() {
		this.generator = null;
	}
	
	/**
	 * Test if rotating by 0 degrees results in the same image
	 */
	@Test
	public void notRotating() {
		assertEquals(this.image, this.generator.rotateImage(this.image, 0.0));
	}
	
	/**
	 * Test if method works with null as image
	 */
	@Test
	public void nullImage() {
		assertNull(this.generator.rotateImage(null, 0.0));
	}
}