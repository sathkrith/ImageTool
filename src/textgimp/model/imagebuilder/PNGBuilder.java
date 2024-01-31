package textgimp.model.imagebuilder;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.PNGPixel;
import textgimp.model.betterimage.Pixel;

/**
 * This class represents a builder for PNG images. This class is responsible for encoding and
 * decoding PNG images.
 */
public class PNGBuilder extends AbstractImageBuilder implements ImageBuilder {

  @Override
  public Image loadImage(byte[] data) throws IllegalArgumentException {
    if (data == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }

    // create byte stream
    InputStream in = new ByteArrayInputStream(data);

    // use the ImageIO class to read the image
    BufferedImage img;
    try {
      img = ImageIO.read(in);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid PNG file.");
    }

    // validate that the image has 4 channels
    if (img.getRaster().getNumBands() != 4) {
      throw new IllegalArgumentException("Invalid PNG file.");
    }

    // read image attributes
    int width = img.getWidth();
    int height = img.getHeight();
    int maxValue = 255;
    Pixel[][] pixelData = new Pixel[height][width];

    // read pixel data
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgbData = new int[4];
        img.getRaster().getPixel(j, i, rgbData);
        pixelData[i][j] = new PNGPixel(rgbData[0], rgbData[1], rgbData[2], rgbData[3], 255);
      }
    }
    return new GenericImage(pixelData, maxValue, "png");
  }

  @Override
  public byte[] writeImage(Image image) throws IllegalArgumentException, IOException {
    // check if the image object is null
    if (image == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }

    // get image attributes
    int width = image.getWidth();
    int height = image.getHeight();

    // create a BufferedImage object
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    // write pixel data
    int red;
    int green;
    int blue;
    int transparency;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel p = image.getPixel(i, j);
        red = p.getRed();
        green = p.getGreen();
        blue = p.getBlue();

        // read transparency if the pixel is a PNGPixel
        Color c;
        if (p instanceof PNGPixel) {
          transparency = ((PNGPixel) p).getTransparency();
          c = new Color(red, green, blue, transparency);
        } else {
          c = new Color(red, green, blue);
        }

        // write pixel data
        img.setRGB(j, i, c.getRGB());
      }
    }
    return this.bufferedImageToByteArray(img, "png");
  }
}
