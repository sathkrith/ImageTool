package textgimp.model.macros.colortransform;

import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.macros.AbstractMacro;
import textgimp.model.macros.Macro;

/**
 * This is an RGB color transformation macro. This class accepts a 3x3 transformation matrix and
 * applies it to the RGB values of the image.
 */
class RGBTransform extends AbstractMacro implements Macro {

  private final double[][] transformationMatrix;

  /**
   * Creates a new RGBTransform with the given transformation matrix.
   *
   * @param transformationMatrix of size 3x3
   * @throws IllegalArgumentException if the transformation matrix is not 3x3
   */
  public RGBTransform(double[][] transformationMatrix) throws IllegalArgumentException {
    // validate the transformation matrix size
    if (transformationMatrix.length != 3 || transformationMatrix[0].length != 3) {
      throw new IllegalArgumentException("Transformation matrix must be 3x3");
    }
    this.transformationMatrix = transformationMatrix;
  }

  @Override
  public Image apply(Image sourceImage) {
    // validate the image
    this.validateImage(sourceImage);

    // read the image properties
    int maxValue = sourceImage.getMaxValue();
    int height = sourceImage.getHeight();
    int width = sourceImage.getWidth();

    // create a new 2D array of pixels
    Pixel[][] newPixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // read r, g, b data from the old pixel
        Pixel oldPixel = sourceImage.getPixel(i, j);
        int red = oldPixel.getRed();
        int green = oldPixel.getGreen();
        int blue = oldPixel.getBlue();

        // generate new r, g, b data using the transformation matrix
        int newRed = this.transform(red, green, blue, 0);
        int newGreen = this.transform(red, green, blue, 1);
        int newBlue = this.transform(red, green, blue, 2);

        // clamp the values to the range [0, maxValue]
        newRed = this.clamp(newRed, maxValue);
        newGreen = this.clamp(newGreen, maxValue);
        newBlue = this.clamp(newBlue, maxValue);
        newPixels[i][j] = oldPixel.createPixel(newRed, newGreen, newBlue);
      }
    }
    return new GenericImage(newPixels, maxValue, sourceImage.getImageType());
  }

  /**
   * Transforms the given RGB values using the transformation matrix.
   *
   * @param red   the red value
   * @param green the green value
   * @param blue  the blue value
   * @param row   the row of the transformation matrix to use
   * @return the transformed value
   */
  private int transform(int red, int green, int blue, int row) {
    int[] rgb = {red, green, blue};
    double transformedValue = 0;
    for (int i = 0; i < 3; i++) {
      transformedValue += this.transformationMatrix[row][i] * rgb[i];
    }
    return Math.round((float) transformedValue);
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