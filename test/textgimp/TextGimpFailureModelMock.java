package textgimp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import textgimp.model.Model;

/**
 * This class represents a failure mock model for the TextGimp program. The model throws Exceptions
 * when any of its methods are called.
 */
public class TextGimpFailureModelMock extends LoggerMockModel implements Model {

  @Override
  public void load(byte[] loadBytes, String name, String type) {
    String[] args = {"load", new String(loadBytes, StandardCharsets.UTF_8), name, type};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public byte[] save(String name, String type) {
    String[] args = {"save", name, type};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public void horizontalFlip(String sourceImageName, String newImageName) {
    String[] args = {"h-flip", sourceImageName, newImageName};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public void verticalFlip(String sourceImageName, String newImageName) {
    String[] args = {"v-flip", sourceImageName, newImageName};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public void rgbSplit(String sourceImageName, String redImageName, String greenImageName,
      String blueImageName) {
    String[] args = {"rgb-split", sourceImageName, redImageName, greenImageName, blueImageName};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public void brighten(String sourceImageName, int amount, String newImageName) {
    String[] args = {"brighten", sourceImageName, String.valueOf(amount), newImageName};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public void rgbCombine(String sourceRedImageName, String sourceGreenImageName,
      String sourceBlueImageName, String newImageName) {
    String[] args = {"rgb-combine", sourceRedImageName, sourceGreenImageName, sourceBlueImageName,
        newImageName};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public void greyscale(String sourceImageName, String component, String newImageName) {
    String[] args = {"greyscale", sourceImageName, component, newImageName};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public void filter(String sourceImageName, String filterType, String newImageName) {
    String[] args = {"filter", sourceImageName, filterType, newImageName};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public void transform(String sourceImageName, String transformType, String newImageName) {
    String[] args = {"transform", sourceImageName, transformType, newImageName};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public void dither(String sourceImageName, String newImageName) {
    String[] args = {"dither", sourceImageName, newImageName};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public int[] getHistogramOfGreyscale(String name, String component)
      throws IllegalArgumentException {
    String[] args = {"histogram", name, component};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }

  @Override
  public byte[] getBytesOfImage(String name) throws IllegalArgumentException, IOException {
    String[] args = {"bytes", name};
    this.addToLog(args);
    throw new IllegalArgumentException("Incorrect params");
  }
}