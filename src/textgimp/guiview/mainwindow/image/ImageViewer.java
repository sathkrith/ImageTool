package textgimp.guiview.mainwindow.image;

import java.io.IOException;

/**
 * This interface represents the image panel in the main window. Provides methods to display and
 * clear images.
 */
public interface ImageViewer {

  /**
   * Set the image shown to the user.
   *
   * @param image image to show.
   * @throws IOException when the image cannot be shown.
   */
  void setImage(byte[] image) throws IOException;

  /**
   * Clear the image shown to the user.
   */
  void clearImage();
}
