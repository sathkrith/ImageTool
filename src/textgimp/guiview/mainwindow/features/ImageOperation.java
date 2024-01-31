package textgimp.guiview.mainwindow.features;


import javax.swing.JPanel;
import textgimp.utility.Result;

/**
 * This interface represents an image operation on the GUI. This provides methods to run the
 * operation and obtain support panel if necessary. Support panels are used when the operation needs
 * additional information from the user.
 */
public interface ImageOperation {

  /**
   * Run the image operation. This method is called when the user clicks the button to run the
   * operation. This method delegates the operation to feature controller.
   *
   * @return the result of the command
   */
  Result run();

  /**
   * Returns a support panel for the operation. A support panel is used to obatin additional
   * information from the user.
   *
   * @return JPanel that allows user to input extra necessary information.
   */
  JPanel getSupportPanel();
}
