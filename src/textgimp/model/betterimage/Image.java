package textgimp.model.betterimage;

/**
 * This interface represents an image with pixels. Implementations of this interface should be
 * immutable.
 */
public interface Image {

  /**
   * Get the width of the image.
   *
   * @return the width of the image
   */
  int getWidth();

  /**
   * Get the height of the image.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Get the maximum value of a color in this image (usually 255).
   *
   * @return the maximum value of the image
   */
  int getMaxValue();

  /**
   * Get the pixel at the specified coordinates.
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return the pixel at the specified coordinates
   * @throws IllegalArgumentException if the coordinates are out of bounds
   */
  Pixel getPixel(int x, int y) throws IllegalArgumentException;

  /**
   * Get the type of the image.
   *
   * @return String type of the image.
   */
  String getImageType();
}
