package textgimp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static textgimp.control.ControllerHelper.getRandomAllCommandsChain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import textgimp.control.Controller;
import textgimp.control.ControllerHelper;
import textgimp.control.TextGimpController;
import textgimp.model.Model;
import textgimp.model.TextGimpModel;
import textgimp.view.TextGimpView;
import textgimp.view.TextView;

/**
 * This is a JUnit test class for end-to-end testing for the TextGimp application.
 */
public class MvcTest {

  private final int helpStart = 43;

  /**
   * This method runs valid commands for the TextGimp application.
   */
  @Test
  public void run() {
    // fetch all valid commands
    List<String> commands = ControllerHelper.getValidAllCommands();

    // convert commands to input stream
    InputStream in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    // create a model
    Model m = new TextGimpModel();

    // create a view with output and input streams
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    TextView v = new TextGimpView(in, output);

    // create a controller and pass the model and view
    Controller ct = new TextGimpController(m, v);

    // run the controller and execute the commands
    ct.run();

    // read the outputs from the output stream
    String[] outputInStrList = output.toString().split("\n");
    assertEquals(commands.size(), outputInStrList.length - helpStart);

    // validate the outputs
    int minIndex = 45;
    for (int j = minIndex; j < outputInStrList.length; j++) {
      System.out.println(outputInStrList[j]);
      assertTrue(outputInStrList[j].startsWith("Success"));
    }

    // delete the files created by the application
    File f = new File("res/test/saveBlueP.ppm");
    if (f.exists()) {
      f.delete();
    }
  }

  /**
   * This method runs invalid commands for the TextGimp application.
   */
  @Test
  public void failure() {
    // fetch invalid commands
    List<String> commands = getRandomAllCommandsChain();

    // convert commands to input stream
    InputStream in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    // create a model
    TextGimpModel m = new TextGimpModel();

    // create a view with output and input streams
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    TextView v = new TextGimpView(in, output);

    // create a controller and pass the model and view
    Controller ct = new TextGimpController(m, v);

    // run the controller and execute the commands
    ct.run();

    // read the outputs from the output stream
    String[] outputInStrList = output.toString().split("\n");
    assertEquals(commands.size(), outputInStrList.length - helpStart);

    // validate the outputs
    for (int j = helpStart; j < outputInStrList.length; j++) {
      assertTrue(outputInStrList[j].startsWith("Error"));
    }
  }
}
