package textgimp.control.commands;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static textgimp.control.ControllerHelper.COMMAND_LIST;
import static textgimp.control.ControllerHelper.generateRandomCommands;

import textgimp.TextGimpSuccessModelMock;
import org.junit.Test;
import textgimp.control.ControllerHelper;
import textgimp.utility.Result;

/**
 * This is a JUnit test class for ImageCommandRunner.
 */
public class ImageCommandRunnerTest {

  @Test
  public void runCommand() throws Exception {
    ControllerHelper.ExecutionResultWrapper commands =
        generateRandomCommands(ControllerHelper.RANDOM_GEN.nextInt(2000));
    TextGimpSuccessModelMock m = new TextGimpSuccessModelMock();
    ImageCommandRunner im = new ImageCommandRunner(m);
    for (int i = 0; i < commands.commands.size(); i++) {
      Result res = im.runCommand(commands.commands.get(i));
      assertTrue(res.isSuccess());
      assertFalse(res.getMessage().isEmpty());
      // validate the outputs
      assertArrayEquals(commands.args.get(i), m.getLog().get(i));
    }
  }

  @Test
  public void getHelp() {

    TextGimpSuccessModelMock m = new TextGimpSuccessModelMock();
    ImageCommandRunner im = new ImageCommandRunner(m);
    for (int i = 0; i < 2000; i++) {
      String command =
          COMMAND_LIST.get(ControllerHelper.RANDOM_GEN.nextInt(COMMAND_LIST.size()));
      Result res = im.getHelp("help " + command);
      assertTrue(res.isSuccess());
      assertFalse(res.getMessage().isEmpty());
      assertEquals(0, m.getLog().size());
    }
  }
}