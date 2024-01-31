package textgimp.model.betterimage;

/**
 * This class represents an RGB pixel in an image. It implements the RGBPixel interface, and stores
 * the red, green, and blue channels of the pixel.
 */
public class RGBPixel implements Pixel {

  private final int red;
  private final int green;
  private final int blue;
  private final int maxValue;

  /**
   * Create a new RGB pixel with the given red, green, and blue components.
   *
   * @param red      the red component
   * @param green    the green component
   * @param blue     the blue component
   * @param maxValue the maximum value of a color in this image (usually 255)
   * @throws IllegalArgumentException if any of the components are not in the range [0, 255]
   */
  public RGBPixel(int red, int green, int blue, int maxValue) throws IllegalArgumentException {
    if (maxValue < 0) {
      throw new IllegalArgumentException("The maximum value of a color cannot be negative.");
    }
    this.maxValue = maxValue;

    if (!isValid(red)) {
      throw new IllegalArgumentException(
          "The red component must be in the range [0, " + this.maxValue + "].");
    }
    if (!isValid(green)) {
      throw new IllegalArgumentException(
          "The green component must be in the range [0, " + this.maxValue + "].");
    }
    if (!isValid(blue)) {
      throw new IllegalArgumentException(
          "The blue component must be in the range [0, " + this.maxValue + "].");
    }

    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public Pixel createPixel(int red, int green, int blue) {
    return new RGBPixel(red, green, blue, this.maxValue);
  }

  /**
   * Check if a given value is in the range of [0, 255].
   *
   * @param value the value to check
   * @return true if the value is in the range of [0, 255], false otherwise
   */
  private boolean isValid(int value) {
    return value >= 0 && value <= this.maxValue;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof RGBPixel)) {
      return false;
    }
    RGBPixel other = (RGBPixel) obj;
    return this.red == other.getRed()
        && this.green == other.getGreen()
        && this.blue == other.getBlue();
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 13 * this.red + result;
    result = 23 * this.green + result;
    result = 31 * this.blue + result;
    return result;
  }
}
