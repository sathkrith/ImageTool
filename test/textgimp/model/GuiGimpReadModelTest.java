package textgimp.model;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import textgimp.TextGimpSuccessModelMock;
import org.junit.Test;
import utility.TestHelper;

/**
 * This class tests read only model.
 */
public class GuiGimpReadModelTest {

  @Test
  public void getHistogramOfGreyscale() {
    TextGimpSuccessModelMock m = new TextGimpSuccessModelMock();
    GuiGimpReadModel rd = new GuiGimpReadModel(m);
    String name = TestHelper.generateRandomStringOfSize(10);
    String component = TestHelper.generateRandomStringOfSize(10);
    rd.getHistogramOfGreyscale(name, component);
    assertArrayEquals(new String[]{"histogram", name, component},
        m.getLog().get(0));
  }

  @Test
  public void getBytesOfImage() throws IOException {
    TextGimpSuccessModelMock m = new TextGimpSuccessModelMock();
    GuiGimpReadModel rd = new GuiGimpReadModel(m);
    String name = TestHelper.generateRandomStringOfSize(10);
    rd.getBytesOfImage(name);
    assertArrayEquals(new String[]{"bytes", name},
        m.getLog().get(0));
  }
}