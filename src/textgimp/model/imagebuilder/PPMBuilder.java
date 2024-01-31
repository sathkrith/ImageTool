package textgimp.model.imagebuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import textgimp.model.betterimage.GenericImage;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.betterimage.RGBPixel;

/**
 * This class represents a builder for PPM images. This class is responsible for encoding and
 * decoding PPM images.
 */
public class PPMBuilder implements ImageBuilder {

  @Override
  public Image loadImage(byte[] data) throws IllegalArgumentException {
    if (data == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }

    // create byte stream
    InputStream in = new ByteArrayInputStream(data);

    // read image data from the string
    String imgData = this.inputToString(in);
    Scanner sc = new Scanner(imgData);

    // check if the file is a valid PPM file
    String token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    // read the width and height of the image
    int width = sc.nextInt();
    int height = sc.nextInt();

    // read the maximum value of a color in this file (usually 255)
    int maxValue = sc.nextInt();
    Pixel[][] pixelData = new Pixel[height][width];

    // read pixel data from the stream
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        pixelData[i][j] = new RGBPixel(r, g, b, maxValue);
      }
    }
    return new GenericImage(pixelData, maxValue, "ppm");
  }

  @Override
  public byte[] writeImage(Image image) throws IllegalArgumentException, IOException {
    // check if the image object is null
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }

    // write headers
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int width = image.getWidth();
    int height = image.getHeight();
    this.outWriter("P3", out);
    this.outWriter(width + " " + height, out);
    this.outWriter(image.getMaxValue() + "", out);

    // write pixel data
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel imgPixel = image.getPixel(i, j);
        this.outWriter(imgPixel.getRed() + "", out);
        this.outWriter(imgPixel.getGreen() + "", out);
        this.outWriter(imgPixel.getBlue() + "", out);
      }
    }
    // new line at the end
    this.outWriter("", out);
    return out.toByteArray();
  }

  /**
   * Helper method to write a string to an output stream.
   *
   * @param s   the string to write
   * @param out the output stream to write to
   * @throws IOException if an error occurs while writing to the output stream
   */
  private void outWriter(String s, OutputStream out) throws IOException {
    try {
      // add a line separator to the end of the string
      s += System.lineSeparator();
      out.write(s.getBytes());
    } catch (IOException e) {
      throw new IOException("Error occurred while writing image to output stream.");
    }
  }

  /**
   * Helper method to convert input stream to a string.
   *
   * @param in the input stream to convert
   * @return the string representation of the input stream
   * @throws IllegalArgumentException if the given input stream is null
   */
  private String inputToString(InputStream in) {
    if (in == null) {
      throw new IllegalArgumentException("Input stream cannot be null.");
    }

    // load string data from input stream
    StringBuilder sb = new StringBuilder();
    Scanner sc = new Scanner(in);

    // read data line by line and ignore any comments
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      if (!line.startsWith("#")) {
        line = line + System.lineSeparator();
        sb.append(line);
      }
    }
    return sb.toString();
  }
}
