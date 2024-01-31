package textgimp.view;

import java.util.NoSuchElementException;
import textgimp.utility.Result;

/**
 * This interface represents a Text based view. View is responsible for displaying the result of the
 * operation to the user.
 */
public interface TextView {

  /**
   * Display results to the user.
   *
   * @param result result to display.
   */
  void displayResult(Result result);

  /**
   * Check if view has input to read.
   *
   * @return true if view has input, false otherwise.
   */
  boolean hasInput();

  /**
   * Get input from view.
   *
   * @return input from view.
   * @throws NoSuchElementException when there is no input to read.
   */
  String readInputByLine() throws NoSuchElementException;
}
