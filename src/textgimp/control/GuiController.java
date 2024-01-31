package textgimp.control;

/**
 * This interface represents a GUI controller. The controller provides a features object that can be
 * used by the GUI View to handle user input and perform operations.
 */
public interface GuiController extends Controller {

  /**
   * Provides a feature object that has all the supported action handlers for the GUI.
   *
   * @return feature object.
   */
  Features getFeature();
}
