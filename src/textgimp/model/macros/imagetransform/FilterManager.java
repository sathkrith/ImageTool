package textgimp.model.macros.imagetransform;

import textgimp.model.macros.Macro;

/**
 * This interface represents presets for Filters. This interface is responsible for providing the
 * right macro for a given filter type.
 */
public interface FilterManager {

  /**
   * Returns a macro object for the filter type.
   *
   * @param type Filter type.
   * @return a macro.
   * @throws IllegalArgumentException if the type is not supported.
   */
  Macro getFilter(FilterType type) throws IllegalArgumentException;

  /**
   * Get the filter type for the string passed.
   *
   * @param filter filter string to get the type from.
   * @return corresponding filter type.
   */
  FilterType getFilterType(String filter);
}


