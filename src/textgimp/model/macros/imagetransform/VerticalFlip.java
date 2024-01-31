package textgimp.model.macros.imagetransform;

import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.macros.AbstractMacro;
import textgimp.model.macros.Macro;

/**
 * This class represents a Vertical Flip macro. It flips the image vertically.
 */
public class VerticalFlip extends AbstractMacro implements Macro {

  @Override
  public Image apply(Image sourceImage) throws IllegalArgumentException {
    // validate the image
    this.validateImage(sourceImage);

    // read the image properties
    int maxValue = sourceImage.getMaxValue();
    int height = sourceImage.getHeight();
    int width = sourceImage.getWidth();

    // create a new 2D array of pixels
    Pixel[][] newPixels = new Pixel[height][width];

    // flip the image vertically
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newPixels[i][j] = sourceImage.getPixel(height - i - 1, j);
      }
    }
    return new GenericImage(newPixels, maxValue, sourceImage.getImageType());
  }
}
