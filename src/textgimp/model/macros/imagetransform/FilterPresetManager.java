package textgimp.model.macros.imagetransform;

import static java.util.Map.entry;

import java.util.Map;
import textgimp.model.macros.Macro;

/**
 * This class manages filter presets. Presets can be used to apply a predefined filter to an image.
 */
public class FilterPresetManager implements FilterManager {

  // A map of filter type and corresponding macro.
  private final Map<FilterType, Macro> presetMap;

  //A map of supported user command string and corresponding filter type.
  private final Map<String, FilterType> commandMap;

  /**
   * Creates a filter preset manager and initializes the preset macros.
   */
  public FilterPresetManager() {
    this.presetMap = generatePresetMap();
    this.commandMap = generateCommandMap();
  }

  /**
   * Generate a map of presets.
   *
   * @return map of presets.
   */
  private Map<FilterType, Macro> generatePresetMap() {
    return Map.ofEntries(
        entry(FilterType.BLUR, new FilterPresets.Blur()),
        entry(FilterType.SHARPEN, new FilterPresets.Sharpen())
    );
  }

  /**
   * Generate a command map for filters.
   *
   * @return a map of commands.
   */
  private Map<String, FilterType> generateCommandMap() {
    return Map.ofEntries(
        entry("blur", FilterType.BLUR),
        entry("sharpen", FilterType.SHARPEN)
    );
  }

  @Override
  public Macro getFilter(FilterType type)
      throws IllegalArgumentException {
    if (presetMap.containsKey(type)) {
      return presetMap.get(type);
    }
    throw new IllegalArgumentException("Provided type is currently not "
        + "supported by this manager.");
  }

  @Override
  public FilterType getFilterType(String filter)
      throws IllegalArgumentException {
    if (commandMap.containsKey(filter)) {
      return commandMap.get(filter);
    }
    throw new IllegalArgumentException("Provided type is currently not "
        + "supported by this manager.");
  }
}
