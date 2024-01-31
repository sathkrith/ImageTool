package textgimp.model.macros.colortransform;

import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.macros.AbstractMacro;
import textgimp.model.macros.Macro;

/**
 * This class represents a Brighten color transformation macro. This macro brightens or darkens an
 * image by a given amount. If the amount is positive, the image will be brightened. If the amount
 * is negative, the image will be darkened.
 */
public class Brighten extends AbstractMacro implements Macro {

  private final int amount;

  /**
   * Create a new Brighten macro with the given amount.
   *
   * @param amount integer amount to brighten the image by
   */
  public Brighten(int amount) {
    this.amount = amount;
  }

  @Override
  public Image apply(Image sourceImg) throws IllegalArgumentException {
    // validate the image
    this.validateImage(sourceImg);

    // read the image properties
    int maxValue = sourceImg.getMaxValue();
    int height = sourceImg.getHeight();
    int width = sourceImg.getWidth();

    // create a new 2D array of pixels
    Pixel[][] newPixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel oldPixel = sourceImg.getPixel(i, j);
        int newRed = oldPixel.getRed() + this.amount;
        int newGreen = oldPixel.getGreen() + this.amount;
        int newBlue = oldPixel.getBlue() + this.amount;

        // clamp the values to the range of [0, maxValue]
        newRed = this.clamp(newRed, maxValue);
        newGreen = this.clamp(newGreen, maxValue);
        newBlue = this.clamp(newBlue, maxValue);
        newPixels[i][j] = oldPixel.createPixel(newRed, newGreen, newBlue);
      }
    }
    return new GenericImage(newPixels, maxValue, sourceImg.getImageType());
  }

  /**
   * Clamp a value to the range of [0, 255].
   *
   * @param value    the value to clamp
   * @param maxValue the maximum value of a color in this image
   * @return the clamped value
   */
  private int clamp(int value, int maxValue) {
    if (value < 0) {
      return 0;
    }
    return Math.min(value, maxValue);
  }
}
