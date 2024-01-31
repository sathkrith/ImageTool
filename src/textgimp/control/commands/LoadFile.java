package textgimp.control.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import textgimp.model.Model;
import textgimp.utility.Result;
import textgimp.utility.ResultImpl;

/**
 * This class represents a command to load an image from a file. This class is responsible for
 * reading the data from the file and passing it to the model.
 */
class LoadFile implements Command {

  private final String helpMessage;

  /**
   * Constructs a load file command object and intializes the help message.
   */
  LoadFile() {
    this.helpMessage = "load <image-path> <image-name>\n"
        + "\t\tLoad an image from image-path and store it with image-name.";
  }

  @Override
  public Result execute(String[] args, Model model) {
    // we need at least 2 arguments, the file path and the image name
    if (args.length < 2) {
      return new ResultImpl(false, "Incorrect usage.\n" + this.helpMessage);
    }

    // if the image path contains spaces, the array will contain more than 2 elements
    // the last element will be the image name, rest of them will be parts of the file path
    // we need to combine them to get the complete file path
    if (args.length > 2) {
      args[0] =
          Arrays.stream(args).limit(args.length - 1).reduce((x, i) -> x + " " + i).get();
      args = new String[]{args[0], args[args.length - 1]};
    }

    // First element in the array is the file path
    File f = new File(args[0]);
    // Last element in the array is the image name
    String imageName = args[args.length - 1];
    Result res;

    // read the file and pass data to model
    try {
      if (!f.exists()) {
        return new ResultImpl(false, "Incorrect file path passed, "
            + "No such file found.");
      }
      byte[] fileContent = Files.readAllBytes(f.toPath());

      // extract the image type from the file name
      String name = f.getName();
      String imageType = name.substring(name.lastIndexOf('.') + 1);

      // load the image
      model.load(fileContent, imageName, imageType);
      res = new ResultImpl(true, "Successfully loaded the file");
    } catch (IllegalArgumentException e) {
      res = new ResultImpl(false, "Unable to load file: " + e.getMessage());
    } catch (IOException e) {
      res = new ResultImpl(false, "Failed to read file: " + e.getMessage());
    }
    return res;
  }

  @Override
  public String help() {
    return this.helpMessage;
  }
}
