package textgimp.control.commands;

import textgimp.model.Model;
import textgimp.utility.Result;
import textgimp.utility.ResultImpl;

/**
 * This class represents all the command objects in the TextGIMP application. All classed have been
 * moved to this file to remain in the file limit.
 */
public class AllCommands {

  /**
   * This class represents a command to brighten an image. This class is responsible for validating
   * parameters required for brighten command and calling the appropriate methods in the model.
   */
  static class Brighten implements Command {

    private final String helpMessage;

    /**
     * Constructs a brighten command object and initializes the help message.
     */
    Brighten() {
      this.helpMessage = "brighten <increment> <image-name> <dest-image-name>\n"
          + "\t\tBrighten the image with image-name by increment"
          + " and store it with dest-image-name.\n"
          + "\t\tA positive increment brightens the image and"
          + "a negative increment darkens it.";
    }

    @Override
    public Result execute(String[] args, Model model) {
      Result res;
      // we need at least 3 arguments, brighten amount, source image name and destination image name
      if (args.length < 3) {
        return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
      }

      // parse parameters and call the model to perform the operation
      // first parameter is the integer amount
      // second parameter is the source image name
      // third parameter is the destination image name
      try {
        int amount = Integer.parseInt(args[0]);
        String sourceImageName = args[1];
        String destImageName = args[2];
        model.brighten(sourceImageName, amount, destImageName);
        res = new ResultImpl(true, "Successfully brightened the image.");
      } catch (NumberFormatException e) {
        res = new ResultImpl(false, "Failed to convert amount to an integer");
      } catch (IllegalArgumentException e) {
        res = new ResultImpl(false, "Unable to brighten the image: " + e.getMessage());
      }
      return res;
    }

    @Override
    public String help() {
      return this.helpMessage;
    }
  }

  /**
   * This class represents a command to Dither an image. This class is responsible for validating
   * parameters required for dither command and calling the appropriate methods in the model.
   */
  static class Dither implements Command {

    private final String helpMessage;

    /**
     * Constructs a dither command object and initializes the help message.
     */
    Dither() {
      this.helpMessage = "dither <image-name> <dest-image-name>\n"
          + "\t\tDither the image with image-name and store it with dest-image-name.";
    }

    @Override
    public Result execute(String[] args, Model model) {
      Result res;

      //  we need at least 2 arguments, the source image name and the destination image name
      if (args.length < 2) {
        return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
      }

      // parse parameters and run command
      // first parameter is the source image name
      // second parameter is the destination image name
      try {
        String sourceImageName = args[0];
        String destImageName = args[1];
        model.dither(sourceImageName, destImageName);
        res = new ResultImpl(true, "Successfully dithered the image.");
      } catch (IllegalArgumentException e) {
        res = new ResultImpl(false, "Unable to dither the image: "
            + e.getMessage());
      }
      return res;
    }

    @Override
    public String help() {
      return this.helpMessage;
    }
  }

  /**
   * This class represents a command to filter an image. This class is responsible for validating
   * parameters required for filter command and calling the appropriate methods in the model.
   */
  static class Filter implements Command {

    private final String helpMessage;
    private final String filterType;

    /**
     * Constructs a Filter command object and initializes the help message.
     *
     * @param filterType type of filter (blur, sharpen, etc.)
     */
    Filter(String filterType) {
      this.filterType = filterType;
      this.helpMessage = filterType + " <image-name> <dest-image-name>\n"
          + "\t\tApply " + filterType + " on the image with image-name and store it with "
          + "dest-image-name.";
    }

    @Override
    public Result execute(String[] args, Model model) {
      Result res;

      // we need at least 2 arguments, the source image name and the destination image name
      if (args.length < 2) {
        return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
      }

      // parse parameters and run command
      // first parameter is the source image name
      // second parameter is the destination image name
      try {
        String sourceImageName = args[0];
        String destImageName = args[1];
        model.filter(sourceImageName, this.filterType, destImageName);
        res = new ResultImpl(true, "Successfully applied " + this.filterType
            + " on the image");
      } catch (IllegalArgumentException e) {
        res = new ResultImpl(false, "Unable to apply " + this.filterType
            + " on the image" + e.getMessage());
      }
      return res;
    }

    @Override
    public String help() {
      return this.helpMessage;
    }
  }

  /**
   * This class represents a command to convert an image to greyscale. This class is responsible for
   * validating parameters required for greyscale command and calling the appropriate methods in the
   * model.
   */
  static class Greyscale implements Command {

    private final String helpMessage;

    /**
     * Constructs a greyscale command object and intializes the help message.
     */
    public Greyscale() {
      this.helpMessage = "greyscale <component> <image-name> <dest-image-name>\n"
          + "\t\tConvert the image with image-name to greyscale and store it with "
          + "dest-image-name using the component.\n"
          + "\t\tComponent can be one of the following: "
          + "red-component, green-component, blue-component, "
          + "value-component, luma-component, intensity-component";
    }

    @Override
    public Result execute(String[] args, Model model) {
      Result res;
      // we need at least 3 parameters, the component, the source image name
      // and the destination image
      if (args.length < 3) {
        return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
      }

      // parse parameters and run command
      // first parameter is the component
      // second parameter is the source image name
      // third parameter is the destination image name
      try {
        String component = args[0];
        String sourceImageName = args[1];
        String destImageName = args[2];
        model.greyscale(sourceImageName, component, destImageName);
        res = new ResultImpl(true, "Successfully obtained grayscaled image");
      } catch (IllegalArgumentException e) {
        res = new ResultImpl(false, "Unable to obtain grayscaled image: "
            + e.getMessage());
      }
      return res;
    }

    @Override
    public String help() {
      return this.helpMessage;
    }
  }

  /**
   * This class represents a command to flip an image horizontally. This class is responsible for
   * validating parameters required for horizontal flip command and calling the appropriate methods
   * in the model.
   */
  static class HorizontalFlip implements Command {

    private final String helpMessage;

    /**
     * Constructs a horizontal flip command object and intializes the help message.
     */
    HorizontalFlip() {
      this.helpMessage = "horizontal-flip <image-name> <dest-image-name>\n"
          + "\t\tFlip the image with image-name horizontally"
          + "and store it with dest-image-name.";
    }

    @Override
    public Result execute(String[] args, Model model) {
      Result res;

      // we need at least 2 arguments, source image name and destination image name
      if (args.length < 2) {
        return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
      }

      // parse parameters and run command
      // first parameter is the source image name
      // second parameter is the destination image name
      try {
        String sourceImageName = args[0];
        String destImageName = args[1];
        model.horizontalFlip(sourceImageName, destImageName);
        res = new ResultImpl(true, "Successfully flipped the image horizontally");
      } catch (IllegalArgumentException e) {
        res = new ResultImpl(false, "Unable to flip the image horizontally: "
            + e.getMessage());
      }
      return res;
    }

    @Override
    public String help() {
      return this.helpMessage;
    }
  }


  /**
   * This class represents a command to combine 3 greyscale images into a single RGB image. This
   * class is responsible for validating parameters required for rgb-combine command and calling the
   * appropriate methods in the model.
   */
  static class RGBCombine implements Command {

    private final String helpMessage;

    /**
     * Constructs a RGBCombine command object and intializes the help message.
     */
    RGBCombine() {
      this.helpMessage = "rgb-combine <dest-image-name> <red-image-name> <green-image-name> "
          + "<blue-image-name>\n"
          + "\t\tCombine the 3 greyscale images with red-image-name, green-image-name and "
          + "blue-image-name into a single image and store it with dest-image-name.";
    }

    @Override
    public Result execute(String[] args, Model model) {
      Result res;
      // we need at least 4 arguments, destination image name, red image name, green image name and
      // blue image name
      if (args.length < 4) {
        return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
      }

      // parse parameters and run command
      // first parameter is the destination image name
      // second parameter is the source red image name
      // third parameter is the source green image name
      // fourth parameter is the source blue image name
      try {
        String destImageName = args[0];
        String redImageName = args[1];
        String greenImageName = args[2];
        String blueImageName = args[3];
        model.rgbCombine(redImageName, greenImageName, blueImageName, destImageName);
        res = new ResultImpl(true, "Successfully generated RGB Combined image");
      } catch (IllegalArgumentException e) {
        res = new ResultImpl(false, "Unable to generate RGB Combined image: "
            + e.getMessage());
      }
      return res;
    }

    @Override
    public String help() {
      return this.helpMessage;
    }
  }


  /**
   * This class represents a command to split an image into 3 greyscale images. This class is
   * responsible for validating parameters required for rgb-split command and calling the
   * appropriate methods in the model.
   */
  static class RGBSplit implements Command {

    private final String helpMessage;

    /**
     * Constructs a RGBSplit command object and intializes the help message.
     */
    RGBSplit() {
      this.helpMessage = "rgb-split <image-name> <red-image-name> <green-image-name> "
          + " <blue-image-name>\n"
          + "\t\tSplit the image with image-name into 3 greyscale images"
          + "and store them with red-image-name, green-image-name and blue-image-name.";
    }

    @Override
    public Result execute(String[] args, Model model) {
      Result res;
      // we need at least 4 arguments, source image name and destination image names for red, green
      // and blue channel images
      if (args.length < 4) {
        return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
      }

      // parse parameters and run command
      // first parameter is the source image name
      // second parameter is the destination image name for red channel
      // third parameter is the destination image name for green channel
      // fourth parameter is the destination image name for blue channel
      try {
        String sourceImageName = args[0];
        String redImageName = args[1];
        String greenImageName = args[2];
        String blueImageName = args[3];
        model.rgbSplit(sourceImageName, redImageName, greenImageName, blueImageName);
        res = new ResultImpl(true, "Successfully rgb-split the image");
      } catch (IllegalArgumentException e) {
        res = new ResultImpl(false, "Unable to rgb-split the image: "
            + e.getMessage());
      }
      return res;
    }

    @Override
    public String help() {
      return this.helpMessage;
    }
  }


  /**
   * This class represents a command to flip an image vertically. This class is responsible for
   * validating parameters required for vertical flip command and calling the appropriate methods in
   * the model.
   */
  static class VerticalFLip implements Command {

    private final String helpMessage;

    /**
     * Constructs a vertical flip command object and intializes the help message.
     */
    VerticalFLip() {
      this.helpMessage = "vertical-flip <image-name> <dest-image-name>\n"
          + "\t\tFlip the image with image-name vertically"
          + "and store it with dest-image-name.";
    }

    @Override
    public Result execute(String[] args, Model model) throws IllegalArgumentException {
      Result res;
      // we need at least 2 arguments, source image name and destination image name
      if (args.length < 2) {
        return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
      }

      // parse parameters and run command
      // first parameter is the source image name
      // second parameter is the destination image name
      try {
        String sourceImageName = args[0];
        String destImageName = args[1];
        model.verticalFlip(sourceImageName, destImageName);
        res = new ResultImpl(true, "Successfully flipped the image vertically");
      } catch (IllegalArgumentException e) {
        res = new ResultImpl(false, "Unable to flip the image vertically: "
            + e.getMessage());
      }
      return res;
    }

    @Override
    public String help() {
      return this.helpMessage;
    }
  }

  /**
   * This class represents a command to transform an image. This class is responsible for validating
   * parameters required for transform command and calling the appropriate methods in the model.
   */
  static class Transform implements Command {

    private final String helpMessage;
    private final String transformType;

    /**
     * Constructs a transform command object and initializes the help message.
     *
     * @param transformType type of transform (grayscale, sepia etc.)
     */
    Transform(String transformType) {
      this.transformType = transformType;
      this.helpMessage = transformType + " <image-name> <dest-image-name>\n"
          + "\t\tApply " + transformType + " on the image with image-name and store it with "
          + "dest-image-name.";
    }

    @Override
    public Result execute(String[] args, Model model) {
      Result res;
      // we need atleast 2 arguments, source image name and destination image name
      if (args.length < 2) {
        return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
      }

      // parse parameters and run command
      // first parameter is the source image name
      // second parameter is the destination image name
      try {
        String sourceImageName = args[0];
        String destImageName = args[1];
        model.transform(sourceImageName, this.transformType, destImageName);
        res = new ResultImpl(true, "Successfully applied " + this.transformType
            + " on the image");
      } catch (IllegalArgumentException e) {
        res = new ResultImpl(false, "Unable to apply " + this.transformType
            + " on the image" + e.getMessage());
      }
      return res;
    }

    @Override
    public String help() {
      return this.helpMessage;
    }
  }
}













