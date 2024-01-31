package textgimp;

import static utility.TestHelper.RANDOM_GEN;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.betterimage.RGBPixel;

/**
 * This class is a helper class to generate mock models.
 */
public class ModelMocks {

  /**
   * Creates a generic image with random pixel data.
   *
   * @param height   the height of the image
   * @param width    the width of the image
   * @param maxValue the max value of the pixel
   * @return a generic image with random pixel data
   */
  public static Image createRandomGenericImage(int height, int width,
      int maxValue) {
    Pixel[][] pixelData = new Pixel[height][width];

    // read pixel data from the stream
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = RANDOM_GEN.nextInt(maxValue);
        int g = RANDOM_GEN.nextInt(maxValue);
        int b = RANDOM_GEN.nextInt(maxValue);

        pixelData[i][j] = new RGBPixel(r, g, b, maxValue);
      }
    }

    return new GenericImage(pixelData, maxValue, "ppm");
  }

  /**
   * Creates a generic image with the given pixel data.
   *
   * @param height   the height of the image
   * @param width    the width of the image
   * @param pixel    the pixel to fill the image with
   * @param maxValue the max value of the pixel
   * @return a generic image with the given pixel data
   */
  public static Image createGenericImageWithPixel(int height, int width,
      Pixel pixel, int maxValue) {
    Pixel[][] pixelData = new Pixel[height][width];

    // read pixel data from the stream
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixelData[i][j] = pixel;
      }
    }

    return new GenericImage(pixelData, maxValue, "ppm");
  }

  /**
   * Create a buffered image with the given pixel data.
   * @param height the height of the image
   * @param width the width of the image
   * @param pixel the pixel to fill the image with
   * @return a buffered image with the given pixel data
   */
  public static BufferedImage createBufferedImage(int height, int width,
      Pixel pixel) {
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    WritableRaster raster = img.getRaster();
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        raster.setSample(x, y, 0, pixel.getRed());
        raster.setSample(x, y, 1, pixel.getGreen());
        raster.setSample(x, y, 2, pixel.getBlue());
      }
    }
    return img;
  }
}
