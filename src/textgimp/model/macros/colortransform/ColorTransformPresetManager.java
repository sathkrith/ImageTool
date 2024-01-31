package textgimp.model.macros.colortransform;

import static java.util.Map.entry;

import java.util.Map;
import textgimp.model.macros.Macro;

/**
 * This class manages color transform presets. Presets can be used to apply a predefined color
 * transformation to an image.
 */
public class ColorTransformPresetManager implements ColorTransformManager {

  /**
   * A map of color transform type and corresponding macro.
   */
  private final Map<ColorTransformType, Macro> presetMap;

  /**
   * A map of supported user command string and corresponding transform type.
   */
  private final Map<String, ColorTransformType> commandMap;

  /**
   * Create a ColorTransformPresetManager object and initialize the preset macros.
   */
  public ColorTransformPresetManager() {
    presetMap = generatePresetMap();
    commandMap = generateCommandMap();
  }

  /**
   * Generate a map of color transform type and corresponding macro.
   *
   * @return a map of color transform type and corresponding macro.
   */
  private Map<ColorTransformType, Macro> generatePresetMap() {
    return Map.ofEntries(
        entry(ColorTransformType.GREYSCALE_RED, new CTPresets.GreyscaleRed()),
        entry(ColorTransformType.GREYSCALE_GREEN, new CTPresets.GreyscaleGreen()),
        entry(ColorTransformType.GREYSCALE_BLUE, new CTPresets.GreyscaleBlue()),
        entry(ColorTransformType.GREYSCALE_INTENSITY, new CTPresets.GreyscaleIntensity()),
        entry(ColorTransformType.SEPIA, new CTPresets.Sepia()),
        entry(ColorTransformType.GREYSCALE_VALUE, new CTPresets.GreyScaleValue()),
        entry(ColorTransformType.GREYSCALE_LUMA, new CTPresets.GreyscaleLuma())
    );
  }

  /**
   * Generate a map of supported user command string and corresponding transform type.
   *
   * @return a map of supported user command string and corresponding transform type.
   */
  private Map<String, ColorTransformType> generateCommandMap() {
    return Map.ofEntries(
        entry("greyscale-red-component", ColorTransformType.GREYSCALE_RED),
        entry("greyscale-blue-component", ColorTransformType.GREYSCALE_BLUE),
        entry("greyscale-green-component", ColorTransformType.GREYSCALE_GREEN),
        entry("greyscale-luma-component", ColorTransformType.GREYSCALE_LUMA),
        entry("greyscale-value-component", ColorTransformType.GREYSCALE_VALUE),
        entry("greyscale-intensity-component", ColorTransformType.GREYSCALE_INTENSITY),
        entry("sepia", ColorTransformType.SEPIA)
    );
  }

  @Override
  public Macro getColorTransform(ColorTransformType type) throws IllegalArgumentException {
    if (presetMap.containsKey(type)) {
      return presetMap.get(type);
    }
    throw new IllegalArgumentException("Provided type is currently not supported by this manager.");
  }

  @Override
  public ColorTransformType getColorTransformType(String transform)
      throws IllegalArgumentException {
    if (commandMap.containsKey(transform)) {
      return commandMap.get(transform);
    }
    throw new IllegalArgumentException("Provided transform is currently not "
        + "supported by this manager.");
  }
}
