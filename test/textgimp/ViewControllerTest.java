package textgimp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static textgimp.control.ControllerHelper.getRandomAllCommandsChain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import textgimp.control.Controller;
import textgimp.control.TextGimpController;
import textgimp.view.TextGimpView;
import textgimp.view.TextView;

/**
 * This is a JUnit test class for View - Controller integration of the TextGimp application.
 */
public class ViewControllerTest {

  private final int helpStart = 43;

  /**
   * This method runs valid commands for the Controller.
   */
  @Test
  public void run() {
    // fetch all valid commands
    List<String> commands = getRandomAllCommandsChain();

    // convert commands to input stream
    InputStream in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    // use mock model to test the controller
    LoggerMockModel m = new TextGimpSuccessModelMock();

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
      assertTrue(outputInStrList[j].startsWith("Success"));
    }
  }

  /**
   * This method runs invalid commands for the Controller.
   */
  @Test
  public void failure() {
    // fetch all valid commands
    List<String> commands = getRandomAllCommandsChain();

    // convert commands to input stream
    InputStream in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    // use mock model to test the controller
    LoggerMockModel m = new TextGimpFailureModelMock();

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
