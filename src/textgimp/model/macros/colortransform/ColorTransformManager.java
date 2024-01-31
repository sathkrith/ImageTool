package textgimp.model.macros.colortransform;

import textgimp.model.macros.Macro;

/**
 * This interface represents presets for color transformation. This interface is responsible for
 * providing the right macro for a given color transform type.
 */
public interface ColorTransformManager {

  /**
   * Returns a macro object for the color transform type.
   *
   * @param type color transform type.
   * @return a macro.
   * @throws IllegalArgumentException if the type is not supported.
   */
  Macro getColorTransform(ColorTransformType type) throws IllegalArgumentException;

  /**
   * Get the color transform type for the string passed.
   *
   * @param transform transform string to get the type from.
   * @return corresponding ColorTransformType.
   * @throws IllegalArgumentException when string transform does not correspond to available
   *                                  transform types.
   */
  ColorTransformType getColorTransformType(String transform) throws IllegalArgumentException;
}
