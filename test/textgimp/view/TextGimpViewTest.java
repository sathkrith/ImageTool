package textgimp.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static utility.TestHelper.generateRandomString;
import static utility.TestHelper.generateRandomStringOfSize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import org.junit.Test;
import textgimp.utility.ResultImpl;

/**
 * This is a JUnit test class for the TextGimpView class.
 */
public class TextGimpViewTest {

  /**
   * Test the displayResult method.
   *
   * @throws IOException if there is an error writing to the output stream
   */
  @Test
  public void displayResult() throws IOException {
    // create an output and input stream
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    InputStream in = InputStream.nullInputStream();

    // create a view with the input and output streams
    TextView view = new TextGimpView(in, out);

    // test the displayResult method for success results
    for (int i = 0; i < 2000; i++) {
      String testString = generateRandomString();
      view.displayResult(new ResultImpl(true, testString));
      assertEquals(testString + "\n", out.toString());
      out.flush();
      out.reset();
    }

    // test the displayResult method for error results
    for (int i = 0; i < 2000; i++) {
      String testString = generateRandomString();
      view.displayResult(new ResultImpl(false, testString));
      assertEquals("Error: " + testString + "\n", out.toString());
      out.flush();
      out.reset();
    }
  }

  /**
   * Test the hasInput method of the TextGimpView class.
   */
  @Test
  public void testHasInput() {
    // create an input and output stream
    String input = "";
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    InputStream in = new ByteArrayInputStream(input.getBytes());

    // create a view and validate the result
    TextView view = new TextGimpView(in, out);
    assertFalse(view.hasInput());
    assertThrows(NoSuchElementException.class, view::readInputByLine);

    String input1 = generateRandomStringOfSize(10);
    String input2 = generateRandomStringOfSize(10);
    in = new ByteArrayInputStream((input1 + "\n" + input2).getBytes());
    TextView view2 = new TextGimpView(in, out);
    assertTrue(view2.hasInput());
    assertEquals(input1, view2.readInputByLine());
    assertTrue(view2.hasInput());
    assertEquals(input2, view2.readInputByLine());
    assertFalse(view.hasInput());
    assertThrows(NoSuchElementException.class, view2::readInputByLine);

    in = new ByteArrayInputStream((input1 + "\n" + input2).getBytes());
    TextView view3 = new TextGimpView(in, out);
    assertTrue(view3.hasInput());
  }
}