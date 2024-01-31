package textgimp.model.imagebuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import textgimp.model.betterimage.Image;

/**
 * This class represents a JUnit test class for all the ImageBuilder classes.
 */
public class ImageBuilderTest {

  /**
   * This is a JUnit test class for the BMPBuilder class.
   */
  class BMPBuilderTest {

    private Map<String, byte[]> images;
    private Map<String, String> imagePaths;

    /**
     * Set up test data - load files and images.
     */
    public BMPBuilderTest() {
      this.loadImagePaths();
      this.loadImageFiles();
    }

    /**
     * This method tests the builder by loading and writing an image using the builder.
     */
    @Test
    public void builderTest() {
      // create a builder
      ImageBuilder builder = new BMPBuilder();

      // load an image
      byte[] elephant = this.images.get("elephant");
      Image image = builder.loadImage(elephant);

      // write the image to an output stream
      byte[] imageData = null;
      try {
        imageData = builder.writeImage(image);
      } catch (Exception e) {
        fail("Exception thrown when writing image: " + e.getMessage());
      }

      // compare the image data to the original image data
      assertTrue(compareImageBytes(elephant, imageData));
      assertFalse(compareImageBytes(elephant, this.images.get("elephant-horizontal")));
    }

    /**
     * This is a helper method to load image paths for testing.
     */
    private void loadImagePaths() {
      // add image paths
      this.imagePaths = new HashMap<String, String>();
      this.imagePaths.put("elephant", "res/elephant.bmp");
      this.imagePaths.put("elephant-horizontal", "res/elephant-horizontal.bmp");
    }

    /**
     * This is a helper method to load imagefiles for testing.
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

  /**
   * This is a JUnit test class for the JPGBuilder class.
   */
  class JPGBuilderTest {

    private Map<String, byte[]> images;
    private Map<String, String> imagePaths;

    /**
     * Set up test data - load files and images.
     */
    public JPGBuilderTest() {
      this.loadImagePaths();
      this.loadImageFiles();
    }

    /**
     * This method tests the builder by loading and writing an image using the builder.
     */
    @Test
    public void builderTest() {
      // create a builder
      ImageBuilder builder = new JPGBuilder();

      // load an image
      byte[] elephant = this.images.get("elephant");
      Image image = builder.loadImage(elephant);

      // write the image to an output stream
      byte[] imageData = null;
      try {
        imageData = builder.writeImage(image);
      } catch (Exception e) {
        fail("Exception thrown when writing image: " + e.getMessage());
      }

      // compare the image data to the original image data
      assertTrue(compareImageBytes(elephant, imageData));
      assertFalse(compareImageBytes(elephant, this.images.get("elephant-horizontal")));
    }

    /**
     * This is a helper method to load image paths for testing.
     */
    private void loadImagePaths() {
      // add image paths
      this.imagePaths = new HashMap<String, String>();
      this.imagePaths.put("elephant", "res/elephant.jpg");
      this.imagePaths.put("elephant-horizontal", "res/elephant-horizontal.jpg");
    }

    /**
     * This is a helper method to load imagefiles for testing.
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

  /**
   * This is a JUnit test class for the PNGBuilder class.
   */
  class PNGBuilderTest {

    private Map<String, byte[]> images;
    private Map<String, String> imagePaths;

    /**
     * Set up test data - load files and images.
     */
    public PNGBuilderTest() {
      this.loadImagePaths();
      this.loadImageFiles();
    }

    /**
     * This method tests the builder by loading and writing an image using the builder.
     */
    @Test
    public void builderTest() {
      // create a builder
      ImageBuilder builder = new PNGBuilder();

      // load an image
      byte[] elephant = this.images.get("elephant");
      Image image = builder.loadImage(elephant);

      // write the image to an output stream
      byte[] imageData = null;
      try {
        imageData = builder.writeImage(image);
      } catch (Exception e) {
        fail("Exception thrown when writing image: " + e.getMessage());
      }

      // compare the image data to the original image data
      assertTrue(compareImageBytes(elephant, imageData));
      assertFalse(compareImageBytes(elephant, this.images.get("elephant-horizontal")));
    }

    /**
     * This is a helper method to load image paths for testing.
     */
    private void loadImagePaths() {
      // add image paths
      this.imagePaths = new HashMap<String, String>();
      this.imagePaths.put("elephant", "res/elephant.png");
      this.imagePaths.put("elephant-horizontal", "res/elephant-horizontal.png");
    }

    /**
     * This is a helper method to load imagefiles for testing.
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

  /**
   * This is a JUnit test class for the PPMBuilder class.
   */
  class PPMBuilderTest {

    private Map<String, byte[]> images;
    private Map<String, String> imagePaths;

    /**
     * Set up test data - load files and images.
     */
    public PPMBuilderTest() {
      this.loadImagePaths();
      this.loadImageFiles();
    }

    /**
     * This method tests the builder by loading and writing an image using the builder.
     */
    @Test
    public void builderTest() {
      // create a builder
      PPMBuilder builder = new PPMBuilder();

      // load an image
      byte[] elephant = this.images.get("elephant");
      Image image = builder.loadImage(elephant);

      // write the image to an output stream
      byte[] imageData = null;
      try {
        imageData = builder.writeImage(image);
      } catch (Exception e) {
        fail("Exception thrown when writing image: " + e.getMessage());
      }

      // compare the image data to the original image data
      assertTrue(compareImageBytes(elephant, imageData));
      assertFalse(compareImageBytes(elephant, this.images.get("elephant-horizontal")));
    }

    /**
     * This is a helper method to load image paths for testing.
     */
    private void loadImagePaths() {
      // add image paths
      this.imagePaths = new HashMap<String, String>();
      this.imagePaths.put("elephant", "res/elephant.ppm");
      this.imagePaths.put("elephant-horizontal", "res/elephant-horizontal.ppm");
    }

    /**
     * This is a helper method to load imagefiles for testing.
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
}
