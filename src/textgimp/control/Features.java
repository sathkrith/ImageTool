package textgimp.control;

import textgimp.utility.Result;

/**
 * This interface represents a Features object.It contains callback methods that can be used by the
 * GUI to handle user input and perform image operations.
 */
public interface Features {


  /**
   * Load an image from the specified path and display it on the GUI.
   *
   * @param path path where image should be loaded from.
   */
  Result load(String path);

  /**
   * Save the currently loaded image at the specified path.
   *
   * @param path path where image should be saved.
   */
  Result save(String path);

  /**
   * Flip the currently loaded image and display it on the GUI.
   */
  Result horizontalFlip();

  /**
   * Flip an image vertically and display it on the GUI.
   */
  Result verticalFlip();

  /**
   * Split the currently loaded image into three images using the red, green, and blue channels and
   * store them in three image paths provided.
   *
   * @param redPath   path to red image file.
   * @param greenPath path to green image file.
   * @param bluePath  path to blue image file.
   */
  Result rgbSplit(String redPath, String greenPath, String bluePath);

  /**
   * Brighten the currently loaded image and display it on the GUI.
   *
   * @param amount amount by which to brighten. Negative values will darken the image.
   */
  Result brighten(int amount);

  /**
   * Combine three images into one using the red, green, and blue channels and display the result on
   * the GUI. The three images must be the same size.
   *
   * @param redPath   path to red image file.
   * @param greenPath path to green image file.
   * @param bluePath  path to blue image file.
   */
  Result rgbCombine(String redPath, String greenPath,
      String bluePath);

  /**
   * Obtain greyscale of the image using the specified component and display the result on the GUI.
   *
   * @param component component to create greyscale from.
   */
  Result greyscale(String component);

  /**
   * Apply a filter to an image and display the result on the GUI.
   *
   * @param filterType name of the filter to apply.
   */

  Result filter(String filterType);

  /**
   * Apply a transformation to an image and display the result on the GUI.
   *
   * @param transformType name of the transformation to apply.
   */
  Result transform(String transformType);

  /**
   * Apply a dithering effect to an image and display the result on the GUI.
   */
  Result dither();


}
