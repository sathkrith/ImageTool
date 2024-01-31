package textgimp.model.betterimage;

/**
 * This class represents a PNG pixel in an image. It extends the RGBPixel class and adds the
 * transparency channel.
 */
public class PNGPixel extends RGBPixel implements Pixel {

  private final int transparency;
  private final int maxValue;

  /**
   * Create a new PNG pixel object with the given red, green, blue, and transparency components.
   *
   * @param red          the red component
   * @param green        the green component
   * @param blue         the blue component
   * @param transparency the transparency value
   * @param maxValue     the maximum value of a color in this image (usually 255)
   * @throws IllegalArgumentException if any of the components are not in the range [0, 255]
   */
  public PNGPixel(int red, int green, int blue, int transparency, int maxValue)
      throws IllegalArgumentException {
    // create a new RGB pixel - call to super
    super(red, green, blue, maxValue);
    this.maxValue = maxValue;

    // validate the transparency value
    if (!isValid(transparency)) {
      throw new IllegalArgumentException(
          "The transparency value must be in the range [0, " + this.maxValue + "].");
    }
    this.transparency = transparency;
  }

  /**
   * Get the transparency value of this pixel.
   *
   * @return the transparency value
   */
  public int getTransparency() {
    return this.transparency;
  }

  @Override
  public Pixel createPixel(int red, int green, int blue) {
    return new PNGPixel(red, green, blue, this.transparency, this.maxValue);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PNGPixel)) {
      return false;
    }
    PNGPixel other = (PNGPixel) obj;
    return this.getRed() == other.getRed()
        && this.getGreen() == other.getGreen()
        && this.getBlue() == other.getBlue()
        && this.getTransparency() == other.getTransparency();
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 13 * this.getRed() + result;
    result = 23 * this.getGreen() + result;
    result = 31 * this.getBlue() + result;
    result = 37 * this.getTransparency() + result;
    return result;
  }

  /**
   * Check if a given value is in the range of [0, maxValue].
   *
   * @param value the value to check
   * @return true if the value is in the range of [0, maxValue], false otherwise
   */
  private boolean isValid(int value) {
    return value >= 0 && value <= this.maxValue;
  }
}
