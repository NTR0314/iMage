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
	 * Initialize generator and load picture
	 * @throws IOException on invalid path
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
	
	/**
	 * Testing illegal arguments
	 */
	@Test(expected = IllegalArgumentException.class)
	public void argumentTest() {
		this.generator.rotateImage(this.image, 0.5);
	}
	
}