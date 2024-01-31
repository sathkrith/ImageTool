package textgimp.model.imagebuilder;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import textgimp.model.betterimage.Image;

/**
 * This is a JUnit test class to test save and load images of different types.
 */
public class ImageConverterTest {

  private Map<String, byte[]> images;
  private Map<String, String> imagePaths;

  /**
   * Set up test data - load files and images.
   */
  public ImageConverterTest() {
    this.loadImagePaths();
    this.loadImageFiles();
  }

  /**
   * This method tests the image conversion using different builders.
   */
  @Test
  public void builderTest() {
    // create all type of builders
    ImageBuilder bmpBuilder = new BMPBuilder();
    ImageBuilder pngBuilder = new PNGBuilder();
    ImageBuilder jpgBuilder = new JPGBuilder();
    ImageBuilder ppmBuilder = new PPMBuilder();

    byte[] elephantPNG = this.images.get("elephant-bmp");
    byte[] elephantPPM = this.images.get("elephant-ppm");
    byte[] elephantBMP = this.images.get("elephant-png");
    byte[] elephantJPG = this.images.get("elephant-jpg");

    // bmp to other formats
    Image image = bmpBuilder.loadImage(elephantBMP);

    // write the image to an output stream using different builders
    byte[] pngData = null;
    byte[] jpgData = null;
    byte[] ppmData = null;
    try {
      pngData = pngBuilder.writeImage(image);
      jpgData = jpgBuilder.writeImage(image);
      ppmData = ppmBuilder.writeImage(image);
    } catch (Exception e) {
      fail("Exception thrown when writing image: " + e.getMessage());
    }

    // compare the image data to the original image data
    assertTrue(compareImageBytes(elephantPNG, pngData));
    assertTrue(compareImageBytes(elephantJPG, jpgData));
    assertTrue(compareImageBytes(elephantPPM, ppmData));

    // png to other formats
    image = pngBuilder.loadImage(elephantPNG);

    // write the image to an output stream using different builders
    byte[] bmpData = null;
    jpgData = null;
    ppmData = null;
    try {
      bmpData = bmpBuilder.writeImage(image);
      jpgData = jpgBuilder.writeImage(image);
      ppmData = ppmBuilder.writeImage(image);
    } catch (Exception e) {
      fail("Exception thrown when writing image: " + e.getMessage());
    }

    // compare the image data to the original image data
    assertTrue(compareImageBytes(elephantBMP, bmpData));
    assertTrue(compareImageBytes(elephantJPG, jpgData));
    assertTrue(compareImageBytes(elephantPPM, ppmData));

    // jpg to other formats
    image = jpgBuilder.loadImage(elephantJPG);

    // write the image to an output stream using different builders
    bmpData = null;
    pngData = null;
    ppmData = null;
    try {
      bmpData = bmpBuilder.writeImage(image);
      pngData = pngBuilder.writeImage(image);
      ppmData = ppmBuilder.writeImage(image);
    } catch (Exception e) {
      fail("Exception thrown when writing image: " + e.getMessage());
    }

    // compare the image data to the original image data
    assertTrue(compareImageBytes(elephantBMP, bmpData));
    assertTrue(compareImageBytes(elephantPNG, pngData));
    assertTrue(compareImageBytes(elephantPPM, ppmData));

    // ppm to other formats
    image = ppmBuilder.loadImage(elephantPPM);

    // write the image to an output stream using different builders
    bmpData = null;
    pngData = null;
    jpgData = null;
    try {
      bmpData = bmpBuilder.writeImage(image);
      pngData = pngBuilder.writeImage(image);
      jpgData = jpgBuilder.writeImage(image);
    } catch (Exception e) {
      fail("Exception thrown when writing image: " + e.getMessage());
    }

    // compare the image data to the original image data
    assertTrue(compareImageBytes(elephantBMP, bmpData));
    assertTrue(compareImageBytes(elephantPNG, pngData));
    assertTrue(compareImageBytes(elephantJPG, jpgData));
  }

  /**
   * This is a helper method to load image paths for testing.
   */
  private void loadImagePaths() {
    // add image paths
    this.imagePaths = new HashMap<String, String>();
    this.imagePaths.put("elephant-bmp", "res/elephant.bmp");
    this.imagePaths.put("elephant-png", "res/elephant.png");
    this.imagePaths.put("elephant-jpg", "res/elephant.jpg");
    this.imagePaths.put("elephant-ppm", "res/elephant.ppm");
  }

  /**
   * This is a helper method to load image files for testing.
   */
  private void loadImageFiles() {
    // load all images as byte arrays by reading from the file system
    this.images = new HashMap<String, byte[]>();

    for (String name : this.imagePaths.keySet()) {
      String path = this.imagePaths.get(name);
      File f = new File(path);

      try {
        byte[] fileContent = Files.readAllBytes(f.toPath());
        this.images.put(name, fileContent);
      } catch (Exception e) {
        fail("Failed to load image file: " + path);
      }
    }
  }

  /**
   * This is a helper method to compare two images by comparing their byte arrays.
   *
   * @param image1 - the first image as a byte array
   * @param image2 - the second image as a byte array
   * @return true if the images are the same, false otherwise
   */
  private boolean compareImageBytes(byte[] image1, byte[] image2) {
    if (image1 == null || image2 == null) {
      return false;
    }

    // convert both images to strings
    String image1String = new String(image1);
    String image2String = new String(image2);

    // compare the strings
    return image1String.equals(image2String);
  }
}