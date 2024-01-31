package textgimp.model.macros.imagetransform;

import textgimp.model.betterimage.Image;
import textgimp.model.macros.AbstractMacro;
import textgimp.model.macros.Macro;

/**
 * This class represents a set of Filter presets. Multiple classes have been moved here to remain
 * under the file limit.
 */
public class FilterPresets {

  /**
   * This macro generates a blurred image. Re-uses th generic Filter macro by providing an
   * appropriate filter matrix.
   */
  static class Blur extends AbstractMacro implements Macro {

    @Override
    public Image apply(Image sourceImage) throws IllegalArgumentException {
      // matrix to blur the image
      double[][] filterMatrix = {
          {1.0 / 16, 1.0 / 8, 1.0 / 16},
          {1.0 / 8, 1.0 / 4, 1.0 / 8},
          {1.0 / 16, 1.0 / 8, 1.0 / 16}
      };
      return new Filter(filterMatrix).apply(sourceImage);
    }
  }

  /**
   * This class represents a macro to sharpen an image. Re-uses the Filter macro by setting the
   * filter matrix to a sharpening matrix.
   */
  static class Sharpen extends AbstractMacro implements Macro {

    @Override
    public Image apply(Image sourceImage) throws IllegalArgumentException {
      // 5 x 5 matrix to sharpen an image
      double[][] filterMatrix = new double[][]{
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
      };
      return new Filter(filterMatrix).apply(sourceImage);
    }
  }
}
