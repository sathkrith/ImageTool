package textgimp.control;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static textgimp.control.ControllerHelper.generateRandomCommands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;
import textgimp.LoggerMockModel;
import textgimp.TextGimpFailureModelMock;
import textgimp.TextGimpSuccessModelMock;
import org.junit.Test;
import textgimp.control.ControllerHelper.ExecutionResultWrapper;
import textgimp.view.TextGimpView;
import textgimp.view.TextView;
import utility.TestHelper;

/**
 * This class represents a test class for the TextGimpController class.
 */
public class TextGimpControllerTest {

  private final String testResourceFolder;
  private final Random random;

  /**
   * Constructs a new TextGimpControllerTest and initializes the test resource folder.
   */
  public TextGimpControllerTest() {
    this.random = new Random(0);
    this.testResourceFolder = "res/test/";
  }

  /**
   * Tests the run method of the TextGimpController class using a success mock model.
   *
   * @throws Exception if an error occurs
   */
  @Test
  public void run() throws Exception {
    for (int i = 0; i < 2000; i++) {
      // generate random commands
      ExecutionResultWrapper commands = generateRandomCommands(random.nextInt(2000));

      // convert commands to input stream
      InputStream in = new ByteArrayInputStream(
          String.join("\n", commands.commands).getBytes());

      // create a success mock model
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // create a view with the input and output streams
      TextView v = new TextGimpView(in, OutputStream.nullOutputStream());

      // create a controller with the model and view
      Controller ct = new TextGimpController(m, v);

      // run the controller
      ct.run();

      // get the log from the model
      List<String[]> log = m.getLog();
      assertEquals(commands.args.size(), log.size());

      // validate the outputs
      for (int j = 0; j < commands.args.size(); j++) {
        assertArrayEquals(commands.args.get(j), log.get(j));
      }
    }
  }


  /**
   * Tests the run method of the TextGimpController class using a failure mock model.
   *
   * @throws Exception if an error occurs
   */
  @Test
  public void failure() throws Exception {
    for (int i = 0; i < 2000; i++) {
      // generate random commands
      ExecutionResultWrapper commands = generateRandomCommands(random.nextInt(2000));

      // convert commands to input stream
      InputStream in = new ByteArrayInputStream(String.join("\n",
          commands.commands).getBytes());

      // create a failure mock model
      LoggerMockModel m = new TextGimpFailureModelMock();

      // create a view with the input and output streams
      TextView v = new TextGimpView(in, OutputStream.nullOutputStream());

      // create a controller with the model and view
      Controller ct = new TextGimpController(m, v);
      ct.run();

      // get the log from the model
      List<String[]> log = m.getLog();
      assertEquals(commands.args.size(), log.size());

      // validate the outputs
      for (int j = 0; j < commands.args.size(); j++) {
        assertArrayEquals(commands.args.get(j), log.get(j));
      }
    }
  }

  /**
   * Tests the CLI parameters of the controller using a success mock model. This runs the controller
   * from a file using the CLI command.
   *
   * @throws Exception if an error occurs
   */
  @Test
  public void runFromScriptCli() throws Exception {
    // create a test script file
    String filePath = testResourceFolder + "/" + TestHelper.generateRandomStringOfSize(10) + ".tg";
    File testScript = new File(filePath);

    // delete the file if it already exists
    if (testScript.exists()) {
      testScript.delete();
    }

    // create a new file
    if (testScript.createNewFile()) {
      for (int i = 0; i < 2000; i++) {
        List<String[]> obtainedLog;
        // generate random commands
        ExecutionResultWrapper commands = generateRandomCommands(random.nextInt(2000));

        // write the commands to the test script file
        try (FileWriter fw = new FileWriter(testScript)) {
          fw.write(String.join("\n", commands.commands));
        }

        // run the test script file
        try (InputStream in = new FileInputStream(testScript)) {
          // create a success mock model
          LoggerMockModel m = new TextGimpSuccessModelMock();

          // create a view with the input and output streams
          TextView v = new TextGimpView(in, OutputStream.nullOutputStream());

          // create a controller with the model and view
          Controller ct = new TextGimpController(m, v);

          // run the controller
          ct.run();

          // get the log from the model
          obtainedLog = m.getLog();
        }

        // validate the outputs
        for (int j = 0; j < commands.commands.size(); j++) {
          assertArrayEquals(commands.args.get(j), obtainedLog.get(j));
        }
      }
    }

    // delete the test script file
    testScript.delete();
  }

  /**
   * Tests the run command of controller using a success mock model. This runs the controller from a
   * file using the run command.
   *
   * @throws Exception if an error occurs
   */
  @Test
  public void runFromScriptCommand() throws Exception {
    // create a test script file
    String filePath = testResourceFolder + "/" + TestHelper.generateRandomStringOfSize(10) + ".tg";
    File testScript = new File(filePath);

    // delete the file if it already exists
    if (testScript.exists()) {
      testScript.delete();
    }

    // create a new file
    if (testScript.createNewFile()) {
      for (int i = 0; i < 2000; i++) {
        // generate random commands
        List<String[]> obtainedLog;
        ExecutionResultWrapper commandsRun = generateRandomCommands(random.nextInt(1000));

        // add run from script command to the file
        commandsRun.commands.add("\nrun " + testScript.getAbsolutePath() + "\n");

        // convert commands to input stream
        InputStream in = new ByteArrayInputStream(String.join("\n",
            commandsRun.commands).getBytes());

        // write the commands to the test script file
        ExecutionResultWrapper commandsInScript = generateRandomCommands(random.nextInt(1000));
        try (FileWriter fw = new FileWriter(testScript)) {
          fw.write(String.join("\n", commandsInScript.commands));
        }
        commandsRun.extend(commandsInScript);

        // create a success mock model
        LoggerMockModel m = new TextGimpSuccessModelMock();

        // create a view with the input and output streams
        TextView v = new TextGimpView(in, OutputStream.nullOutputStream());

        // create a controller with the model and view
        Controller ct = new TextGimpController(m, v);

        // run the controller
        ct.run();

        // get the log from the model
        obtainedLog = m.getLog();

        // validate the outputs
        for (int j = 0; j < commandsRun.args.size(); j++) {
          assertArrayEquals(commandsRun.args.get(j), obtainedLog.get(j));
        }
      }
    } else {
      fail("Failed to create script file");
    }

    // delete the test script file
    testScript.delete();
  }
}