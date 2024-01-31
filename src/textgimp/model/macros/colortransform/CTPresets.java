package textgimp.model.macros.colortransform;


import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.macros.AbstractMacro;
import textgimp.model.macros.Macro;

/**
 * This class represents a set of presets for color transformation. Multiple classes have been moved
 * here to remain under the file limit.
 */
public class CTPresets {

  static class GreyscaleBlue extends AbstractMacro implements Macro {

    @Override
    public Image apply(Image sourceImg) {
      // matrix to read blue component
      double[][] transformMatrix = {{0, 0, 1}, {0, 0, 1}, {0, 0, 1}};
      return new RGBTransform(transformMatrix).apply(sourceImg);
    }
  }

  /**
   * This macro generates a greyscale image using the green component. Re-uses the generic
   * RGBTransform macro by passing the appropriate matrix.
   */
  static class GreyscaleGreen extends AbstractMacro implements Macro {

    @Override
    public Image apply(Image sourceImg) {
      // matrix to read green component
      double[][] transformMatrix = {{0, 1, 0}, {0, 1, 0}, {0, 1, 0}};
      return new RGBTransform(transformMatrix).apply(sourceImg);
    }
  }

  /**
   * This macro generates a greyscale image using the red component. Re-uses the generic
   * RGBTransform macro by passing the appropriate matrix.
   */
  static class GreyscaleRed extends AbstractMacro implements Macro {

    @Override
    public Image apply(Image sourceImg) {
      // matrix to read red component
      double[][] transformMatrix = {{1, 0, 0}, {1, 0, 0}, {1, 0, 0}};
      return new RGBTransform(transformMatrix).apply(sourceImg);
    }

  }

  /**
   * This macro generates a greyscale image by using intensity component. Intensity component is
   * calculated as the average of the RGB components. Re-uses the generic RGBTransform macro by
   * passing the appropriate matrix.
   */
  static class GreyscaleIntensity extends AbstractMacro implements Macro {

    @Override
    public Image apply(Image sourceImg) {
      // matrix to read intensity component
      double[][] transformMatrix = {
          {1.0 / 3, 1.0 / 3, 1.0 / 3},
          {1.0 / 3, 1.0 / 3, 1.0 / 3},
          {1.0 / 3, 1.0 / 3, 1.0 / 3}
      };
      return new RGBTransform(transformMatrix).apply(sourceImg);
    }
  }

  /**
   * This macro generates a greyscale image by using luma component. Luma component is calculated as
   * the weighted average of the RGB components. Re-uses the generic RGBTransform macro by passing
   * the appropriate matrix.
   */
  static class GreyscaleLuma extends AbstractMacro implements Macro {

    @Override
    public Image apply(Image sourceImg) {
      // matrix to generate luma component
      double[][] transformMatrix = {
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722}
      };
      return new RGBTransform(transformMatrix).apply(sourceImg);
    }
  }

  /**
   * This macro generates a greyscale image using the value component. Value component is the max of
   * the RGB values.
   */
  static class GreyScaleValue extends AbstractMacro implements Macro {

    @Override
    public Image apply(Image sourceImage) {
      // validate the image
      this.validateImage(sourceImage);

      // read the image properties
      int maxValue = sourceImage.getMaxValue();
      int height = sourceImage.getHeight();
      int width = sourceImage.getWidth();

      // create a new 2D array of pixels
      Pixel[][] newPixels = new Pixel[height][width];

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          Pixel oldPixel = sourceImage.getPixel(i, j);
          int value = Math.max(oldPixel.getRed(),
              Math.max(oldPixel.getGreen(), oldPixel.getBlue()));
          newPixels[i][j] = oldPixel.createPixel(value, value, value);
        }
      }
      return new GenericImage(newPixels, maxValue, sourceImage.getImageType());
    }
  }


  /**
   * This macro applies sepia tone transform to an image. Re-uses the generic RGBTransform macro by
   * passing the appropriate matrix.
   */
  static class Sepia extends AbstractMacro implements Macro {

    @Override
    public Image apply(Image sourceImg) {
      // matrix to generate sepia tone
      double[][] transformMatrix = {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131}
      };
      return new RGBTransform(transformMatrix).apply(sourceImg);
    }
  }
}
