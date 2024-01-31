package textgimp.model.betterimage;

/**
 * This class represents a generic image with Pixels. The image is stored as a 2D array of Pixels.
 * This class is immutable, and the pixels cannot be changed after the image is created.
 */
public final class GenericImage implements Image {

  private final Pixel[][] pixels;
  private final int width;
  private final int height;
  private final int maxValue;

  private final String type;

  /**
   * Create a new Image with the given width, height, and pixel objects.
   *
   * @param pixels   the pixels of the image
   * @param maxValue the maximum value of a color in this image (usually 255)
   * @param type     type of the image.
   * @throws IllegalArgumentException if the given pixel array is null or empty or if the given
   *                                  maximum value is less than 0
   */
  public GenericImage(Pixel[][] pixels, int maxValue, String type) throws IllegalArgumentException {
    if (pixels == null || pixels.length == 0 || pixels[0].length == 0) {
      throw new IllegalArgumentException("Image has no pixels.");
    }
    if (maxValue < 0) {
      throw new IllegalArgumentException("Maximum value of a color cannot be less than 0.");
    }
    this.width = pixels[0].length;
    this.height = pixels.length;
    this.pixels = pixels;
    this.maxValue = maxValue;
    this.type = type;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public Pixel getPixel(int x, int y) throws IllegalArgumentException {
    if (x < 0 || x >= this.height || y < 0 || y >= this.width) {
      throw new IllegalArgumentException("There is no pixel with the given coordinates.");
    }
    return this.pixels[x][y];
  }

  @Override
  public String getImageType() {
    return type;
  }


  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof GenericImage)) {
      return false;
    }

    // check width and height of other image
    GenericImage other = (GenericImage) o;
    if (this.width != other.getWidth()
        || this.height != other.getHeight()
        || this.maxValue != other.getMaxValue()) {
      return false;
    }

    // check if all pixels are the same
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (!this.pixels[i][j].equals(other.getPixel(i, j))) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 13 * this.width + result;
    result = 23 * this.height + result;
    result = 31 * this.maxValue + result;
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        result = result + 7 * this.pixels[i][j].hashCode();
      }
    }
    return result;
  }
}
