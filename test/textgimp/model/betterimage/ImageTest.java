package textgimp.model.betterimage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import textgimp.model.imagebuilder.ImageBuilder;
import textgimp.model.imagebuilder.PPMBuilder;
import textgimp.model.macros.Macro;
import textgimp.model.macros.colortransform.ColorTransformManager;
import textgimp.model.macros.colortransform.ColorTransformPresetManager;
import textgimp.model.macros.colortransform.ColorTransformType;
import textgimp.model.macros.imagetransform.HorizontalFlip;
import textgimp.model.macros.imagetransform.RGBCombine;
import textgimp.model.macros.imagetransform.VerticalFlip;

/**
 * This is a JUnit test class for the PPMImage class.
 */
public class ImageTest {

  private Map<String, Image> images;
  private Map<String, String> imagePaths;

  /**
   * Set up test data - load files and images.
   */
  public ImageTest() {
    this.loadImagePaths();
    this.loadImageFiles();
  }

  /**
   * This method tests the constructor of the PPMImage class.
   */
  @Test
  public void testConstructor() {
    // create a 10*10 image
    Pixel[][] pixels = new Pixel[10][10];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        pixels[i][j] = new RGBPixel(255, 255, 255, 255);
      }
    }

    // test constructor raises error when given null pixels
    assertThrows(IllegalArgumentException.class, () -> new GenericImage(null,
        255, "ppm"));

    // test constructor raises error when given empty pixels
    assertThrows(IllegalArgumentException.class,
        () -> new GenericImage(new Pixel[0][0], 255, "ppm"));

    // test constructor raises error when given negative max value
    assertThrows(IllegalArgumentException.class,
        () -> new GenericImage(pixels, -1, "ppm"));

    // test constructor with valid parameters
    GenericImage image = new GenericImage(pixels, 255, "ppm");

    // test getWidth() method
    assertEquals(10, image.getWidth());

    // test getHeight() method
    assertEquals(10, image.getHeight());

    // test getMaxValue() method
    assertEquals(255, image.getMaxValue());

    // test getPixel() method
    assertEquals(new RGBPixel(255, 255, 255, 255),
        image.getPixel(0, 0));
  }

  /**
   * This method tests the greyScale() method of the PPMImage class.
   */
  @Test
  public void testGreyScale() {
    // create elephant image
    Pixel[][] elephant = this.loadPixelFromImage("elephant-combine");
    Image elephantImage = new GenericImage(elephant, 255, "ppm");
    ColorTransformManager cm = new ColorTransformPresetManager();
    // test red-component
    Image redComponent =
        cm.getColorTransform(ColorTransformType.GREYSCALE_RED).apply(elephantImage);
    assertEquals(this.images.get("elephant-combine-red"), redComponent);

    // test green-component
    Image greenComponent =
        cm.getColorTransform(ColorTransformType.GREYSCALE_GREEN).apply(elephantImage);
    assertEquals(this.images.get("elephant-combine-green"), greenComponent);

    // test blue-component
    Image blueComponent =
        cm.getColorTransform(ColorTransformType.GREYSCALE_BLUE).apply(elephantImage);
    assertEquals(this.images.get("elephant-combine-blue"), blueComponent);

    // test value-component
    Image valueComponent =
        cm.getColorTransform(ColorTransformType.GREYSCALE_VALUE).apply(elephantImage);
    assertEquals(this.images.get("elephant-combine-value-greyscale"), valueComponent);

    // test intensity-component
    Image intensityComponent =
        cm.getColorTransform(ColorTransformType.GREYSCALE_INTENSITY).apply(elephantImage);
    assertEquals(this.images.get("elephant-combine-intensity-greyscale"), intensityComponent);

    // test luma-component
    Image lumaComponent =
        cm.getColorTransform(ColorTransformType.GREYSCALE_LUMA).apply(elephantImage);
    assertEquals(this.images.get("elephant-combine-luma-greyscale"), lumaComponent);
  }


  /**
   * This method tests the rgbcombine() method of the PPMImage class.
   */
  @Test
  public void testRGBCombine() {
    // create elephant image
    Pixel[][] elephant = this.loadPixelFromImage("elephant-combine");
    Image elephantImage = new GenericImage(elephant, 255, "ppm");
    Image red = this.images.get("elephant-combine-red");
    Image green = this.images.get("elephant-combine-green");
    Image blue = this.images.get("elephant-combine-blue");

    // test rgbcombine() method raises error when given null image objects
    assertThrows(IllegalArgumentException.class,
        () -> new RGBCombine(null, blue));
    assertThrows(IllegalArgumentException.class,
        () -> new RGBCombine(green, null));
    Macro mc = new RGBCombine(green, blue);
    assertThrows(IllegalArgumentException.class,
        () -> mc.apply(null));
    assertEquals(elephantImage, mc.apply(red));
  }

  /**
   * This method tests horiuzontal and vertical flip methods of the PPMImage class.
   */
  @Test
  public void testFlip() {
    // create elephant image
    Pixel[][] elephant = this.loadPixelFromImage("elephant");
    Image elephantImage = new GenericImage(elephant, 255, "ppm");

    // test horizontalFlip() method
    Macro mc = new HorizontalFlip();
    Image elephantHorizontal = mc.apply(elephantImage);
    assertEquals(this.images.get("elephant-horizontal"), elephantHorizontal);

    // test verticalFlip() method
    mc = new VerticalFlip();
    Image elephantVertical = mc.apply(elephantHorizontal);
    assertEquals(this.images.get("elephant-horizontal-vertical"), elephantVertical);
  }


  /**
   * This method tests the getHeight, getWidth, and getMaxValue methods of the PPMImage class.
   */
  @Test
  public void testUtility() {
    // create 10 x 10 pixels
    Pixel[][] pixels = new Pixel[10][10];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        pixels[i][j] = new RGBPixel(255, 255, 255, 255);
      }
    }

    // create image
    GenericImage image = new GenericImage(pixels, 255, "ppm");

    // test getHeight() method
    assertEquals(10, image.getHeight());

    // test getWidth() method
    assertEquals(10, image.getWidth());

    // test getMaxValue() method
    assertEquals(255, image.getMaxValue());

    // test getPixel() method
    assertEquals(new RGBPixel(255, 255, 255, 255), image.getPixel(0, 0));
  }

  /**
   * This is a helper method to load image paths for testing.
   */
  private void loadImagePaths() {
    // add image paths
    this.imagePaths = new HashMap<String, String>();
    this.imagePaths.put("elephant", "res/elephant.png");
    this.imagePaths.put("elephant-brighter", "res/elephant-brighter.jpg");
    this.imagePaths.put("elephant-darker", "res/elephant-darker.bmp");
    this.imagePaths.put("elephant-horizontal", "res/elephant-horizontal.ppm");
    this.imagePaths.put("elephant-horizontal-vertical", "res/elephant-horizontal-vertical.jpg");
    this.imagePaths.put("elephant-blue-tint", "res/elephant-blue-tint.jpg");
    this.imagePaths.put("elephant-combine", "res/elephant-combine.jpg");
    this.imagePaths.put("elephant-combine-red", "res/elephant-combine-red.jpg");
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
    this.images = new HashMap<String, Image>();
    ImageBuilder builder = new PPMBuilder();

    for (String name : this.imagePaths.keySet()) {
      String path = this.imagePaths.get(name);
      File f = new File(path);

      try {
        byte[] fileContent = Files.readAllBytes(f.toPath());
        Image image = builder.loadImage(fileContent);
        this.images.put(name, image);
      } catch (Exception e) {
        fail("Failed to load image file: " + path);
      }
    }
  }

  /**
   * This is a helper method to load pixels from an image.
   *
   * @param name the name of the image
   * @return the pixels of the image
   */
  private Pixel[][] loadPixelFromImage(String name) {
    Image image = this.images.get(name);
    int width = image.getWidth();
    int height = image.getHeight();

    Pixel[][] pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixels[i][j] = image.getPixel(i, j);
      }
    }
    return pixels;
  }

  /**
   * This method tests the constructor of the PNGPixel class.
   */
  @Test
  public void testConstructorPng() {
    // test constructor with valid values
    PNGPixel pixel = new PNGPixel(0, 0, 0, 0, 255);
    assertEquals(0, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(0, pixel.getBlue());
    assertEquals(0, pixel.getTransparency());

    // test that constructor raises error when values are negative or above maxValue
    assertThrows(IllegalArgumentException.class, () -> new PNGPixel(-1, 0, 0, 0, 255));
    assertThrows(IllegalArgumentException.class, () -> new PNGPixel(0, -1, 0, 0, 255));
    assertThrows(IllegalArgumentException.class, () -> new PNGPixel(0, 0, -1, 0, 10));
    assertThrows(IllegalArgumentException.class, () -> new PNGPixel(256, 0, 0, -1, 20));
    assertThrows(IllegalArgumentException.class, () -> new PNGPixel(0, 256, 0, 0, 30));
    assertThrows(IllegalArgumentException.class, () -> new PNGPixel(0, 0, 256, 0, 40));
    assertThrows(IllegalArgumentException.class, () -> new PNGPixel(0, 0, 256, 0, -50));
    assertThrows(IllegalArgumentException.class, () -> new PNGPixel(0, 0, 256, 0, -1));

    // fuzzy test
    for (int i = 0; i < 100; i++) {
      int red = (int) (Math.random() * 256);
      int green = (int) (Math.random() * 256);
      int blue = (int) (Math.random() * 256);
      int maxValue = (int) (Math.random() * 256);
      int transparency = (int) (Math.random() * 256);

      if (red > maxValue || green > maxValue || blue > maxValue || transparency > maxValue) {
        assertThrows(IllegalArgumentException.class,
            () -> new PNGPixel(red, green, blue, transparency, maxValue));
        continue;
      }
      pixel = new PNGPixel(red, green, blue, transparency, maxValue);
      assertEquals(red, pixel.getRed());
      assertEquals(green, pixel.getGreen());
      assertEquals(blue, pixel.getBlue());
      assertEquals(transparency, pixel.getTransparency());
    }
  }

  /**
   * This method tests the constructor of the RGBPixel class.
   */
  @Test
  public void testConstructorRGB() {
    // test constructor with valid values
    Pixel pixel = new RGBPixel(0, 0, 0, 255);
    assertEquals(0, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(0, pixel.getBlue());

    // test that constructor raises error when values are negative or above maxValue
    assertThrows(IllegalArgumentException.class, () -> new RGBPixel(-1, 0, 0, 255));
    assertThrows(IllegalArgumentException.class, () -> new RGBPixel(0, -1, 0, 255));
    assertThrows(IllegalArgumentException.class, () -> new RGBPixel(0, 0, -1, 10));
    assertThrows(IllegalArgumentException.class, () -> new RGBPixel(256, 0, 0, 20));
    assertThrows(IllegalArgumentException.class, () -> new RGBPixel(0, 256, 0, 30));
    assertThrows(IllegalArgumentException.class, () -> new RGBPixel(0, 0, 256, 40));
    assertThrows(IllegalArgumentException.class, () -> new RGBPixel(0, 0, 256, -50));
    assertThrows(IllegalArgumentException.class, () -> new RGBPixel(0, 0, 256, -1));

    // fuzzy test
    for (int i = 0; i < 100; i++) {
      int red = (int) (Math.random() * 256);
      int green = (int) (Math.random() * 256);
      int blue = (int) (Math.random() * 256);
      int maxValue = (int) (Math.random() * 256);

      if (red > maxValue || green > maxValue || blue > maxValue) {
        assertThrows(IllegalArgumentException.class,
            () -> new RGBPixel(red, green, blue, maxValue));
        continue;
      }
      pixel = new RGBPixel(red, green, blue, maxValue);
      assertEquals(red, pixel.getRed());
      assertEquals(green, pixel.getGreen());
      assertEquals(blue, pixel.getBlue());
    }
  }
}
