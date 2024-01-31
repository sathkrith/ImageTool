package textgimp.control;

import static org.junit.Assert.assertArrayEquals;

import textgimp.TextGimpSuccessModelMock;
import textgimp.ViewMock;
import org.junit.Test;

/**
 * This class tests GUI Gimp controller.
 */
public class GUIGimpControllerTest {

  @Test
  public void run() {
    ViewMock view = new ViewMock();
    TextGimpSuccessModelMock model = new TextGimpSuccessModelMock();
    GUIGimpController g = new GUIGimpController(model, view);
    g.run();
    assertArrayEquals(view.log.get(0), new String[]{"show"});
  }
}