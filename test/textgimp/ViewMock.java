package textgimp;

import java.util.ArrayList;
import java.util.List;
import textgimp.control.Features;
import textgimp.guiview.GUIView;

/**
 * This class represents a mock view for the TextGimp program. The view writes all the commands it
 * receives to a log.
 */
public class ViewMock implements GUIView {

  public final List<String[]> log;

  /**
   * Constructs a new ViewMock and initializes the log.
   */
  public ViewMock() {
    log = new ArrayList<>();
  }

  @Override
  public void showGUI() {
    String[] args = {"show"};
    log.add(args);
  }

  @Override
  public void addFeatureHandler(Features feature) {
    String[] args = {"feature", feature.toString()};
    log.add(args);
  }

  @Override
  public void displayImage(String imageName) {
    String[] args = {"display", imageName};
    log.add(args);
  }
}
