package textgimp.model.imagebuilder;

import java.io.IOException;
import textgimp.model.betterimage.Image;

/**
 * This interface represents an image builder. This behaves as an adapter between TextGimp Image
 * object and the generic image file format.
 */
public interface ImageBuilder {

  /**
   * Given a byte array representing an image, this method will decode the data and return the
   * corresponding Image object.
   *
   * @param loadData data to load from byte array
   * @return the image object
   * @throws IllegalArgumentException if the input stream is invalid
   */
  Image loadImage(byte[] loadData) throws IllegalArgumentException;

  /**
   * Given an Image object, this method will encode the image and return the corresponding byte
   * array.
   *
   * @param image the image to write
   * @return byte array representation of the image
   * @throws IllegalArgumentException if the image is invalid or output stream is invalid
   */
  byte[] writeImage(Image image) throws IllegalArgumentException, IOException;
}