package textgimp.control.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import textgimp.model.Model;
import textgimp.utility.Result;
import textgimp.utility.ResultImpl;

/**
 * This class represents a command to save an image to a file. This class is responsible for writing
 * data obtained from the model to the file.
 */
class SaveFile implements Command {

  private final String helpMessage;

  /**
   * Constructs a save file command object and initializes the help message.
   */
  SaveFile() {
    this.helpMessage = "save <image-path> <image-name>\n"
        + "\t\tSave the image with image-name to image-path.";
  }

  @Override
  public Result execute(String[] args, Model model) {
    Result res;
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

    try {
      // create file if it doesn't exist
      if (!f.exists()) {
        if (!f.createNewFile()) {
          return new ResultImpl(false, "Failed to create file");
        }
      }

      // extract image type from file name
      String path = f.getPath();
      String imageType = path.substring(path.lastIndexOf('.') + 1);

      // obtain image data and save to file
      byte[] saveContent = model.save(imageName, imageType);
      try (FileOutputStream fos = new FileOutputStream(f)) {
        fos.write(saveContent);
      }
      res = new ResultImpl(true, "Successfully saved file");
    } catch (IOException e) {
      deleteFile(f);
      res = new ResultImpl(false, "Save file failed: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      deleteFile(f);
      res = new ResultImpl(false, "Unable to save file: " + e.getMessage());
    }
    return res;
  }

  @Override
  public String help() {
    return this.helpMessage;
  }

  /**
   * Deletes the file if it exists. Used for error handling.
   *
   * @param f the file to delete
   */
  private void deleteFile(File f) {
    if (f.exists()) {
      f.delete();
    }
  }
}
