package textgimp.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import textgimp.control.commands.ImageCommandRunner;
import textgimp.model.Model;
import textgimp.utility.Result;
import textgimp.utility.ResultImpl;
import textgimp.view.TextView;

/**
 * Controller for TextGimp Application. Handles user interactions, validates commands, and uses
 * command objects to call appropriate methods in the model. Also, displays the results using the
 * view.
 */
public class TextGimpController implements Controller {

  private final TextView view; // TextGimp View to be used in this controller.
  private final Set<String> runningScripts; // A set of scripts that are currently running.
  private final ImageCommandRunner imageCommandRunner; // object to validate and run commands.

  /**
   * Constructs a TextGimpController object and initialize the view and model and command objects
   * using CommandRunner class.
   *
   * @param model model to run the commands on.
   * @param view  view to read input and display output.
   */
  public TextGimpController(Model model, TextView view) {
    this.view = view;
    this.imageCommandRunner = new ImageCommandRunner(model);
    this.runningScripts = new HashSet<String>();
  }

  /**
   * Display result on the view.
   *
   * @param result result object to be displayed.
   */
  private void sendToView(Result result) {
    this.view.displayResult(result);
  }

  /**
   * Run a string command, and display the result on the view.
   *
   * @param command string command to be run.
   * @return true if application has to quit after executing this command.
   */
  private boolean runCommand(String command) {
    Result result;

    // run script file
    if (command.startsWith("run ")) {
      return runFromFile(command.substring(4));
    }

    result = imageCommandRunner.runCommand(command);

    // display result
    sendToView(result);
    return result.isTerminating();
  }

  /**
   * Run commands from a script file.
   *
   * @param filePath path to the script file.
   * @return true if application has to quit after running the file.
   */
  private boolean runFromFile(String filePath) {
    // validate script file
    Result result = validateScript(filePath);
    if (!result.isSuccess()) {
      sendToView(result);
      return false;
    }

    // add file path to running scripts
    this.runningScripts.add(filePath);

    // open script file to read commands
    try (InputStream fileInput = new FileInputStream(filePath)) {
      Scanner scanner = new Scanner(fileInput);

      // read commands from file and run them
      while (scanner.hasNextLine()) {
        String command = scanner.nextLine();
        boolean quitApplication = runCommand(command);
        if (quitApplication) {
          return true;
        }
      }
    } catch (IOException e) {
      // display error message
      result = new ResultImpl(false, "Load file failed:" + e.getMessage());
      sendToView(result);
    } finally {
      // remove file path from running scripts
      this.runningScripts.remove(filePath);
    }

    return false;
  }

  @Override
  public void run() {
    // show help message
    this.displayHelp();

    try {
      while (this.view.hasInput()) {
        // read input from view and run command
        String inputData = this.view.readInputByLine();
        boolean quitApplication = runCommand(inputData);
        if (quitApplication) {
          return;
        }
      }
    } catch (UnsupportedOperationException | NoSuchElementException e) {
      sendToView(new ResultImpl(false, e.getMessage()));
    }
  }

  /**
   * Validate a script - check file path and if script is already running.
   *
   * @param filePath path to the script file.
   * @return Result object with success status and message.
   */
  private Result validateScript(String filePath) {
    Result result = new ResultImpl(true, "Script file is valid");

    // check for valid filepath
    if (!filePath.endsWith(".tg")) {
      result = new ResultImpl(false, "Invalid script file,"
          + " file must end with .tg extension");
      return result;
    }

    // check if  script is already running
    if (runningScripts.contains(filePath)) {
      result = new ResultImpl(false, "Recursive scripting not allowed" + filePath);
      return result;
    }

    return result;
  }

  /**
   * Display help message for the application.
   */
  private void displayHelp() {
    Result result = imageCommandRunner.getHelp("");
    // display result
    sendToView(result);
  }
}
