package textgimp.model.macros;

import textgimp.model.betterimage.Image;

/**
 * This abstract class contains common methods that are used by all macros.
 */
public abstract class AbstractMacro implements Macro {

  /**
   * Validates the given image.
   *
   * @param sourceImage The image to validate.
   * @throws IllegalArgumentException if the given image is null or empty
   */
  protected void validateImage(Image sourceImage) throws IllegalArgumentException {
    if (sourceImage == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    } else if (sourceImage.getHeight() <= 0 || sourceImage.getWidth() <= 0) {
      throw new IllegalArgumentException("Image has no pixels.");
    }
  }
}

