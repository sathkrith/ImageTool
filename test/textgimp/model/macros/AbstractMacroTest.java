package textgimp.model.macros;

import static textgimp.ModelMocks.createRandomGenericImage;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import textgimp.model.betterimage.Image;

/**
 * This class tests abstract macro class.
 */
public class AbstractMacroTest {

  @Test
  public void validateImage() {
    Image img = createRandomGenericImage(5, 5, 255);
    Macro mc = new TestMacro();
    assertNull(mc.apply(img));

    // throws when image is null
    assertThrows(IllegalArgumentException.class, () -> mc.apply(null));
    assertThrows(IllegalArgumentException.class,
        () -> mc.apply(createRandomGenericImage(5, 0, 255)));
    assertThrows(IllegalArgumentException.class,
        () -> mc.apply(createRandomGenericImage(0, 5, 255)));

  }

  private class TestMacro extends AbstractMacro {

    @Override
    public Image apply(Image sourceImage) throws IllegalArgumentException {
      super.validateImage(sourceImage);
      return null;
    }
  }
}