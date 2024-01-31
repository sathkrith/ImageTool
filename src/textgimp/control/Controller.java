package textgimp.control;

/**
 * This interface represents a TextGimp controller. Controller is responsible for obtaining input
 * from the user, calling the appropriate methods in the model, and displaying the results using the
 * view.
 */
public interface Controller {

  /**
   * Runs the controller, which starts a control loop to run the program.
   */
  void run();
}
