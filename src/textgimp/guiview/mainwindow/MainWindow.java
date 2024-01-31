package textgimp.guiview.mainwindow;

/**
 * This interface represents the main window of GUI Swing gimp.
 */
public interface MainWindow {

  /**
   * Display the image to the user.
   *
   * @param displayImage image to display;
   */
  void displayImage(String displayImage);

  /**
   * Clear the image and histogram from the view.
   */
  void clearImage();
}
