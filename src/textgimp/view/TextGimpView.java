package textgimp.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import textgimp.utility.Result;

/**
 * This class represents a view for TextGimp application, implements View interface. View is
 * responsible for displaying the result of the operation to the user.
 */
public class TextGimpView implements TextView {

  private final Scanner inputReader; // InputStream to read input from.
  private final OutputStream output; // OutputStream to send output to.

  /**
   * Construct a view object with passed OutputStream.
   *
   * @param out OutputStream to send output to.
   */
  public TextGimpView(InputStream in, OutputStream out) {
    this.inputReader = new Scanner(in);
    this.output = out;
  }

  @Override
  public boolean hasInput() {
    return inputReader.hasNextLine();
  }

  @Override
  public String readInputByLine() {
    return inputReader.nextLine();
  }

  /**
   * Print string to output.
   *
   * @param message message to print.
   * @throws IOException if Output error occurs.
   */
  private void printToOutput(String message) throws IOException {
    output.write(message.getBytes());
  }

  @Override
  public void displayResult(Result result) {
    // check if the result object is null
    if (result == null) {
      return;
    }

    // display the message based on whether it is a success or error
    try {
      if (result.isSuccess()) {
        printToOutput(result.getMessage() + "\n");
      } else {
        printToOutput("Error: " + result.getMessage() + "\n");
      }
    } catch (IOException e) {
      System.out.println(result.getMessage());
      System.err.println(e.getMessage());
    }
  }
}
