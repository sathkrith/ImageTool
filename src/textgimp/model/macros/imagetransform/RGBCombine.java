package textgimp.model.macros.imagetransform;

import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.macros.AbstractMacro;
import textgimp.model.macros.Macro;


/**
 * This class represents a RGBCombine macro. It combines red, green, blue channels into one image.
 */
public class RGBCombine extends AbstractMacro implements Macro {

  private final Image greenImg;
  private final Image blueImg;

  /**
   * Initialize RGBCombine macro and set green and blue channel images.
   *
   * @param greenImg The green channel image.
   * @param blueImg  The blue channel image.
   * @throws IllegalArgumentException if the given images are null or empty
   */
  public RGBCombine(Image greenImg, Image blueImg) throws IllegalArgumentException {
    // validate images
    this.validateImage(greenImg);
    this.validateImage(blueImg);

    if (greenImg.getHeight() != blueImg.getHeight() || greenImg.getWidth() != blueImg.getWidth()) {
      throw new IllegalArgumentException("Green and blue images must be the same size.");
    }

    this.greenImg = greenImg;
    this.blueImg = blueImg;
  }

  /**
   * Combine red, green, blue channels from three images into one image.
   *
   * @param redImg The red channel image.
   * @return The combined image.
   */
  @Override
  public Image apply(Image redImg) throws IllegalArgumentException {
    this.validateImage(redImg);

    // check if all images are the same size
    int imgWidth = redImg.getWidth();
    int imgHeight = redImg.getHeight();
    int maxValue = redImg.getMaxValue();

    if (imgWidth != this.greenImg.getWidth() || imgHeight != this.greenImg.getHeight()) {
      throw new IllegalArgumentException("Red image size does not march with blue and green");
    }

    // Create a new 2D array of pixels
    Pixel[][] newPixels = new Pixel[imgHeight][imgWidth];

    // Loop through the pixels and set the new pixels to the combined version of the old pixels
    for (int i = 0; i < imgHeight; i++) {
      for (int j = 0; j < imgWidth; j++) {
        Pixel redPixel = redImg.getPixel(i, j);
        int red = redPixel.getRed();
        int green = this.greenImg.getPixel(i, j).getGreen();
        int blue = this.blueImg.getPixel(i, j).getBlue();
        newPixels[i][j] = redPixel.createPixel(red, green, blue);
      }
    }
    return new GenericImage(newPixels, maxValue, redImg.getImageType());
  }
}
