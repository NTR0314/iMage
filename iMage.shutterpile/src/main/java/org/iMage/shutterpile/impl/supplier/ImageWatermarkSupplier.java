package org.iMage.shutterpile.impl.supplier;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.impl.filters.AlphaFilter;
import org.iMage.shutterpile.impl.filters.GrayscaleFilter;
import org.iMage.shutterpile.impl.filters.ThresholdFilter;
import org.iMage.shutterpile.impl.util.ImageUtils;
import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile.port.IWatermarkSupplier;

/**
 * This class realizes a {@link IWatermarkSupplier} which uses a {@link BufferedImage} to generate
 * the watermark.
 *
 * @author Dominik Fuchss
 *
 */
public final class ImageWatermarkSupplier extends AbstractWatermarkSupplier {

  /**
   * This factor indicates by how much the transparency is increased. The new value alpha' is
   * calculated by <em>alpha' = (alpha * FACTOR) / 100</em>
   */
  public static final int DEFAULT_FACTOR = 50;

  private final IFilter gsf = new GrayscaleFilter();
  private final IFilter thf;

  private final BufferedImage watermarkInput;
  private final boolean useGrayscaleFilter;

  private BufferedImage createdWatermark;

  /**
   * Create the {@link IWatermarkSupplier} by base image of watermark. (Use GrayscaleFilter)
   *
   * @param watermarkInput
   *          the base image to create the watermark
   * @see #ImageWatermarkSupplier(BufferedImage, boolean)
   */
  public ImageWatermarkSupplier(BufferedImage watermarkInput) {
    this(watermarkInput, true);
  }
  

  /**
   * Create the {@link IWatermarkSupplier} by base image of watermark.
   *
   * @param watermarkInput
   *          the base image to create the watermark
   * @param useGrayscaleFilter
   *          indicates whether a {@link GrayscaleFilter} shall applied upon the input image
   */
  public ImageWatermarkSupplier(BufferedImage watermarkInput, boolean useGrayscaleFilter) {
    this.watermarkInput = watermarkInput;
    this.useGrayscaleFilter = useGrayscaleFilter;
    thf = new ThresholdFilter();
    
  }
 /** 
  * Creater the {@link IWatermarkSupplier} by base imge of watermark.
  * 
  * @param watermarkInput
  * 		the base image to create the watermark
  * @param useGrayscaleFilter
  * 		indicates whether a {@link GrayscaleFilter} shall be applied upton the input image
  * @param threshold
  * 		the threshold the {@link GrayscaleFilter} should use if it is used
  */  
  public ImageWatermarkSupplier(BufferedImage watermarkInput, boolean useGrayscaleFilter, int threshold) {
	  this.watermarkInput = watermarkInput;
	  this.useGrayscaleFilter = useGrayscaleFilter;
	  thf = new ThresholdFilter(threshold);	  
	  
  }

  public BufferedImage createWatermark() {
    if (this.createdWatermark == null) {
      // Create ARGB image as filters need (A)RGB
      BufferedImage watermark = ImageUtils.createARGBImage(this.watermarkInput);
      // Apply GrayscaleFilter
      if (this.useGrayscaleFilter) {
        watermark = this.gsf.apply(watermark);
      }
      // Apply ThresholdFilter
      watermark = this.thf.apply(watermark);
      // Set alpha value / create ARGB as we guarantee an ARBG-Image
      watermark = ImageUtils.createARGBImage(watermark);
      // Halve the alpha value
      watermark = new AlphaFilter().apply(watermark);

      this.createdWatermark = watermark;
    }
    return this.createdWatermark;
  }


}