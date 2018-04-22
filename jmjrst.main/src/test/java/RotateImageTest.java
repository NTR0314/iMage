import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jis.generator.Generator;
import org.junit.Before;

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
	public void getImage() throws IOException {
		this.image = ImageIO.read(new File(IMAGEPATH));
	}

	/**
	 * initialize generator
	 */
	@Before
	public void setUp() {
		this.generator = new Generator(null, 0);
	}
}