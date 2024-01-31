package textgimp.model;

import java.io.IOException;

/**
 * This class is an object adapter to model. Provides restricted access to the model allowing data
 * read.
 */
public class GuiGimpReadModel implements ReadOnlyModel {

  private final Model model;

  /**
   * Initialize the model and provide an object with read-only access to the model.
   *
   * @param model model object to use.
   */
  public GuiGimpReadModel(Model model) {
    this.model = model;
  }

  @Override
  public int[] getHistogramOfGreyscale(String name, String component)
      throws IllegalArgumentException {
    return model.getHistogramOfGreyscale(name, component);
  }

  @Override
  public byte[] getBytesOfImage(String name) throws IllegalArgumentException, IOException {
    return model.getBytesOfImage(name);
  }
}
