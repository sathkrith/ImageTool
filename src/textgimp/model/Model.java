package textgimp.model;

import java.io.IOException;

/**
 * This interface represents a model for the TextGimp program. Defines the available operations that
 * can be performed on images. This interface is used by the controller to interact with the model.
 */
public interface Model extends ReadOnlyModel {

  /**
   * Load an image from the byte array. The image is stored in the model with the given name.
   *
   * @param input input byte array to load the image from.
   * @param name  name of the image loaded.
   * @param type  type of the image file.
   * @throws IllegalArgumentException if the stream is not a valid PPM image.
   */
  void load(byte[] input, String name, String type) throws IllegalArgumentException;

  /**
   * Given an image name, return the byte array representation of the image. The returned byte array
   * can be stored in a file to save the image.
   *
   * @param name name of the image being saved.
   * @param type type of the image file.
   * @return byte array representation of the image.
   * @throws IllegalArgumentException if the image does not exist or the stream is null.
   * @throws IOException              when input or output during save fails.
   */
  byte[] save(String name, String type) throws IllegalArgumentException,
      IOException;

  /**
   * Flip an image horizontally and store the result in a new image.
   *
   * @param sourceImageName name of the image to flip.
   * @param newImageName    name of the flipped image.
   * @throws IllegalArgumentException if the image does not exist.
   */
  void horizontalFlip(String sourceImageName, String newImageName) throws IllegalArgumentException;

  /**
   * Flip an image vertically and store the result in a new image.
   *
   * @param sourceImageName name of the image to flip.
   * @param newImageName    name of the flipped image.
   * @throws IllegalArgumentException if the image does not exist.
   */
  void verticalFlip(String sourceImageName, String newImageName) throws IllegalArgumentException;

  /**
   * Split an image by channel into 3 greyscale images and store the result in 3 new images.
   *
   * @param sourceImageName name of the image to split.
   * @param redImageName    name of the image with red greyscale values.
   * @param greenImageName  name of the image with green greyscale values.
   * @param blueImageName   name of the image with blue greyscale values.
   * @throws IllegalArgumentException if the image does not exist.
   */
  void rgbSplit(String sourceImageName,
      String redImageName,
      String greenImageName,
      String blueImageName) throws IllegalArgumentException;

  /**
   * Brighten an image by a specified amount and store the result in a new image.
   *
   * @param sourceImageName name of the image to brighten.
   * @param amount          amount by which to brighten.
   * @param newImageName    name of the brightened image.
   * @throws IllegalArgumentException if the image does not exist.
   */
  void brighten(String sourceImageName, int amount, String newImageName)
      throws IllegalArgumentException;

  /**
   * Combine three images into one using the red, green, and blue channels and store the result in a
   * new image.
   *
   * @param sourceRedImageName   name of the image to pick red channel values.
   * @param sourceGreenImageName name of the image to pick green channel values.
   * @param sourceBlueImageName  name of the image to pick blue channel values.
   * @param newImageName         name of the combined image.
   * @throws IllegalArgumentException if any of the images does not exist or if the images are not
   *                                  the same size.
   */
  void rgbCombine(String sourceRedImageName, String sourceGreenImageName,
      String sourceBlueImageName, String newImageName) throws IllegalArgumentException;

  /**
   * Obtain greyscale of the image using the specified component and store the result in a new
   * image.
   *
   * @param sourceImageName name of the image to get greyscale from.
   * @param component       component to create greyscale from.
   * @param newImageName    name of the new greyscale image.
   * @throws IllegalArgumentException if the image does not exist or the component is invalid.
   */
  void greyscale(String sourceImageName, String component, String newImageName)
      throws IllegalArgumentException;

  /**
   * Apply a filter to an image and store the result in a new image.
   *
   * @param sourceImageName name of the image to apply the filter to.
   * @param filterType      name of the filter to apply.
   * @param newImageName    name of the new image with the filter applied.
   * @throws IllegalArgumentException if the image does not exist or the filter does not exist.
   */

  void filter(String sourceImageName, String filterType, String newImageName)
      throws IllegalArgumentException;

  /**
   * Apply a transformation to an image.
   *
   * @param sourceImageName name of the image to apply the transformation to.
   * @param transformType   name of the transformation to apply.
   * @param newImageName    name of the new image with the transformation applied.
   * @throws IllegalArgumentException if the image does not exist or the transformation does not
   *                                  exist.
   */
  void transform(String sourceImageName, String transformType, String newImageName)
      throws IllegalArgumentException;

  /**
   * Apply a dithering effect to an image and store the result in a new image.
   *
   * @param sourceImageName name of the image to apply the dithering effect to.
   * @param newImageName    name of the new image with the dithering effect applied.
   * @throws IllegalArgumentException if the image does not exist.
   */
  void dither(String sourceImageName, String newImageName) throws IllegalArgumentException;
}
