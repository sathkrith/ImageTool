package textgimp.control.commands;

import textgimp.utility.Result;

/**
 * This interface represents a TextGIMP command runner. The implementation classes are responsible
 * for executing a command using the provided command objects.
 */
public interface CommandRunner {

  /**
   * Execute a string command. Decode the input string, create a command object, and run the
   * command.
   *
   * @param command a string representing the command to run.
   * @return Result the result of running the command.
   */
  Result runCommand(String command);

  /**
   * Get help string for command.
   *
   * @param command command to get help for.
   * @return Result with help content.
   */
  Result getHelp(String command);
}
