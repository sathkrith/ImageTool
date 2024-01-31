package textgimp.guiview;

import textgimp.control.Features;

/**
 * This interface represents a GUI view for the application.
 */
public interface GUIView {

  /**
   * Show the GUI of the application.
   */
  void showGUI();

  /**
   * Add a feature handler to view. A feature handler contains callbacks to be used in view.
   *
   * @param feature feature handler.
   */
  void addFeatureHandler(Features feature);

  /**
   * Display the image to the user.
   *
   * @param imageName name of the image to display.
   */
  void displayImage(String imageName);
}
