package textgimp.model.macros.imagetransform;

import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.macros.AbstractMacro;
import textgimp.model.macros.Macro;
import textgimp.model.macros.colortransform.ColorTransformPresetManager;
import textgimp.model.macros.colortransform.ColorTransformType;

/**
 * This class represents a Dither macro. It applies a dithering effect to the image.
 */
public class Dither extends AbstractMacro implements Macro {

  @Override
  public Image apply(Image sourceImage) throws IllegalArgumentException {
    // validate image
    this.validateImage(sourceImage);

    // read image properties
    int imgWidth = sourceImage.getWidth();
    int imgHeight = sourceImage.getHeight();
    int maxValue = sourceImage.getMaxValue();

    // generate greyscaled image using luma component
    Macro macro = new ColorTransformPresetManager().getColorTransform(
        ColorTransformType.GREYSCALE_LUMA);
    Image greyImage = macro.apply(sourceImage);

    // Create a new 2D array of pixels
    Pixel[][] newPixels = new Pixel[imgHeight][imgWidth];

    // Loop through the pixels and copy the pixel to a new image
    // this is required because dithering effects the pixels around the current pixel
    for (int i = 0; i < imgHeight; i++) {
      for (int j = 0; j < imgWidth; j++) {
        newPixels[i][j] = greyImage.getPixel(i, j);
      }
    }

    // Loop through the pixels and apply dithering
    for (int i = 0; i < imgHeight; i++) {
      for (int j = 0; j < imgWidth; j++) {
        this.applyDither(newPixels, i, j, maxValue);
      }
    }
    return new GenericImage(newPixels, maxValue, sourceImage.getImageType());
  }


  /**
   * Apply floyd-steinberg dithering to a pixel.
   *
   * @param imgPixels 2D array of pixels representing an image
   * @param row       The row of the pixel to apply the filter to.
   * @param col       The column of the pixel to apply the filter to.
   * @param maxValue  The max value of the color in the image.
   */
  private void applyDither(Pixel[][] imgPixels, int row, int col, int maxValue) {
    // read the pixel
    Pixel sourcePixel = imgPixels[row][col];

    // read the pixel's color values
    int red = sourcePixel.getRed();
    int green = sourcePixel.getGreen();
    int blue = sourcePixel.getBlue();

    // calculate the new color values
    int newRed = this.calculateNewColor(red, maxValue);
    int newGreen = this.calculateNewColor(green, maxValue);
    int newBlue = this.calculateNewColor(blue, maxValue);

    // calculate the error
    int redError = red - newRed;
    int greenError = green - newGreen;
    int blueError = blue - newBlue;

    // apply the error to the neighboring pixels
    this.updateNeighbours(imgPixels, row, col, redError, greenError, blueError, maxValue);

    // set the current pixel to the new color values
    imgPixels[row][col] = sourcePixel.createPixel(newRed, newGreen, newBlue);
  }

  /**
   * Apply the error to the neighboring pixels.
   *
   * @param imgPixels  2D array of pixels representing an image
   * @param row        The row of the source pixel.
   * @param col        The column of the source pixel.
   * @param redError   The red error to apply.
   * @param greenError The green error to apply.
   * @param blueError  The blue error to apply.
   */
  private void updateNeighbours(Pixel[][] imgPixels, int row, int col, int redError, int greenError,
      int blueError, int maxValue) {
    // read the width and height of the image
    int imgWidth = imgPixels[0].length;
    int imgHeight = imgPixels.length;

    // apply the error to (row, col + 1) pixel
    if (col + 1 < imgWidth) {
      this.applyErrorToPixel(7, imgPixels, row, col + 1, redError, greenError, blueError, maxValue);
    }

    // apply error to (row + 1, col - 1) pixel
    if (row + 1 < imgHeight && col - 1 >= 0) {
      this.applyErrorToPixel(3, imgPixels, row + 1, col - 1, redError, greenError, blueError,
          maxValue);
    }

    // apply error to (row + 1, col) pixel
    if (row + 1 < imgHeight) {
      this.applyErrorToPixel(5, imgPixels, row + 1, col, redError, greenError, blueError, maxValue);
    }

    // apply error to (row + 1, col + 1) pixel
    if (row + 1 < imgHeight && col + 1 < imgWidth) {
      this.applyErrorToPixel(1, imgPixels, row + 1, col + 1, redError, greenError, blueError,
          maxValue);
    }
  }

  /**
   * Apply the error to a single pixel. The error is multiplied by the factor.
   *
   * @param imgPixels  2D array of pixels representing an image
   * @param row        The row of the pixel to apply the error to.
   * @param col        The column of the pixel to apply the error to.
   * @param redError   The red error to apply.
   * @param greenError The green error to apply.
   * @param blueError  The blue error to apply.
   * @param factor     The factor to apply the error by.
   */
  private void applyErrorToPixel(int factor, Pixel[][] imgPixels, int row, int col, int redError,
      int greenError, int blueError, int maxValue) {
    // read the pixel
    Pixel sourcePixel = imgPixels[row][col];

    // read the pixel's color values
    double red = sourcePixel.getRed();
    double green = sourcePixel.getGreen();
    double blue = sourcePixel.getBlue();

    // calculate the new color values
    red += (redError * factor / 16.0);
    green += (greenError * factor / 16.0);
    blue += (blueError * factor / 16.0);

    int newRed = this.clamp(red, maxValue);
    int newGreen = this.clamp(green, maxValue);
    int newBlue = this.clamp(blue, maxValue);

    // set the current pixel to the new color values
    imgPixels[row][col] = sourcePixel.createPixel(newRed, newGreen, newBlue);
  }

  /**
   * Calculate the new color value. 0 or max value whichever is closer.
   *
   * @param color    The color value to calculate the new value for.
   * @param maxValue The max value of the color.
   * @return The new color value.
   */
  private int calculateNewColor(int color, int maxValue) {
    int halfMaxValue = maxValue / 2;
    if (color < halfMaxValue) {
      return 0;
    } else {
      return maxValue;
    }
  }

  /**
   * Clamp a value to the range of [0, 255].
   *
   * @param value    the value to clamp
   * @param maxValue the maximum value of a color in this image
   * @return the clamped value
   */
  private int clamp(double value, int maxValue) {
    if (value < 0) {
      return 0;
    }
    value = Math.round(value);
    return Math.min((int) value, maxValue);
  }
}
