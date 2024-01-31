package textgimp.model.macros;

import textgimp.model.betterimage.Image;

/**
 * This interface represents a macro that can be applied to an image. Macros are used for color
 * transformation, image manipulation, and filters.
 */
public interface Macro {

  /**
   * Applies the macro to the given image.
   *
   * @param sourceImage The image to apply the macro to.
   * @return The image after the macro has been applied.
   * @throws IllegalArgumentException if the given image is null or empty
   */
  Image apply(Image sourceImage) throws IllegalArgumentException;
}
