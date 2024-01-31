package textgimp.control;

import static java.util.Map.entry;
import static utility.TestHelper.generateRandomString;
import static utility.TestHelper.generateRandomStringOfSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import utility.TestHelper;

/**
 * This is a helper class to test the controller. It takes care of generating valid and invalid
 * commands to test the controller.
 */
public class ControllerHelper {

  // The following are the valid components for the greyscale command.
  public static final Map<Integer, String> COMPONENT_MAP = Map.ofEntries(
      entry(0, "red-component"),
      entry(1, "green-component"),
      entry(2, "blue-component"),
      entry(3, "luma-component"),
      entry(4, "value-component"),
      entry(5, "intensity-component")
  );

  public static final Random RANDOM_GEN = new Random(0);
  public static final Map<Integer, Callable<ExecutionResult>> COMMAND_MAP = createCommandMap();

  public static final List<String> COMMAND_LIST = Arrays.asList(
      "load",
      "save", "blur", "sepia", "greyscale", "sharpen", "vertical-flip",
      "horizontal-flip", "rgb-split", "rgb-combine"
  );

  /**
   * Generate commands and store them in a map.
   *
   * @return a map of commands.
   */
  public static Map<Integer, Callable<ControllerHelper.ExecutionResult>> createCommandMap() {
    Map<Integer, Callable<ControllerHelper.ExecutionResult>> commandMap = new HashMap<>();
    commandMap.put(0, ControllerHelper::createRandomVerticalFlipCommand);
    commandMap.put(1, ControllerHelper::createRandomHorizontalFlipCommand);
    commandMap.put(2, ControllerHelper::createRandomGreyscaleCommand);
    commandMap.put(3, ControllerHelper::createRandomBrightenCommand);
    commandMap.put(4, ControllerHelper::createRandomRGBSplitCommand);
    commandMap.put(5, ControllerHelper::createRandomRGBCombineCommand);
    commandMap.put(6, ControllerHelper::createRandomBlurCommand);
    commandMap.put(7, ControllerHelper::createRandomSepiaCommand);
    commandMap.put(8, ControllerHelper::createRandomSharpenCommand);
    return commandMap;
  }

  /**
   * Generate and return a list of valid commands.
   *
   * @return a list of valid commands.
   */
  public static List<String> getValidAllCommands() {
    List<String> cmd = new ArrayList<>();
    String name = generateRandomStringOfSize(10);
    cmd.add("load " + "res/test/blueP.ppm" + " " + name);
    cmd.add("save " + "res/test/saveBlueP.ppm" + " " + name);
    cmd.add("vertical-flip " + name + " " + generateRandomStringOfSize(10));
    cmd.add("brighten " + "10 " + name + " " + generateRandomStringOfSize(10));
    cmd.add("greyscale " + COMPONENT_MAP.get(RANDOM_GEN.nextInt(COMPONENT_MAP.size())) + " "
        + name + " " + generateRandomStringOfSize(10));
    cmd.add("blur " + name + " " + generateRandomStringOfSize(10));
    cmd.add("sharpen " + name + " " + generateRandomStringOfSize(10));
    cmd.add("sepia " + name + " " + generateRandomStringOfSize(10));
    String split1 = generateRandomStringOfSize(10) + " ";
    String split2 = generateRandomStringOfSize(10) + " ";
    String split3 = generateRandomStringOfSize(10);
    cmd.add("rgb-split " + name + " " + split1 + split2 + split3);
    cmd.add("rgb-combine " + generateRandomStringOfSize(10) + " " + split1 + split2 + split3);
    cmd.add("horizontal-flip " + name + " " + generateRandomStringOfSize(10));
    return cmd;
  }

  /**
   * Generate and return a list of random commands for fuzzy testing.
   *
   * @return a list of random commands.
   */
  public static List<String> getRandomAllCommandsChain() {
    List<String> cmd = new ArrayList<>();
    cmd.add("load " + generateRandomString() + " " + TestHelper.generateRandomString());
    cmd.add("save  " + generateRandomString() + " " + TestHelper.generateRandomString());
    cmd.add(ControllerHelper.createRandomGreyscaleCommand().command);
    cmd.add(ControllerHelper.createRandomBrightenCommand().command);
    cmd.add(ControllerHelper.createRandomRGBSplitCommand().command);
    cmd.add(ControllerHelper.createRandomHorizontalFlipCommand().command);
    cmd.add(ControllerHelper.createRandomVerticalFlipCommand().command);
    cmd.add(ControllerHelper.createRandomRGBCombineCommand().command);
    cmd.add(ControllerHelper.createRandomBlurCommand().command);
    cmd.add(ControllerHelper.createRandomSepiaCommand().command);
    cmd.add(ControllerHelper.createRandomSharpenCommand().command);
    return cmd;
  }

  /**
   * This method generates commands, and stores the results of the commands in a wrapper class.
   *
   * @param numCommands the number of commands to generate.
   * @return a wrapper class containing the results of the commands.
   * @throws Exception if the commands fail.
   */
  public static ExecutionResultWrapper generateRandomCommands(int numCommands) throws Exception {
    ExecutionResultWrapper results = new ExecutionResultWrapper();
    for (int j = 0; j < numCommands; j++) {
      ExecutionResult r = COMMAND_MAP.get(RANDOM_GEN.nextInt(COMMAND_MAP.size())).call();
      results.addResult(r);
    }
    return results;
  }

  /**
   * Generate a random brighten command.
   *
   * @return a random brighten command.
   */
  public static ExecutionResult createRandomBrightenCommand() {
    int a = RANDOM_GEN.nextInt();
    String b = TestHelper.generateRandomString();
    String c = TestHelper.generateRandomString();
    return new ExecutionResult(
        String.format("brighten %d %s %s", a, b, c),
        new String[]{"brighten", b, String.valueOf(a), c});
  }

  /**
   * Generate a random greyscale command.
   *
   * @return a random greyscale command.
   */
  public static ExecutionResult createRandomGreyscaleCommand() {
    String component = COMPONENT_MAP.get(RANDOM_GEN.nextInt(COMPONENT_MAP.size()));
    String b = TestHelper.generateRandomString();
    String c = TestHelper.generateRandomString();
    return new ExecutionResult(
        String.format("greyscale %s %s %s", component, b, c),
        new String[]{"greyscale", b, component, c});
  }

  /**
   * Generate a random sepia command.
   *
   * @return a random sepia command.
   */
  public static ExecutionResult createRandomSepiaCommand() {
    String b = TestHelper.generateRandomString();
    String c = TestHelper.generateRandomString();
    return new ExecutionResult(
        String.format("sepia %s %s", b, c),
        new String[]{"transform", b, "sepia", c});
  }


  /**
   * Generate a random sharpen command.
   *
   * @return a random sharpen command.
   */
  public static ExecutionResult createRandomSharpenCommand() {
    String b = TestHelper.generateRandomString();
    String c = TestHelper.generateRandomString();
    return new ExecutionResult(
        String.format("sharpen %s %s", b, c),
        new String[]{"filter", b, "sharpen", c});
  }


  /**
   * Generate a random blur command.
   *
   * @return a random blur command.
   */
  public static ExecutionResult createRandomBlurCommand() {
    String b = TestHelper.generateRandomString();
    String c = TestHelper.generateRandomString();
    return new ExecutionResult(
        String.format("blur %s %s", b, c),
        new String[]{"filter", b, "blur", c});
  }


  /**
   * Generate a random horizontal flip command.
   *
   * @return a random horizontal flip command.
   */
  public static ExecutionResult createRandomHorizontalFlipCommand() {
    String a = TestHelper.generateRandomString();
    String b = TestHelper.generateRandomString();
    return new ExecutionResult(
        String.format("horizontal-flip %s %s", a, b),
        new String[]{"h-flip", a, b});
  }

  /**
   * Generate a random vertical flip command.
   *
   * @return a random vertical flip command.
   */
  public static ExecutionResult createRandomVerticalFlipCommand() {
    String a = TestHelper.generateRandomString();
    String b = TestHelper.generateRandomString();
    return new ExecutionResult(
        String.format("vertical-flip %s %s", a, b),
        new String[]{"v-flip", a, b});
  }

  /**
   * Generate a random rgb split command.
   *
   * @return a random rgb split command.
   */
  public static ExecutionResult createRandomRGBSplitCommand() {
    String a = TestHelper.generateRandomString();
    String b = TestHelper.generateRandomString();
    String c = TestHelper.generateRandomString();
    String d = TestHelper.generateRandomString();
    return new ExecutionResult(String.format("rgb-split %s %s %s %s", a, b, c, d),
        new String[]{"rgb-split", a, b, c, d});
  }

  /**
   * Generate a random rgb combine command.
   *
   * @return a random rgb combine command.
   */
  public static ExecutionResult createRandomRGBCombineCommand() {
    String a = TestHelper.generateRandomString();
    String b = TestHelper.generateRandomString();
    String c = TestHelper.generateRandomString();
    String d = TestHelper.generateRandomString();
    return new ExecutionResult(String.format("rgb-combine %s %s %s %s", a, b, c, d),
        new String[]{"rgb-combine", b, c, d, a});
  }

  /**
   * This class represents an execution command with command line and arguments.
   */
  public static final class ExecutionResult {

    public final String command;
    public final String[] args;

    /**
     * Constructor for ExecutionResult.
     *
     * @param command the command line.
     * @param args    the arguments.
     */
    ExecutionResult(String command, String[] args) {
      this.command = command;
      this.args = args;
    }
  }

  /**
   * This class represents a wrapper for a list of execution commands.
   */
  public static final class ExecutionResultWrapper {

    public final List<String> commands = new ArrayList<>();
    public final List<String[]> args = new ArrayList<>();

    /**
     * Add a result to the wrapper.
     *
     * @param r the result to add.
     */
    public void addResult(ExecutionResult r) {
      commands.add(r.command);
      args.add(r.args);
    }

    /**
     * Extend the wrapper with another wrapper.
     *
     * @param rw the wrapper to extend.
     */
    public void extend(ExecutionResultWrapper rw) {
      commands.addAll(rw.commands);
      args.addAll(rw.args);
    }
  }
}
