package textgimp.model.macros;

import static textgimp.ModelMocks.createGenericImageWithPixel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import textgimp.model.betterimage.Image;
import textgimp.model.betterimage.Pixel;
import textgimp.model.betterimage.RGBPixel;
import textgimp.model.macros.colortransform.Brighten;
import textgimp.model.macros.colortransform.ColorTransformManager;
import textgimp.model.macros.colortransform.ColorTransformPresetManager;
import textgimp.model.macros.colortransform.ColorTransformType;
import textgimp.model.macros.imagetransform.Dither;
import textgimp.model.macros.imagetransform.FilterManager;
import textgimp.model.macros.imagetransform.FilterPresetManager;
import textgimp.model.macros.imagetransform.FilterType;

/**
 * This class tests macros in TextGimp.
 */
public class MacroTester {

  /**
   * This method tests the brighten macro.
   */
  @Test
  public void brighten() {
    Macro mc = new Brighten(5);
    Pixel pixel = new RGBPixel(0, 0, 0, 255);
    Pixel expectedPx = new RGBPixel(5, 5, 5, 255);
    Image img = createGenericImageWithPixel(1, 1, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));

    // test that brighten reduces amount when negative value is given
    pixel = new RGBPixel(20, 20, 20, 255);
    expectedPx = new RGBPixel(5, 5, 5, 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);
    mc = new Brighten(-15);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));

    // test brighten does not go above maxValue
    pixel = new RGBPixel(250, 250, 250, 255);
    expectedPx = new RGBPixel(255, 255, 255, 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);
    mc = new Brighten(10);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));

    // test brighten does not go below 0
    // test brighten does not go above maxValue
    pixel = new RGBPixel(10, 10, 10, 255);
    expectedPx = new RGBPixel(0, 0, 0, 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);
    mc = new Brighten(-100);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));
  }

  /**
   * This method tests the color transform macro.
   */
  @Test
  public void testColorTransformPresets() {
    ColorTransformManager mg = new ColorTransformPresetManager();
    Pixel pixel = new RGBPixel(0, 5, 10, 255);

    // test greyscale-red
    Macro mc = mg.getColorTransform(ColorTransformType.GREYSCALE_RED);
    Pixel expectedPx = new RGBPixel(0, 0, 0, 255);
    Image img = createGenericImageWithPixel(1, 1, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));

    // test green-component
    mc = mg.getColorTransform(ColorTransformType.GREYSCALE_GREEN);
    expectedPx = new RGBPixel(5, 5, 5, 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));

    // test blue-component
    mc = mg.getColorTransform(ColorTransformType.GREYSCALE_BLUE);
    expectedPx = new RGBPixel(10, 10, 10, 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));

    // test value-component
    mc = mg.getColorTransform(ColorTransformType.GREYSCALE_VALUE);
    expectedPx = new RGBPixel(10, 10, 10, 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));

    // test intensity-component
    mc = mg.getColorTransform(ColorTransformType.GREYSCALE_INTENSITY);
    expectedPx = new RGBPixel(5, 5, 5, 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));

    // test luma-component
    // luma is calculated by Math.round(0.2126 * red + 0.7152 * green + 0.0722 * blue)
    mc = mg.getColorTransform(ColorTransformType.GREYSCALE_LUMA);
    expectedPx = new RGBPixel((int) Math.round(3.6482), (int) Math.round(3.6482),
        (int) Math.round(3.6482), 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));

    // test sepia-component
    mc = mg.getColorTransform(ColorTransformType.SEPIA);
    expectedPx = new RGBPixel((int) Math.round(5.735), (int) Math.round(5.11),
        (int) Math.round(3.98), 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 0));
  }

  /**
   * This method tests the available color transform presets.
   */
  @Test
  public void testColorTransformPresets2() {
    ColorTransformManager mg = new ColorTransformPresetManager();

    assertThrows(IllegalArgumentException.class, () -> mg.getColorTransformType(
        "random"));
    // test greyscale-red
    assertEquals(ColorTransformType.GREYSCALE_RED, mg.getColorTransformType("greyscale"

        + "-red-component"));

    // test green-component
    assertEquals(ColorTransformType.GREYSCALE_GREEN, mg.getColorTransformType("greyscale"
        + "-green-component"));

    // test blue-component
    assertEquals(ColorTransformType.GREYSCALE_BLUE, mg.getColorTransformType("greyscale"
        + "-blue-component"));

    // test value-component
    assertEquals(ColorTransformType.GREYSCALE_VALUE, mg.getColorTransformType("greyscale"
        + "-value-component"));

    // test intensity-component
    assertEquals(ColorTransformType.GREYSCALE_INTENSITY, mg.getColorTransformType("greyscale"
        + "-intensity-component"));

    // test luma-component
    assertEquals(ColorTransformType.GREYSCALE_LUMA, mg.getColorTransformType("greyscale"
        + "-luma-component"));

    // test sepia-component
    assertEquals(ColorTransformType.SEPIA, mg.getColorTransformType("sepia"));
  }

  /**
   * This method tests the available filter presets.
   */
  @Test
  public void filters() {
    FilterManager ft = new FilterPresetManager();
    Pixel pixel = new RGBPixel(4, 4, 4, 255);

    // test blur
    Macro mc = ft.getFilter(FilterType.BLUR);
    Pixel expectedPx = new RGBPixel(2, 2, 2, 255);
    Image img = createGenericImageWithPixel(1, 3, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 1));

    // test sharpen
    mc = ft.getFilter(FilterType.SHARPEN);
    expectedPx = new RGBPixel(6, 6, 6, 255);
    img = createGenericImageWithPixel(1, 3, pixel, 255);
    assertEquals(expectedPx, mc.apply(img).getPixel(0, 1));
  }

  /**
   * Additional tests for the filter presets.
   */
  @Test
  public void filters2() {
    FilterManager ft = new FilterPresetManager();

    assertThrows(IllegalArgumentException.class, () -> ft.getFilterType(
        "random"));
    // test blur
    assertEquals(FilterType.BLUR, ft.getFilterType("blur"));

    // test sharpen
    assertEquals(FilterType.SHARPEN, ft.getFilterType("sharpen"));
  }

  /**
   * This method tests dither macro.
   */
  @Test
  public void DitherTest() {
    Macro dm = new Dither();

    Pixel pixel = new RGBPixel(100, 100, 100, 255);
    Image img = createGenericImageWithPixel(1, 1, pixel, 255);

    // test Floyd-Steinberg reaching 0
    Pixel expectedPx = new RGBPixel(0, 0, 0, 255);
    assertEquals(expectedPx, dm.apply(img).getPixel(0, 0));

    pixel = new RGBPixel(200, 200, 200, 255);
    img = createGenericImageWithPixel(1, 1, pixel, 255);

    // test Floyd-Steinberg
    expectedPx = new RGBPixel(255, 255, 255, 255);
    assertEquals(expectedPx, dm.apply(img).getPixel(0, 0));
  }
}
