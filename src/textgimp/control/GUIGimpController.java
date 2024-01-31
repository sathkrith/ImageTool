package textgimp.control;

import textgimp.guiview.GUIView;
import textgimp.model.Model;

/**
 * This class implements the GuiController interface. It provides a feature object that can be used
 * by the GUI View to handle user input and perform operations.
 */
public class GUIGimpController implements GuiController {

  private final GUIView view;

  private final Features featureControl;

  /**
   * Initialize the controller with a model and a view.
   *
   * @param model model for the application
   * @param view  GUI based view for the application
   */
  public GUIGimpController(Model model, GUIView view) {
    this.view = view;
    this.featureControl = new GuiFeatures(model, view);
  }

  @Override
  public void run() {
    this.view.showGUI();
  }

  @Override
  public Features getFeature() {
    return this.featureControl;
  }
}
