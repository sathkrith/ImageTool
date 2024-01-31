package textgimp.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import textgimp.model.betterimage.Image;
import textgimp.model.imagebuilder.BMPBuilder;
import textgimp.model.imagebuilder.ImageBuilder;
import textgimp.model.imagebuilder.JPGBuilder;
import textgimp.model.imagebuilder.PNGBuilder;
import textgimp.model.imagebuilder.PPMBuilder;
import textgimp.model.macros.Macro;
import textgimp.model.macros.colortransform.Brighten;
import textgimp.model.macros.colortransform.ColorTransformManager;
import textgimp.model.macros.colortransform.ColorTransformPresetManager;
import textgimp.model.macros.colortransform.ColorTransformType;
import textgimp.model.macros.imagetransform.Dither;
import textgimp.model.macros.imagetransform.FilterManager;
import textgimp.model.macros.imagetransform.FilterPresetManager;
import textgimp.model.macros.imagetransform.FilterType;
import textgimp.model.macros.imagetransform.HorizontalFlip;
import textgimp.model.macros.imagetransform.RGBCombine;
import textgimp.model.macros.imagetransform.VerticalFlip;

/**
 * This class represents a TextGimp application model. This model is responsible for storing and
 * manipulating images. This class is used by the controller to interact with the model.
 */
public class TextGimpModel implements Model {

  private final Map<String, Image> imageSet;
  private final Map<String, ImageBuilder> imageBuilders;
  private final ColorTransformManager colorTransformManager;

  private final FilterManager filterManager;

  /**
   * Create a new TextGimp model and initialize image builders and presets.
   */
  public TextGimpModel() {
    this.imageSet = new HashMap<>();
    this.colorTransformManager = new ColorTransformPresetManager();
    this.imageBuilders = this.loadImageBuilders();
    this.filterManager = new FilterPresetManager();
  }

  @Override
  public void load(byte[] input, String name, String type) throws IllegalArgumentException {
    // validate name and type
    this.validateName(name);
    this.validateType(type);

    // fetch the image builder for the given type
    ImageBuilder builder = this.fetchBuilder(type);

    // load and save the image
    Image image = builder.loadImage(input);
    this.imageSet.put(name, image);
  }

  @Override
  public byte[] save(String name, String type) throws IllegalArgumentException, IOException {
    // validate name and type
    this.validateName(name);
    this.validateType(type);

    // fetch the image
    Image image = this.fetchImage(name);

    // fetch the image builder for the given type
    ImageBuilder builder = this.fetchBuilder(type);
    return builder.writeImage(image);
  }

  @Override
  public void horizontalFlip(String sourceImageName, String newImageName)
      throws IllegalArgumentException {
    // validate name
    this.validateName(sourceImageName);
    this.validateName(newImageName);

    // fetch the image and perform the flip
    Image sourceImage = this.fetchImage(sourceImageName);

    // create macro and perform the flip
    Macro macro = new HorizontalFlip();
    Image newImage = macro.apply(sourceImage);
    this.imageSet.put(newImageName, newImage);
  }

  @Override
  public void verticalFlip(String sourceImageName, String newImageName)
      throws IllegalArgumentException {
    // validate name
    this.validateName(sourceImageName);
    this.validateName(newImageName);

    // fetch the image and perform the flip
    Image sourceImage = this.fetchImage(sourceImageName);

    // create macro and perform the flip
    Macro macro = new VerticalFlip();
    Image newImage = macro.apply(sourceImage);
    this.imageSet.put(newImageName, newImage);
  }

  @Override
  public void rgbSplit(String sourceImageName, String redImageName, String greenImageName,
      String blueImageName) throws IllegalArgumentException {
    // validate name
    this.validateName(sourceImageName);
    this.validateName(redImageName);
    this.validateName(greenImageName);
    this.validateName(blueImageName);

    // fetch the image and perform the split using greyscale macros
    Image sourceImage = this.fetchImage(sourceImageName);

    // obtain red-component using greyscale-red
    Macro macro = this.colorTransformManager.getColorTransform(ColorTransformType.GREYSCALE_RED);
    Image redImage = macro.apply(sourceImage);

    // obtain green-component using greyscale-green
    macro = this.colorTransformManager.getColorTransform(ColorTransformType.GREYSCALE_GREEN);
    Image greenImage = macro.apply(sourceImage);

    // obtain blue-component using greyscale-blue
    macro = this.colorTransformManager.getColorTransform(ColorTransformType.GREYSCALE_BLUE);
    Image blueImage = macro.apply(sourceImage);

    this.imageSet.put(redImageName, redImage);
    this.imageSet.put(greenImageName, greenImage);
    this.imageSet.put(blueImageName, blueImage);

  }

  @Override
  public void brighten(String sourceImageName, int amount, String newImageName)
      throws IllegalArgumentException {
    // validate name
    this.validateName(sourceImageName);
    this.validateName(newImageName);

    // fetch the image and perform the brighten
    Image sourceImage = this.fetchImage(sourceImageName);

    // create macro and perform the brighten
    Macro macro = new Brighten(amount);
    Image newImage = macro.apply(sourceImage);
    this.imageSet.put(newImageName, newImage);
  }

  @Override
  public void rgbCombine(String sourceRedImageName, String sourceGreenImageName,
      String sourceBlueImageName, String newImageName) throws IllegalArgumentException {
    // validate name
    this.validateName(sourceRedImageName);
    this.validateName(sourceGreenImageName);
    this.validateName(sourceBlueImageName);
    this.validateName(newImageName);

    // fetch the images and perform the combine
    Image redImage = this.fetchImage(sourceRedImageName);
    Image greenImage = this.fetchImage(sourceGreenImageName);
    Image blueImage = this.fetchImage(sourceBlueImageName);

    // create macro and perform the combine
    Macro macro = new RGBCombine(greenImage, blueImage);
    Image newImage = macro.apply(redImage);
    this.imageSet.put(newImageName, newImage);
  }


  @Override
  public void greyscale(String sourceImageName, String component, String newImageName)
      throws IllegalArgumentException {
    // validate name
    this.validateName(sourceImageName);
    this.validateName(newImageName);

    // fetch the image and perform the greyscale
    Image sourceImage = this.fetchImage(sourceImageName);

    // fetch the macro from presets
    component = "greyscale-" + component.toLowerCase();
    ColorTransformType type = this.colorTransformManager.getColorTransformType(component);

    // apply the macro
    Macro macro = this.colorTransformManager.getColorTransform(type);
    Image newImage = macro.apply(sourceImage);
    this.imageSet.put(newImageName, newImage);
  }

  @Override
  public void filter(String sourceImageName, String filterType, String newImageName)
      throws IllegalArgumentException {
    // validate name
    this.validateName(sourceImageName);
    this.validateName(newImageName);

    // fetch the image and perform the filter
    Image sourceImage = this.fetchImage(sourceImageName);

    // fetch the macro from presets
    filterType = filterType.toLowerCase();
    FilterType type = this.filterManager.getFilterType(filterType);
    Macro macro = this.filterManager.getFilter(type);

    // apply the macro
    Image newImage = macro.apply(sourceImage);
    this.imageSet.put(newImageName, newImage);
  }

  @Override
  public void transform(String sourceImageName, String transformType, String newImageName)
      throws IllegalArgumentException {
    // validate name
    this.validateName(sourceImageName);
    this.validateName(newImageName);

    // fetch the image and perform the transform
    Image sourceImage = this.fetchImage(sourceImageName);

    // fetch the macro from presets
    transformType = transformType.toLowerCase();
    ColorTransformType type = this.colorTransformManager.getColorTransformType(transformType);
    Macro macro = this.colorTransformManager.getColorTransform(type);

    // apply the macro
    Image newImage = macro.apply(sourceImage);
    this.imageSet.put(newImageName, newImage);
  }

  @Override
  public void dither(String sourceImageName, String newImageName) throws IllegalArgumentException {
    // validate name
    this.validateName(sourceImageName);
    this.validateName(newImageName);

    // fetch the image and perform the dithering
    Image sourceImage = this.fetchImage(sourceImageName);

    // create macro and perform the dithering
    Macro macro = new Dither();
    Image newImage = macro.apply(sourceImage);
    this.imageSet.put(newImageName, newImage);
  }

  /**
   * Generate a map of image builders.
   *
   * @return a map of image builders
   */
  private Map<String, ImageBuilder> loadImageBuilders() {
    Map<String, ImageBuilder> imageBuilders = new HashMap<>();
    imageBuilders.put("ppm", new PPMBuilder());
    imageBuilders.put("png", new PNGBuilder());
    imageBuilders.put("jpg", new JPGBuilder());
    imageBuilders.put("bmp", new BMPBuilder());
    return imageBuilders;
  }

  /**
   * Fetch the image builder for the given type.
   *
   * @param type the type of image builder to fetch
   * @return the image builder
   * @throws IllegalArgumentException if the type is not supported
   */
  private ImageBuilder fetchBuilder(String type) throws IllegalArgumentException {
    ImageBuilder builder = this.imageBuilders.getOrDefault(type, null);
    if (builder == null) {
      throw new IllegalArgumentException("Unsupported image format: " + type);
    }
    return builder;
  }

  /**
   * Fetch the image with the given name.
   *
   * @param name the name of the image to fetch
   * @return the RGB image object
   * @throws IllegalArgumentException if the image is not found
   */
  private Image fetchImage(String name) throws IllegalArgumentException {
    Image image = this.imageSet.getOrDefault(name, null);
    if (image == null) {
      throw new IllegalArgumentException("Image not found: " + name);
    }
    return image;
  }

  /**
   * Validate the image name. Check if it is null, empty, or blank.
   *
   * @param name the name of the image
   * @throws IllegalArgumentException if the name is invalid
   */
  private void validateName(String name) throws IllegalArgumentException {
    if (name == null || name.isEmpty() || name.isBlank()) {
      throw new IllegalArgumentException("Image name cannot be empty");
    }
  }

  /**
   * Validate the image type. Check if it is null, empty, or blank.
   *
   * @param type the type of the image
   * @throws IllegalArgumentException if the type is invalid
   */
  private void validateType(String type) throws IllegalArgumentException {
    if (type == null || type.isEmpty() || type.isBlank()) {
      throw new IllegalArgumentException("Image type cannot be empty");
    }
  }

  @Override
  public int[] getHistogramOfGreyscale(String name, String component)
      throws IllegalArgumentException {
    // transform the image to the given component.
    ColorTransformType cType = colorTransformManager.getColorTransformType(component);
    Macro transformer = colorTransformManager.getColorTransform(cType);
    Image img = fetchImage(name);
    Image transformedImage = transformer.apply(img);
    int[] histogram = new int[transformedImage.getMaxValue() + 1];

    // generate histogram array
    for (int i = 0; i < transformedImage.getHeight(); i++) {
      for (int j = 0; j < transformedImage.getWidth(); j++) {
        histogram[transformedImage.getPixel(i, j).getRed()]++;
      }
    }
    return histogram;
  }

  @Override
  public byte[] getBytesOfImage(String name) throws IllegalArgumentException, IOException {
    // validate name
    this.validateName(name);

    // fetch the image
    Image image = this.fetchImage(name);

    // fetch the image builder for the given type
    ImageBuilder builder = this.fetchBuilder(image.getImageType());
    return builder.writeImage(image);
  }
}
