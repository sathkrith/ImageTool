package textgimp.control.commands;

import textgimp.model.Model;
import textgimp.utility.Result;

/**
 * This interface represents a TextGIMP command. The implementation classes are responsible for
 * calling appropriate methods in the model to perform the operation.
 */
interface Command {

  /**
   * Validate the sting parameters and call the appropriate methods in the model to perform the
   * operation.
   *
   * @param args  arguments for the command.
   * @param model model to run the command on.
   * @return result of the command.
   */
  Result execute(String[] args, Model model);

  /**
   * Get the help string for the command.
   *
   * @return help string for the command.
   */
  String help();
}
