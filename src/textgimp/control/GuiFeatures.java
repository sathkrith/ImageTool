package textgimp.control;

import textgimp.control.commands.CommandRunner;
import textgimp.control.commands.ImageCommandRunner;
import textgimp.guiview.GUIView;
import textgimp.model.Model;
import textgimp.utility.Result;
import textgimp.utility.ResultImpl;

/**
 * This class implements features for the GUI view. It contains callback methods for handling the
 * user input on the GUI view. Builds the string command and delegates it to the command objects.
 */
public class GuiFeatures implements Features {

  private final CommandRunner imageCommandRunner;
  private final Model model;
  private final GUIView view;

  // default image names - required while loading images from GUI
  private final String TARGET = "TARGET";
  private final String TARGET_RED = "TARGET_RED";
  private final String TARGET_GREEN = "TARGET_GREEN";
  private final String TARGET_BLUE = "TARGET_BLUE";

  /**
   * Initialize the feature handler with model, view and command objects.
   *
   * @param model model for the application
   * @param view  GUI view for the application
   */
  GuiFeatures(Model model, GUIView view) {
    this.imageCommandRunner = new ImageCommandRunner(model);
    this.model = model;
    this.view = view;
  }

  @Override
  public Result load(String path) {
    // build and run the load command using command runner
    Result res = imageCommandRunner.runCommand(String.format("load %s %s", path, TARGET));
    if (res.isSuccess()) {
      view.displayImage(TARGET);
    }
    return res;
  }

  @Override
  public Result save(String path) {
    return imageCommandRunner.runCommand(String.format("save %s %s", path,
        TARGET));
  }

  @Override
  public Result horizontalFlip() {
    try {
      model.horizontalFlip(TARGET, TARGET);
      view.displayImage(TARGET);
      return new ResultImpl(true, "Successfully flipped the image");
    } catch (Exception e) {
      return new ResultImpl(false, "Failed to flip, no image loaded.");
    }
  }

  @Override
  public Result verticalFlip() {
    try {
      model.verticalFlip(TARGET, TARGET);
      view.displayImage(TARGET);
      return new ResultImpl(true, "Successfully flipped the image");
    } catch (Exception e) {
      return new ResultImpl(false, "Failed to flip, no image loaded.");
    }
  }

  @Override
  public Result rgbSplit(String redPath, String greenPath,
      String bluePath) {
    try {
      model.rgbSplit(TARGET, TARGET_RED, TARGET_GREEN, TARGET_BLUE);
    } catch (Exception e) {
      return new ResultImpl(false, "Failed to split, no image loaded.");
    }
    Result res = imageCommandRunner.runCommand(String.format("save %s %s",
        redPath,
        TARGET_RED));
    if (!res.isSuccess()) {
      return res;
    }
    res = imageCommandRunner.runCommand(String.format("save %s %s", bluePath,
        TARGET_BLUE));
    if (!res.isSuccess()) {
      return res;
    }
    res = imageCommandRunner.runCommand(String.format("save %s %s", greenPath,
        TARGET_GREEN));
    if (!res.isSuccess()) {
      return res;
    }
    return new ResultImpl(true, "Successfully split the image and saved at "
        + "the location");
  }

  @Override
  public Result brighten(int amount) {
    try {
      model.brighten(TARGET, amount, TARGET);
      view.displayImage(TARGET);
      return new ResultImpl(true, "Successfully brightened the image");
    } catch (Exception e) {
      return new ResultImpl(false, "Failed to split, no image loaded.");
    }
  }

  @Override
  public Result rgbCombine(String redPath, String greenPath,
      String bluePath) {
    Result res = imageCommandRunner.runCommand(String.format("load %s %s",
        redPath,
        TARGET_RED));
    if (!res.isSuccess()) {
      return res;
    }
    res = imageCommandRunner.runCommand(String.format("load %s %s", bluePath,
        TARGET_BLUE));
    if (!res.isSuccess()) {
      return res;
    }
    res = imageCommandRunner.runCommand(String.format("load %s %s", greenPath,
        TARGET_GREEN));
    if (!res.isSuccess()) {
      return res;
    }
    try {
      model.rgbCombine(TARGET_RED, TARGET_GREEN,
          TARGET_BLUE, TARGET);
      view.displayImage(TARGET);
      return new ResultImpl(true, "Successfully combined the images");
    } catch (Exception e) {
      return new ResultImpl(false, "Failed to combine, images are not of the "
          + "same size.");
    }
  }

  @Override
  public Result greyscale(String component) {
    try {
      model.greyscale(TARGET, component, TARGET);
      view.displayImage(TARGET);
      return new ResultImpl(true, "Successfully greyscaled the image");
    } catch (Exception e) {
      return new ResultImpl(false, "Failed to greyscale, no image loaded.");
    }
  }

  @Override
  public Result filter(String filterType) {
    try {
      model.filter(TARGET, filterType, TARGET);
      view.displayImage(TARGET);
      return new ResultImpl(true, "Successfully filtered the image");
    } catch (Exception e) {
      return new ResultImpl(false, "Failed to filtered, no image loaded.");
    }
  }

  @Override
  public Result transform(String transformType) {
    try {
      model.transform(TARGET, transformType, TARGET);
      view.displayImage(TARGET);
      return new ResultImpl(true, "Successfully transformed the image");
    } catch (Exception e) {
      return new ResultImpl(false, "Failed to transformed, no image loaded.");
    }
  }

  @Override
  public Result dither() {
    try {
      model.dither(TARGET, TARGET);
      view.displayImage(TARGET);
      return new ResultImpl(true, "Successfully dithered the image");
    } catch (Exception e) {
      return new ResultImpl(false, "Failed to dithered, no image loaded.");
    }
  }
}
