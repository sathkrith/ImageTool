package textgimp.guiview;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import textgimp.control.Features;
import textgimp.guiview.mainwindow.MainFrame;
import textgimp.guiview.mainwindow.MainWindow;
import textgimp.model.ReadOnlyModel;

/**
 * This class implements the GUI for gimp with Swing.
 */
public class SwingGUI implements GUIView {

  private final ReadOnlyModel readOnlyModel;
  private MainWindow mainFrame;
  private Features features;

  /**
   * Intialize the view with  read only model.
   *
   * @param rd the read only model.
   */
  public SwingGUI(ReadOnlyModel rd)
      throws UnsupportedLookAndFeelException, ClassNotFoundException,
      InstantiationException, IllegalAccessException {
    readOnlyModel = rd;
    setLookAndFeel();
  }

  /**
   * Set up the theme, look and feel for the application.
   *
   * @throws UnsupportedLookAndFeelException if the look and feel is not supported.
   * @throws ClassNotFoundException if theme classes are not found.
   * @throws InstantiationException if the theme classes cannot be instantiated.
   * @throws IllegalAccessException if the theme classes cannot be accessed.
   */
  private void setLookAndFeel()
      throws UnsupportedLookAndFeelException, ClassNotFoundException,
      InstantiationException, IllegalAccessException {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }

  /**
   * Build the application UI and show it to the user.
   *
   * @throws IllegalStateException if the method is called without feature handler set.
   */
  @Override
  public void showGUI() throws IllegalStateException {
    if (features == null) {
      throw new IllegalStateException("Cannot show GUI without feature handler.");
    }
    mainFrame = new MainFrame(readOnlyModel, features);
    ((JFrame) mainFrame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ((JFrame) mainFrame).setVisible(true);
  }

  @Override
  public void addFeatureHandler(Features feature) {
    this.features = feature;
  }

  @Override
  public void displayImage(String imageName) {
    mainFrame.displayImage(imageName);
  }
}
