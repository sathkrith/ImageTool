package textgimp.model;

import java.io.IOException;

/**
 * This interface represents a read only version of Model. This provides restricted access to the
 * model allowing data read. Used for the GUI to read data from the model.
 */
public interface ReadOnlyModel {

  /**
   * return a histogram of a grey-scaled image. A histogram of an image is an array where the value
   * at each index is the count of pixels with index as its value.
   *
   * @param name      name of the image loaded.
   * @param component greyscale component.
   * @throws IllegalArgumentException if no image of "name" exists.
   */
  int[] getHistogramOfGreyscale(String name, String component) throws IllegalArgumentException;

  /**
   * Given an image name, return the byte array representation of the image. The returned byte array
   * is used to display the image in the GUI.
   *
   * @param name name of the image being saved.
   * @return byte array representation of the image.
   * @throws IllegalArgumentException if the image does not exist.
   */
  byte[] getBytesOfImage(String name) throws IllegalArgumentException, IOException;
}
