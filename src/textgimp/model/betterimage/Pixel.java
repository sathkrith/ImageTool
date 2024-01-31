package textgimp.model.betterimage;


/**
 * This interface represents a Pixel in an image. Stores the red, green, and blue channels of the
 * pixel by default. Implementations of this interface can add additional channels as needed.
 * Implementations of this interface should be immutable.
 */
public interface Pixel {

  /**
   * Get the red component of the pixel.
   *
   * @return the red component
   */
  int getRed();

  /**
   * Get the green component of the pixel.
   *
   * @return the green component
   */
  int getGreen();

  /**
   * Get the blue component of the pixel.
   *
   * @return the blue component
   */
  int getBlue();

  /**
   * Create a pixel of the current type using the specified components.
   *
   * @param red   the red component
   * @param green the green component
   * @param blue  the blue component
   * @return a new pixel
   */
  Pixel createPixel(int red, int green, int blue);

}
