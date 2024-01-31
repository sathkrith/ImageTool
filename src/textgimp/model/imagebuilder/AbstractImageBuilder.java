package textgimp.model.imagebuilder;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.betterimage.RGBPixel;

/**
 * This is an abstract class that implements the ImageBuilder interface. It provides helper methods
 * for encoding and decoding images.
 */
abstract class AbstractImageBuilder implements ImageBuilder {

  /**
   * Helper method to convert a BufferedImage to a byte array.
   *
   * @param image   the BufferedImage to convert
   * @param imgType the type of the image (format)
   * @return the byte array representation of the image
   * @throws IOException if an error occurs while writing to the output stream
   */
  protected byte[] bufferedImageToByteArray(BufferedImage image, String imgType)
      throws IOException {
    if (image == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }
    if (imgType == null) {
      throw new IllegalArgumentException("Image type cannot be null.");
    }

    // create an output stream
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    // use the ImageIO class to write the image
    try {
      ImageIO.write(image, imgType, out);
      out.flush();
    } catch (IOException e) {
      throw new IOException("Error occurred while writing image to output stream.");
    }
    return out.toByteArray();
  }

  /**
   * Helper method to read a generic RGB image using ImageIO. This method assumes that the image is
   * in the RGB format with 3 channels.
   *
   * @param data    the byte array representation of the image
   * @param imgType the type of the image (format)
   * @return the image object
   * @throws IllegalArgumentException if the given byte array is null or the image is not in the RGB
   *                                  format
   */
  protected Image parseRGB(byte[] data, String imgType) throws IllegalArgumentException {
    if (data == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }
    if (imgType == null) {
      throw new IllegalArgumentException("Image type cannot be null.");
    }

    // create byte stream
    InputStream in = new ByteArrayInputStream(data);

    // use the ImageIO class to read the image
    BufferedImage img;
    try {
      img = ImageIO.read(in);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid " + imgType + " file.");
    }

    // validate that the image has 3 channels
    if (img.getRaster().getNumBands() != 3) {
      throw new IllegalArgumentException("Invalid " + imgType + " file.");
    }

    // read image attributes
    int width = img.getWidth();
    int height = img.getHeight();
    int maxValue = 255;
    Pixel[][] pixelData = new Pixel[height][width];

    // read pixel data
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgbData = new int[3];
        img.getRaster().getPixel(j, i, rgbData);
        pixelData[i][j] = new RGBPixel(rgbData[0], rgbData[1], rgbData[2], maxValue);
      }
    }
    return new GenericImage(pixelData, maxValue, imgType);
  }

  /**
   * Helper method to write an Image to a byte array using ImageIO. This method assumes that the
   * image is in the RGB format with 3 channels.
   *
   * @param image   the image to write
   * @param imgType the type of the image (format)
   * @return the byte array representation of the image
   * @throws IOException if an error occurs while writing to the output stream
   */
  protected byte[] writeRGB(Image image, String imgType) throws IOException {
    if (image == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }
    if (imgType == null) {
      throw new IllegalArgumentException("Image type cannot be null.");
    }

    // get image attributes
    int width = image.getWidth();
    int height = image.getHeight();

    // create a buffered image
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // write pixel data
    int red;
    int green;
    int blue;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        red = image.getPixel(i, j).getRed();
        green = image.getPixel(i, j).getGreen();
        blue = image.getPixel(i, j).getBlue();

        Color c = new Color(red, green, blue);
        img.setRGB(j, i, c.getRGB());
      }
    }
    return this.bufferedImageToByteArray(img, imgType);
  }
}
