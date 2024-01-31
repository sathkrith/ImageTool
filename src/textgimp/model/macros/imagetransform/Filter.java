package textgimp.model.macros.imagetransform;

import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.macros.AbstractMacro;
import textgimp.model.macros.Macro;

/**
 * This class represents a generic Filter macro. It accepts an N x N matrix and applies it to the
 * image. The matrix size must be an odd number.
 */
class Filter extends AbstractMacro implements Macro {

  private final double[][] filterMatrix;
  private final int matrixSize;

  /**
   * Initialize Filter macro and set filter matrix.
   *
   * @param filterMatrix The filter matrix.
   * @throws IllegalArgumentException if the given matrix is null or empty or not an odd number
   */
  public Filter(double[][] filterMatrix) throws IllegalArgumentException {
    // validate matrix
    this.validateMatrix(filterMatrix);

    this.filterMatrix = filterMatrix;
    this.matrixSize = filterMatrix.length;
  }

  /**
   * Apply the filter matrix to the image.
   *
   * @param sourceImage The image to apply the filter to.
   * @return The image after the filter has been applied.
   * @throws IllegalArgumentException if the given image is null or empty
   */
  @Override
  public Image apply(Image sourceImage) {
    this.validateImage(sourceImage);

    // read image properties
    int imgWidth = sourceImage.getWidth();
    int imgHeight = sourceImage.getHeight();
    int maxValue = sourceImage.getMaxValue();

    // Create a new 2D array of pixels
    Pixel[][] newPixels = new Pixel[imgHeight][imgWidth];

    // Loop through the pixels apply the filter
    for (int i = 0; i < imgHeight; i++) {
      for (int j = 0; j < imgWidth; j++) {
        newPixels[i][j] = this.applyFilter(sourceImage, i, j);
      }
    }
    return new GenericImage(newPixels, maxValue, sourceImage.getImageType());
  }

  /**
   * Apply the filter matrix to a single pixel.
   *
   * @param sourceImage The image to apply the filter to.
   * @param row         The row of the pixel to apply the filter to.
   * @param col         The column of the pixel to apply the filter to.
   * @return The pixel after the filter has been applied.
   */
  private Pixel applyFilter(Image sourceImage, int row, int col) {
    double redSum = 0;
    double greenSum = 0;
    double blueSum = 0;
    Pixel sourcePixel = sourceImage.getPixel(row, col);
    int maxValue = sourceImage.getMaxValue();
    int matrixCenter = this.matrixSize / 2;

    // Loop through the pixels in the matrix and apply the filter
    for (int i = 0; i < this.matrixSize; i++) {
      for (int j = 0; j < this.matrixSize; j++) {
        int pixelRow = row + i - matrixCenter;
        int pixelCol = col + j - matrixCenter;

        // ignore pixels outside the image
        if (pixelRow < 0 || pixelRow >= sourceImage.getHeight()
            || pixelCol < 0 || pixelCol >= sourceImage.getWidth()) {
          continue;
        }

        // apply filter
        redSum += sourceImage.getPixel(pixelRow, pixelCol).getRed() * this.filterMatrix[i][j];
        greenSum += sourceImage.getPixel(pixelRow, pixelCol).getGreen() * this.filterMatrix[i][j];
        blueSum += sourceImage.getPixel(pixelRow, pixelCol).getBlue() * this.filterMatrix[i][j];
      }
    }

    return sourcePixel.createPixel(
        this.clamp(redSum, maxValue),
        this.clamp(greenSum, maxValue),
        this.clamp(blueSum, maxValue));
  }

  /**
   * Validate the filter matrix.
   *
   * @param filterMatrix The filter matrix.
   * @throws IllegalArgumentException if the given matrix is null or empty or not an odd number
   */
  private void validateMatrix(double[][] filterMatrix) throws IllegalArgumentException {
    // check if matrix is null or empty
    if (filterMatrix == null || filterMatrix.length == 0) {
      throw new IllegalArgumentException("Filter matrix cannot be null or empty.");
    }

    // check if matrix has an odd number of rows and columns
    int matrixSize = filterMatrix.length;
    if (matrixSize % 2 == 0) {
      throw new IllegalArgumentException("Filter matrix size must be an odd number.");
    }

    // check if matrix is a square matrix
    for (double[] matrix : filterMatrix) {
      if (matrix.length != matrixSize) {
        throw new IllegalArgumentException("Filter matrix must be a square matrix.");
      }
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