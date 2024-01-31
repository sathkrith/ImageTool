package textgimp.control.commands;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import textgimp.LoggerMockModel;
import textgimp.TextGimpSuccessModelMock;
import org.junit.Test;
import textgimp.control.commands.AllCommands.Brighten;
import textgimp.utility.Result;
import utility.TestHelper;

/**
 * This is a JUnit test class that contains test classes of all commands.
 */
public class CommandsTest {

  /**
   * This is a JUnit test class for the blur command.
   */
  class BlurTest {

    /**
     * Tests the execute method of the blur command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.Filter("blur");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName};
      String[] expectedCommand = new String[]{"filter", sourceName, "blur", destName};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommand);
    }

    /**
     * Tests the execute method of the blur command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.Filter("blur");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the blur command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.Filter("blur");
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for the Brighten command.
   */
  class BrightenTest {

    /**
     * Tests the execute method of the Brighten command using valid parameters.
     */
    @Test
    public void success() {
      Command c = new AllCommands.Brighten();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{"100", sourceName, destName};
      String[] expectedCommand = new String[]{"brighten", sourceName, "100", destName};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommand);
    }

    /**
     * Tests the execute method of the Brighten command using invalid parameters.
     */
    @Test
    public void fail() {
      Command c = new Brighten();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, "100", destName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);

      String[] commandString2 = new String[]{sourceName, "100"};
      res = c.execute(commandString2, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the Brighten command.
     */
    @Test
    public void help() {
      Command c = new Brighten();
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for the dither command.
   */
  class DitherTest {

    /**
     * Tests the execute method of the dither command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.Dither();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName};
      String[] expectedCommand = new String[]{"dither", sourceName, destName};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommand);
    }

    /**
     * Tests the execute method of the dither command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.Filter("dither");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the blur command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.Filter("blur");
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for the Greyscale command.
   */
  class GreyscaleTest {

    /**
     * Tests the execute method of the Greyscale command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.Transform("greyscale");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName};
      String[] expectedCommand = new String[]{"transform", sourceName,
          "greyscale", destName};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommand);
    }

    /**
     * Tests the execute method of the Greyscale command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.Transform("greyscale");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the Greyscale command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.Transform("greyscale");
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for the Greyscale command.
   */
  class GreyscaleTransformTest {

    /**
     * Tests the execute method of the Greyscale command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.Transform("greyscale");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName};
      String[] expectedCommand = new String[]{"transform", sourceName, "greyscale", destName};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommand);
    }

    /**
     * Tests the execute method of the Greyscale command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.Transform("greyscale");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the Greyscale command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.Transform("greyscale");
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for the Horizontal Flip command.
   */
  class HFlipTest {

    /**
     * Tests the execute method of the Horizontal Flip command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.HorizontalFlip();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName};
      String[] expectedCommandString = new String[]{"h-flip", sourceName, destName};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommandString);
    }

    /**
     * Tests the execute method of the Horizontal Flip command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.HorizontalFlip();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the Horizontal Flip command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.HorizontalFlip();
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for RGBCombine command.
   */
  class RGBCombineTest {

    /**
     * Test the execute method of RGBCombine command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.RGBCombine();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String destName2 = TestHelper.generateRandomStringOfSize(10);
      String destName3 = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName, destName2, destName3};
      String[] expectedCommandString = new String[]{"rgb-combine",
          destName, destName2, destName3, sourceName,};

      // validate the results
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommandString);
    }

    /**
     * Test the execute method of RGBCombine command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.RGBCombine();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);

      // validate the results
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Test the help method of RGBCombine command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.RGBCombine();
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for RGBSplit command.
   */
  class RGBSplitTest {

    /**
     * Test the execute method of RGBSplit command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.RGBSplit();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String destName2 = TestHelper.generateRandomStringOfSize(10);
      String destName3 = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName, destName2, destName3};
      String[] expectedCommandString = new String[]{"rgb-split", sourceName,
          destName, destName2, destName3};

      // validate the results
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommandString);
    }

    /**
     * Test the execute method of RGBSplit command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.RGBSplit();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Test the help method of RGBSplit command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.RGBSplit();
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for the sepia command.
   */
  class SepiaTest {

    /**
     * Tests the execute method of the sepia command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.Transform("sepia");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName};
      String[] expectedCommand = new String[]{"transform", sourceName, "sepia", destName};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommand);
    }

    /**
     * Tests the execute method of the sepia command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.Transform("sepia");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the Greyscale command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.Transform("sepia");
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for the Sharpen command.
   */
  class SharpenTest {

    /**
     * Tests the execute method of the sharpen command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.Filter("sharpen");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName};
      String[] expectedCommand = new String[]{"filter", sourceName, "sharpen", destName};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommand);
    }

    /**
     * Tests the execute method of the sharpen command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.Filter("sharpen");
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the sharpen command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.Filter("sharpen");
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for the VerticalFlip command.
   */
  class VFlipTest {

    /**
     * Tests the execute method of the VerticalFlip command using valid parameters.
     */
    @Test
    public void Success() {
      Command c = new AllCommands.VerticalFLip();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // Test valid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName, destName};
      String[] expectedCommandString = new String[]{"v-flip", sourceName, destName};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(m.getLog().get(0), expectedCommandString);
    }

    /**
     * Tests the execute method of the VerticalFlip command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new AllCommands.VerticalFLip();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // test invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the VerticalFlip command.
     */
    @Test
    public void help() {
      Command c = new AllCommands.VerticalFLip();
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test for the Load command.
   */
  class LoadTest {

    /**
     * Test the execute method of the Load command using valid parameters.
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void Success() throws Exception {
      Command c = new LoadFile();
      TextGimpSuccessModelMock m = new TextGimpSuccessModelMock();

      // generate a sample ppm file
      String ext = TestHelper.generateRandomStringOfSize(3);
      String testResourceFolder = "res/test/";
      String fileName = testResourceFolder + TestHelper.generateRandomStringOfSize(10) + "." + ext;
      File f = new File(fileName);
      String fileContent = TestHelper.generateRandomStringOfSize(20);

      // delete the file if it exists
      if (f.exists()) {
        if (!f.delete()) {
          fail();
        }
      }

      // write ppm image data to file
      if (f.createNewFile()) {
        try (FileWriter fw = new FileWriter(f)) {
          fw.write(fileContent);
        }
      }

      // execute the command with valid parameters
      String destName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{f.getAbsolutePath(), destName};
      c.execute(commandString, m);

      // check if the command was executed successfully
      byte[] expectedBytes = Files.readAllBytes(f.toPath());
      String[] expectedCommandString = new String[]{
          "load", new String(expectedBytes, StandardCharsets.UTF_8), destName, ext};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(expectedCommandString, m.getLog().get(0));

      // delete the file
      if (f.exists()) {
        if (!f.delete()) {
          fail();
        }
      }
    }

    /**
     * Test the execute method of the Load command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new LoadFile();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // generate command string with invalid parameters
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};

      // execute the command with invalid parameters and check if it fails
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);

      String[] commandString2 = new String[]{sourceName, sourceName};
      res = c.execute(commandString2, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Test the help method of the Load command.
     */
    @Test
    public void help() {
      Command c = new LoadFile();
      assertNotEquals(0, c.help().length());
    }
  }

  /**
   * This is a JUnit test class for the Save command.
   */
  class SaveTest {

    // The folder where the test resources are located.
    private final String testResourceFolder = "res/test/";

    /**
     * Tests the execute method of the Save command using valid parameters. Tests if valid commands
     * were passed to the model.
     *
     * @throws Exception if an error occurs.
     */
    @Test
    public void SuccessCommand() throws Exception {
      Command c = new SaveFile();
      TextGimpSuccessModelMock m = new TextGimpSuccessModelMock();

      // Test correct command
      String ext = TestHelper.generateRandomStringOfSize(3);
      String fileName = testResourceFolder + TestHelper.generateRandomStringOfSize(10) + "." + ext;

      // create a file if it does not exist
      File f = new File(fileName);
      if (!f.exists()) {
        if (!f.createNewFile()) {
          fail();
        }
      }

      // generate a destination name to save the file
      String destName = TestHelper.generateRandomStringOfSize(10);

      // execute the command and check if the same command was passed
      String[] commandString = new String[]{f.getAbsolutePath(), destName};
      c.execute(commandString, m);
      String[] expectedCommandString = new String[]{"save", destName, ext};
      c.execute(commandString, m);
      assertArrayEquals(expectedCommandString, m.getLog().get(0));

      // delete the file
      if (f.exists()) {
        if (!f.delete()) {
          fail();
        }
      }
    }

    /**
     * Tests the execute method of the Save command using valid parameters. Tests if command was
     * executed successfully.
     */
    @Test
    public void Success() {
      Command c = new SaveFile();
      TextGimpSuccessModelMock m = new TextGimpSuccessModelMock();

      // test a valid command
      String ext = TestHelper.generateRandomStringOfSize(3);
      String fileName = testResourceFolder + TestHelper.generateRandomStringOfSize(10) + "." + ext;

      // create a file if it does not exist
      File f = new File(fileName);
      if (f.exists()) {
        if (!f.delete()) {
          fail();
        }
      }

      // generate a destination name to save the file
      String destName = TestHelper.generateRandomStringOfSize(10);

      // execute the command and validate the results
      String[] commandString = new String[]{f.getAbsolutePath(), destName};
      c.execute(commandString, m);
      String[] expectedCommandString = new String[]{"save", destName, ext};
      Result res = c.execute(commandString, m);
      assertTrue(res.isSuccess());
      assertArrayEquals(expectedCommandString, m.getLog().get(0));

      // delete the file
      if (f.exists()) {
        if (!f.delete()) {
          fail();
        }
      }
    }

    /**
     * Tests the execute method of the Save command using invalid parameters.
     */
    @Test
    public void Fail() {
      Command c = new SaveFile();
      LoggerMockModel m = new TextGimpSuccessModelMock();

      // test an invalid command
      String sourceName = TestHelper.generateRandomStringOfSize(10);
      String[] commandString = new String[]{sourceName};
      Result res = c.execute(commandString, m);
      assertFalse(res.isSuccess());
      assertEquals(m.getLog().size(), 0);
    }

    /**
     * Tests the help method of the Save command.
     */
    @Test
    public void help() {
      Command c = new SaveFile();
      assertNotEquals(0, c.help().length());
    }
  }
}



