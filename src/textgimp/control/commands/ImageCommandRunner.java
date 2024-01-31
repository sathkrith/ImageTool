package textgimp.control.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import textgimp.model.Model;
import textgimp.utility.Result;
import textgimp.utility.ResultImpl;

/**
 * This class represents a command runner for image commands. This class is responsible for running
 * the commands and providing help messages. Internally it used command objects to perform
 * individual operations.
 */
public class ImageCommandRunner implements CommandRunner {

  private final Map<String, Command> commandMap;

  private final Model model;

  /**
   * Constructs an image command runner object and initializes the command map.
   *
   * @param model model to run commands on.
   * @throws IllegalArgumentException if model is null.
   */
  public ImageCommandRunner(Model model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.commandMap = generateCommandMap();
    this.model = model;
  }

  /**
   * Generates a map of string and respective command objects.
   *
   * @return Map of strings and supported commands.
   */
  private Map<String, Command> generateCommandMap() {
    Map<String, Command> commandMap = new HashMap<>();
    commandMap.put("load", new LoadFile());
    commandMap.put("save", new SaveFile());
    commandMap.put("vertical-flip", new AllCommands.VerticalFLip());
    commandMap.put("horizontal-flip", new AllCommands.HorizontalFlip());
    commandMap.put("brighten", new AllCommands.Brighten());
    commandMap.put("rgb-split", new AllCommands.RGBSplit());
    commandMap.put("rgb-combine", new AllCommands.RGBCombine());
    commandMap.put("blur", new AllCommands.Filter("blur"));
    commandMap.put("sharpen", new AllCommands.Filter("sharpen"));
    commandMap.put("sepia", new AllCommands.Transform("sepia"));
    commandMap.put("greyscale", new AllCommands.Greyscale());
    commandMap.put("dither", new AllCommands.Dither());
    return commandMap;
  }

  /**
   * Runs a command from the command map.
   *
   * @param command string key to get command from map.
   * @return Result of running the command.
   */
  private Result runCommandFromMap(String command) {
    String[] splitCommand = command.trim().split(" ");
    String cmd = splitCommand[0];

    // get command object
    Command cmdObject = commandMap.getOrDefault(cmd, null);
    Result result;

    // if we don't have a command object, return error
    if (cmdObject == null) {
      result = new ResultImpl(false, "Command not supported:" + cmd);
    } else {
      // execute the command
      try {
        String[] cmdArgs = Arrays.stream(splitCommand).skip(1).toArray(String[]::new);
        result = cmdObject.execute(cmdArgs, this.model);
      } catch (Exception e) {
        result = new ResultImpl(false, e.getMessage());
        System.err.println(e.getMessage());
      }
    }
    return result;
  }

  @Override
  public Result runCommand(String command) {
    Result result;
    // ignore comments
    if (command.isEmpty() || command.startsWith("#")) {
      return new ResultImpl(true, "");
    }

    // ignore comments
    if (command.startsWith("q") || command.startsWith("quit")) {
      return new ResultImpl(true, true, "Thank you for using TextGimp.");
    }

    if (command.startsWith("help")) {
      return getHelp(command);
    }
    return runCommandFromMap(command);
  }

  @Override
  public Result getHelp(String command) {
    Result result;
    String[] commands = command.trim().split(" ");

    // display help for all commands
    if (commands.length < 2) {
      StringBuilder helpMessage = new StringBuilder();
      helpMessage.append("Supported commands:\n");

      // fetch help message from all commands
      for (Command cmd : commandMap.values()) {
        String cmdHelp = cmd.help() + "\n\n";
        helpMessage.append(cmdHelp);
      }

      // add default help commands
      helpMessage.append("run <filename> - run a script file\n\n");
      helpMessage.append("help <command> - display help for a command\n\n");
      helpMessage.append("help - display help for all commands\n");
      result = new ResultImpl(true, helpMessage.toString());
    } else {
      // display help for a specific command
      String cmd = commands[1];
      Command cmdObject = commandMap.getOrDefault(cmd, null);
      if (cmdObject != null) {
        result = new ResultImpl(true, cmdObject.help());
      } else {
        result = new ResultImpl(false, "Command not supported:" + cmd);
      }
    }
    return result;
  }
}
