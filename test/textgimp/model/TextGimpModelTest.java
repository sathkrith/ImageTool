package textgimp.model;

import static textgimp.ModelMocks.createBufferedImage;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.junit.Assert;
import org.junit.Test;
import textgimp.model.betterimage.Pixel;
import textgimp.model.betterimage.RGBPixel;

/**
 * This is a JUnit test class to test the TextGimpModel.
 */
public class TextGimpModelTest {

  private Map<String, byte[]> images;
  private Map<String, String> imagePaths;

  /**
   * Set up test data - load files and images.
   */
  public TextGimpModelTest() {
    this.loadImagePaths();
    this.loadImageFiles();
  }

  /**
   * This test method tests the constructor.
   */
  @Test
  public void testConstructor() {
    // create model
    Model model = new TextGimpModel();
    Assert.assertNotNull(model);

    // check that the image set is empty
    assertThrows(IllegalArgumentException.class,
        () -> model.verticalFlip("elephant", "elephant-vertical"));

    // check that the image builders are loaded
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");
    byte[] response = null;

    try {
      response = model.save("elephant", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(compareImageBytes(elephantBytes, response));
  }

  /**
   * This test method tests the load method of the model by loading different image types.
   */
  @Test
  public void testLoad() {
    // create model
    Model model = new TextGimpModel();

    // try loading with null input
    assertThrows(IllegalArgumentException.class, () -> model.load(null, "elephant", "ppm"));

    // try loading with null name
    byte[] elephantBytes = this.images.get("elephant");
    assertThrows(IllegalArgumentException.class, () -> model.load(elephantBytes, null, "ppm"));

    // try loading with null type
    assertThrows(IllegalArgumentException.class, () -> model.load(elephantBytes, "elephant", null));

    // try loading with invalid type
    assertThrows(IllegalArgumentException.class,
        () -> model.load(elephantBytes, "elephant", "jpg"));

    // load image and check if the right data has been loaded
    model.load(elephantBytes, "elephant", "ppm");

    byte[] response = null;
    try {
      response = model.save("elephant", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(compareImageBytes(elephantBytes, response));
  }

  /**
   * This test method tests the save method of the model by saving different image types.
   */
  @Test
  public void testSave() {
    // create model
    Model model = new TextGimpModel();

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try saving with null name
    assertThrows(IllegalArgumentException.class, () -> model.save(null, "ppm"));

    // try saving with null type
    assertThrows(IllegalArgumentException.class, () -> model.save("elephant", null));

    // try saving with invalid type
    assertThrows(IllegalArgumentException.class, () -> model.save("elephant", "jpg"));

    // try saving with empty name
    assertThrows(IllegalArgumentException.class, () -> model.save(" ", "ppm"));

    // save image and check if the right data is being saved
    byte[] response = null;
    try {
      response = model.save("elephant", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(compareImageBytes(elephantBytes, response));
  }

  /**
   * This test method tests the horizontal flip method of the model.
   */
  @Test
  public void testHorizontalFlip() {
    // create model
    Model model = new TextGimpModel();

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try flipping with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.horizontalFlip(null, "elephant-horizontal"));

    // try flipping with null new name
    assertThrows(IllegalArgumentException.class, () -> model.horizontalFlip("elephant", null));

    // try flipping with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.horizontalFlip(" ", "elephant-horizontal"));

    // try flipping with empty new name
    assertThrows(IllegalArgumentException.class, () -> model.horizontalFlip("elephant", " "));

    // try flipping with non-existent image
    assertThrows(IllegalArgumentException.class,
        () -> model.horizontalFlip("elephant2", "elephant-horizontal"));

    // flip image and check if the right data is being saved
    model.horizontalFlip("elephant", "elephant-horizontal");
    byte[] response = null;
    try {
      response = model.save("elephant-horizontal", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    byte[] expected = this.images.get("elephant-horizontal");
    Assert.assertTrue(compareImageBytes(expected, response));
    Assert.assertFalse(compareImageBytes(elephantBytes, response));
  }

  /**
   * This test method tests the vertical flip method of the model.
   */
  @Test
  public void testVerticalFlip() {
    // create model
    Model model = new TextGimpModel();

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try flipping with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.verticalFlip(null, "elephant-vertical"));

    // try flipping with null new name
    assertThrows(IllegalArgumentException.class, () -> model.verticalFlip("elephant", null));

    // try flipping with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.verticalFlip(" ", "elephant-vertical"));

    // try flipping with empty new name
    assertThrows(IllegalArgumentException.class, () -> model.verticalFlip("elephant", " "));

    // try flipping with non-existent image
    assertThrows(IllegalArgumentException.class,
        () -> model.verticalFlip("elephant2", "elephant-vertical"));

    // flip image and check if the right data is being saved
    model.horizontalFlip("elephant", "elephant-horizontal");
    model.verticalFlip("elephant-horizontal", "elephant-horizontal-vertical");
    byte[] response = null;
    try {
      response = model.save("elephant-horizontal-vertical", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    byte[] expected = this.images.get("elephant-horizontal-vertical");
    Assert.assertTrue(compareImageBytes(expected, response));
    Assert.assertFalse(compareImageBytes(expected, elephantBytes));
  }

  /**
   * This test method tests the rgb split method of the model.
   */
  @Test
  public void testRgbSplit() {
    // create model
    final Model model = new TextGimpModel();

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try splitting with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbSplit(null, "elephant-red", "elephant-green",
            "elephant-blue"));

    // try splitting with null red name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbSplit("elephant", null, "elephant-green",
            "elephant-blue"));

    // try splitting with null green name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbSplit("elephant", "elephant-red", null,
            "elephant-blue"));

    // try splitting with null blue name
    assertThrows(IllegalArgumentException.class, () -> model.rgbSplit("elephant", "elephant-red",
        "elephant-green", null));

    // try splitting with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbSplit(" ", "elephant-red", "elephant-green",
            "elephant-blue"));

    // try splitting with empty red name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbSplit("elephant", " ", "elephant-green",
            "elephant-blue"));

    // try splitting with empty green name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbSplit("elephant", "elephant-red", " ",
            "elephant-blue"));

    // try splitting with empty blue name
    assertThrows(IllegalArgumentException.class, () -> model.rgbSplit("elephant", "elephant-red",
        "elephant-green", " "));

    // try splitting with non-existent image
    assertThrows(IllegalArgumentException.class, () -> model.rgbSplit("elephant2", "elephant-red",
        "elephant-green", "elephant-blue"));

    // split image and check if the right data is being saved
    Model newModel = this.loadElephantCombined();
    newModel.rgbSplit("elephant-combine", "elephant-combine-red", "elephant-combine-green",
        "elephant-combine-blue");

    byte[] redResponse = null;
    byte[] greenResponse = null;
    byte[] blueResponse = null;

    try {
      redResponse = newModel.save("elephant-combine-red", "ppm");
      greenResponse = newModel.save("elephant-combine-green", "ppm");
      blueResponse = newModel.save("elephant-combine-blue", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(compareImageBytes(this.images.get("elephant-combine-red"), redResponse));
    Assert.assertFalse(compareImageBytes(this.images.get("elephant-combine-green"), redResponse));
    Assert.assertTrue(compareImageBytes(this.images.get("elephant-combine-green"), greenResponse));
    Assert.assertTrue(compareImageBytes(this.images.get("elephant-combine-blue"), blueResponse));
  }

  /**
   * This test method tests the brighten method of the model.
   */
  @Test
  public void testBrighten() {
    // create model
    final Model model = new TextGimpModel();

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try brightening with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.brighten(null, 10, "elephant-brighter"));

    // try brightening with null new name
    assertThrows(IllegalArgumentException.class, () -> model.brighten("elephant", 10, null));

    // try brightening with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.brighten(" ", 10, "elephant-brighter"));

    // try brightening with empty new name
    assertThrows(IllegalArgumentException.class, () -> model.brighten("elephant", 10, " "));

    // try brightening with non-existent image
    assertThrows(IllegalArgumentException.class,
        () -> model.brighten("elephant2", 10, "elephant-brighter"));

    // brighten image and check if the right data is being saved
    model.brighten("elephant", 70, "elephant-brighter");

    // darken the image and check if the right data is being saved
    model.brighten("elephant", -70, "elephant-darker");
    model.brighten("elephant-brighter", -70, "elephant-normal");

    byte[] brighterImg = null;
    byte[] normalImg = null;
    byte[] darkerImg = null;

    try {
      brighterImg = model.save("elephant-brighter", "ppm");
      normalImg = model.save("elephant-normal", "ppm");
      darkerImg = model.save("elephant-darker", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(compareImageBytes(this.images.get("elephant-brighter"), brighterImg));
    Assert.assertFalse(compareImageBytes(this.images.get("elephant"), normalImg));
    Assert.assertTrue(compareImageBytes(this.images.get("elephant-darker"), darkerImg));
  }

  /**
   * This test method tests the RGB combine method of the model.
   */
  @Test
  public void testRgbCombine() {
    // create model
    final Model model = new TextGimpModel();

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try combining with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbCombine(null, "elephant-red", "elephant-green",
            "elephant-blue"));

    // try combining with null red name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbCombine("elephant", null, "elephant-green",
            "elephant-blue"));

    // try combining with null green name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbCombine("elephant", "elephant-red", null,
            "elephant-blue"));

    // try combining with null blue name
    assertThrows(IllegalArgumentException.class, () -> model.rgbCombine("elephant", "elephant-red",
        "elephant-green", null));

    // try combining with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbCombine(" ", "elephant-red", "elephant-green",
            "elephant-blue"));

    // try combining with empty red name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbCombine("elephant", " ", "elephant-green",
            "elephant-blue"));

    // try combining with empty green name
    assertThrows(IllegalArgumentException.class,
        () -> model.rgbCombine("elephant", "elephant-red", " ",
            "elephant-blue"));

    // try combining with empty blue name
    assertThrows(IllegalArgumentException.class, () -> model.rgbCombine("elephant", "elephant-red",
        "elephant-green", " "));

    // try combining with non-existent image
    assertThrows(IllegalArgumentException.class, () -> model.rgbCombine("elephant2", "elephant-red",
        "elephant-green", "elephant-blue"));

    // combine image and check if the right data is being saved
    model.horizontalFlip("elephant", "elephant-horizontal");
    model.verticalFlip("elephant-horizontal", "elephant-horizontal-vertical");
    model.rgbCombine("elephant", "elephant-horizontal", "elephant-horizontal-vertical",
        "elephant-combine");

    byte[] combineResponse = null;
    try {
      combineResponse = model.save("elephant-combine", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(compareImageBytes(this.images.get("elephant-combine"), combineResponse));
    Assert.assertFalse(compareImageBytes(this.images.get("elephant"), combineResponse));
  }

  /**
   * This test method tests the greyscale method of the model with the red component.
   */
  @Test
  public void testRedGreyscale() {
    // create model
    final Model model = new TextGimpModel();
    String component = "red-component";

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try greyscaling with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(null, component, "elephant-red"));

    // try greyscaling with null new name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", component, null));

    // try greyscaling with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(" ", component, "elephant-red"));

    // try greyscaling with empty new name
    assertThrows(IllegalArgumentException.class, () -> model.greyscale("elephant", component, " "));

    // try greyscaling with non-existent image
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant2", component, "elephant-red"));

    // try invalid component
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", "invalid-component", "elephant-red"));

    // greyscale image and check if the right data is being saved
    Model newModel = this.loadElephantCombined();
    newModel.greyscale("elephant-combine", component, "elephant-combine-red-greyscale");

    byte[] redResponse = null;
    try {
      redResponse = newModel.save("elephant-combine-red-greyscale", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(
        compareImageBytes(this.images.get("elephant-combine-red-greyscale"), redResponse));
    Assert.assertFalse(compareImageBytes(this.images.get("elephant"), redResponse));
  }

  /**
   * This test method tests the greyscale method of the model with the blue component.
   */
  @Test
  public void testBlueGreyscale() {
    // create model
    final Model model = new TextGimpModel();
    String component = "blue-component";

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try greyscaling with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(null, component, "elephant-blue"));

    // try greyscaling with null new name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", component, null));

    // try greyscaling with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(" ", component, "elephant-blue"));

    // try greyscaling with empty new name
    assertThrows(IllegalArgumentException.class, () -> model.greyscale("elephant", component, " "));

    // try greyscaling with non-existent image
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant2", component, "elephant-blue"));

    // try invalid component
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", "invalid-component", "elephant-blue"));

    // greyscale image and check if the right data is being saved
    Model newModel = this.loadElephantCombined();
    newModel.greyscale("elephant-combine", component, "elephant-combine-blue-greyscale");

    byte[] blueResponse = null;
    try {
      blueResponse = newModel.save("elephant-combine-blue-greyscale", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(
        compareImageBytes(this.images.get("elephant-combine-blue-greyscale"), blueResponse));
    Assert.assertFalse(compareImageBytes(this.images.get("elephant"), blueResponse));
  }

  /**
   * This test method tests the greyscale method of the model with the green component.
   */
  @Test
  public void testGreenGreyscale() {
    // create model
    final Model model = new TextGimpModel();
    String component = "green-component";

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try greyscaling with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(null, component, "elephant-green"));

    // try greyscaling with null new name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", component, null));

    // try greyscaling with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(" ", component, "elephant-green"));

    // try greyscaling with empty new name
    assertThrows(IllegalArgumentException.class, () -> model.greyscale("elephant", component, " "));

    // try greyscaling with non-existent image
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant2", component, "elephant-green"));

    // try invalid component
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", "invalid-component", "elephant-green"));

    // greyscale image and check if the right data is being saved
    Model newModel = this.loadElephantCombined();
    newModel.greyscale("elephant-combine", component, "elephant-combine-green-greyscale");

    byte[] greenResponse = null;
    try {
      greenResponse = newModel.save("elephant-combine-green-greyscale", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(
        compareImageBytes(this.images.get("elephant-combine-green-greyscale"), greenResponse));
    Assert.assertFalse(compareImageBytes(this.images.get("elephant"), greenResponse));
  }

  /**
   * This test method tests the greyscale method of the model with luma-component.
   */
  @Test
  public void testLumaGreyscale() {
    // create model
    final Model model = new TextGimpModel();
    String component = "luma-component";

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try greyscaling with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(null, component, "elephant-luma"));

    // try greyscaling with null new name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", component, null));

    // try greyscaling with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(" ", component, "elephant-luma"));

    // try greyscaling with empty new name
    assertThrows(IllegalArgumentException.class, () -> model.greyscale("elephant", component, " "));

    // try greyscaling with non-existent image
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant2", component, "elephant-luma"));

    // try invalid component
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", "invalid-component", "elephant-luma"));

    // greyscale image and check if the right data is being saved
    Model newModel = this.loadElephantCombined();
    newModel.greyscale("elephant-combine", component, "elephant-combine-luma-greyscale");

    byte[] lumaResponse = null;
    try {
      lumaResponse = newModel.save("elephant-combine-luma-greyscale", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(
        compareImageBytes(this.images.get("elephant-combine-luma-greyscale"), lumaResponse));
    Assert.assertFalse(compareImageBytes(this.images.get("elephant"), lumaResponse));
  }

  /**
   * This test method tests the greyscale method of the model with the value-component.
   */
  @Test
  public void testValueGreyscale() {
    // create model
    final Model model = new TextGimpModel();
    String component = "value-component";

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try greyscaling with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(null, component, "elephant-value"));

    // try greyscaling with null new name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", component, null));

    // try greyscaling with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(" ", component, "elephant-value"));

    // try greyscaling with empty new name
    assertThrows(IllegalArgumentException.class, () -> model.greyscale("elephant", component, " "));

    // try greyscaling with non-existent image
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant2", component, "elephant-value"));

    // try invalid component
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", "invalid-component", "elephant-value"));

    // greyscale image and check if the right data is being saved
    Model newModel = this.loadElephantCombined();
    newModel.greyscale("elephant-combine", component, "elephant-combine-value-greyscale");

    byte[] valueResponse = null;
    try {
      valueResponse = newModel.save("elephant-combine-value-greyscale", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(
        compareImageBytes(this.images.get("elephant-combine-value-greyscale"), valueResponse));
    Assert.assertFalse(compareImageBytes(this.images.get("elephant"), valueResponse));
  }

  /**
   * This test method tests the greyscale method of the model with the intensity-component.
   */
  @Test
  public void testIntensityGreyscale() {
    // create model
    final Model model = new TextGimpModel();
    String component = "intensity-component";

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // try greyscaling with null name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(null, component, "elephant-intensity"));

    // try greyscaling with null new name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", component, null));

    // try greyscaling with empty name
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale(" ", component, "elephant-intensity"));

    // try greyscaling with empty new name
    assertThrows(IllegalArgumentException.class, () -> model.greyscale("elephant", component, " "));

    // try greyscaling with non-existent image
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant2", component, "elephant-intensity"));

    // try invalid component
    assertThrows(IllegalArgumentException.class,
        () -> model.greyscale("elephant", "invalid-component", "elephant-intensity"));

    // greyscale image and check if the right data is being saved
    Model newModel = this.loadElephantCombined();
    newModel.greyscale("elephant-combine", component, "elephant-combine-intensity-greyscale");

    byte[] intensityResponse = null;
    try {
      intensityResponse = newModel.save("elephant-combine-intensity-greyscale", "ppm");
    } catch (IOException e) {
      Assert.fail("IOException thrown when saving image");
    }
    Assert.assertTrue(compareImageBytes(this.images.get("elephant-combine-intensity-greyscale"),
        intensityResponse));
    Assert.assertFalse(compareImageBytes(this.images.get("elephant"), intensityResponse));
  }

  /**
   * This is a helper method to load image paths for testing.
   */
  private void loadImagePaths() {
    // add image paths
    this.imagePaths = new HashMap<String, String>();
    this.imagePaths.put("elephant", "res/elephant.ppm");
    this.imagePaths.put("elephant-brighter", "res/elephant-brighter.ppm");
    this.imagePaths.put("elephant-darker", "res/elephant-darker.ppm");
    this.imagePaths.put("elephant-horizontal", "res/elephant-horizontal.ppm");
    this.imagePaths.put("elephant-horizontal-vertical", "res/elephant-horizontal-vertical.ppm");
    this.imagePaths.put("elephant-blue-tint", "res/elephant-blue-tint.ppm");
    this.imagePaths.put("elephant-combine", "res/elephant-combine.ppm");
    this.imagePaths.put("elephant-combine-red", "res/elephant-combine-red.ppm");
    this.imagePaths.put("elephant-combine-blue", "res/elephant-combine-blue.ppm");
    this.imagePaths.put("elephant-combine-green", "res/elephant-combine-green.ppm");
    this.imagePaths.put("elephant-combine-red-greyscale", "res/elephant-combine-red-greyscale.ppm");
    this.imagePaths.put("elephant-combine-green-greyscale",
        "res/elephant-combine-green-greyscale.ppm");
    this.imagePaths.put("elephant-combine-blue-greyscale",
        "res/elephant-combine-blue-greyscale.ppm");
    this.imagePaths.put("elephant-combine-value-greyscale",
        "res/elephant-combine-value-greyscale.ppm");
    this.imagePaths.put("elephant-combine-luma-greyscale",
        "res/elephant-combine-luma-greyscale.ppm");
    this.imagePaths.put("elephant-combine-intensity-greyscale",
        "res/elephant-combine-intensity-greyscale.ppm");
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
        System.out.println("Failed to load image file: " + path);
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

  /**
   * This is a helper method to load an image and perform pre-defined operations on it.
   *
   * @return the model with the image loaded and operations performed
   */
  private Model loadElephantCombined() {
    // create model
    TextGimpModel model = new TextGimpModel();

    // load an image
    byte[] elephantBytes = this.images.get("elephant");
    model.load(elephantBytes, "elephant", "ppm");

    // perform operations
    model.horizontalFlip("elephant", "elephant-horizontal");
    model.verticalFlip("elephant-horizontal", "elephant-horizontal-vertical");
    model.rgbCombine("elephant", "elephant-horizontal", "elephant-horizontal-vertical",
        "elephant-combine");
    return model;
  }

  @Test
  public void getHistogramOfGreyscale() throws IOException {
    Pixel px = new RGBPixel(1, 1, 1, 255);
    Model model = new TextGimpModel();
    BufferedImage img = createBufferedImage(3, 3, px);
    ByteArrayOutputStream bs = new ByteArrayOutputStream();
    ImageIO.write(img, "jpg", bs);
    byte[] bytes = bs.toByteArray();
    model.load(bytes, "target", "jpg");
    int[] expectedArray = new int[255];
    expectedArray[1] = 9;
    assertArrayEquals(expectedArray,
        model.getHistogramOfGreyscale("target", "greyscale-red-component"));
  }

  @Test
  public void getBytesOfImage() throws IOException {
    Pixel px = new RGBPixel(1, 1, 1, 255);
    Model model = new TextGimpModel();
    BufferedImage img = createBufferedImage(3, 3, px);
    ByteArrayOutputStream bs = new ByteArrayOutputStream();
    ImageIO.write(img, "jpg", bs);
    byte[] bytes = bs.toByteArray();
    model.load(bytes, "target", "jpg");
    assertArrayEquals(bytes,
        model.getBytesOfImage("target"));
  }
}