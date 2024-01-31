import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.UnsupportedLookAndFeelException;
import textgimp.control.Controller;
import textgimp.control.GUIGimpController;
import textgimp.control.TextGimpController;
import textgimp.guiview.GUIView;
import textgimp.guiview.SwingGUI;
import textgimp.model.GuiGimpReadModel;
import textgimp.model.Model;
import textgimp.model.ReadOnlyModel;
import textgimp.model.TextGimpModel;
import textgimp.view.TextGimpView;
import textgimp.view.TextView;

/**
 * Entry point to Image manipulation application. This class is responsible for handling arguments
 * passed to the application and running the application.
 */
public class ImageManager {

  private static String scriptFilePath; // Path to script file.
  private static RunMode GimpRunMode; // True if controller runs on script.
  private static InputStream input; // Input stream for the application.
  private static OutputStream output; // OutputStream for the application.

  /**
   * Entry point for the Text Gimp Application.
   *
   * @param args list of arguments for Text Gimp.
   */
  public static void main(String[] args) {
    try {
      // read arguments
      handleArgs(args);

      // define input stream
      if (GimpRunMode == RunMode.SCRIPT) {
        input = getStreamFromScript(scriptFilePath);
      } else if (GimpRunMode == RunMode.TEXT) {
        input = System.in;
      }

      System.out.println("Running TextGimp");

      // run the application in Text or GUI mode
      if (GimpRunMode != RunMode.GUI) {
        runTextGimp();
      } else {
        runGUIGimp();
      }

    } catch (FileNotFoundException e) {
      System.err.println("failed to open script file:" + e.getMessage());
    }  catch (UnsupportedLookAndFeelException | IllegalAccessException
              | InstantiationException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Handle arguments passed to the application.
   *
   * @param args arguments passed to Manager.
   */
  private static void handleArgs(String[] args) {
    int i = 0;
    output = System.out;
    GimpRunMode = RunMode.GUI;
    // handle arguments
    while (i < args.length) {
      switch (args[i]) {

        case "-help":
          displayHelp();
          break;

        case "-file":
          handleScript(i++, args);
          break;

        case "-text":
          GimpRunMode = RunMode.TEXT;
          break;

        case "-output":
          handleOutput(i++, args);
          break;

        default:
          System.out.println("Unsupported argument");
      }
      i++;
    }
  }

  /**
   * Obtain file input stream from given file path.
   *
   * @param filePath path to the file to be opened.
   * @return InputStream of the file opened.
   * @throws FileNotFoundException when file is not found.
   */
  private static InputStream getStreamFromScript(String filePath)
      throws FileNotFoundException {
    return new FileInputStream(filePath);
  }

  /**
   * Runs Text Gimp Application in interactive text mode.
   */
  private static void runTextGimp() {
    Model model = new TextGimpModel();
    TextView view = new TextGimpView(input, output);
    Controller controller = new TextGimpController(model, view);
    controller.run();
  }

  /**
   * Runs the application with GUI.
   */
  private static void runGUIGimp() throws UnsupportedLookAndFeelException,
          ClassNotFoundException, InstantiationException, IllegalAccessException {
    Model model = new TextGimpModel();
    ReadOnlyModel readOnlyModel = new GuiGimpReadModel(model);
    GUIView view = new SwingGUI(readOnlyModel);
    GUIGimpController controller = new GUIGimpController(model, view);
    view.addFeatureHandler(controller.getFeature());
    controller.run();
  }

  /**
   * Display help message to the user. Used for displaying help for command arguments.
   */
  private static void displayHelp() {
    System.out.println("Following arguments are supported:");
    System.out.println("-help: display this help");
    System.out.println("-text: run the program in interactive text mode");
    System.out.println("-file: run scripts from a .tg file, must be "
        + "followed by file path. By default, TextGimp is interactive");
    System.out.println("-output: redirect output of TextGimp to this file");
  }

  /**
   * Handle the -file argument, Obtain the script path and run the application in script mode.
   *
   * @param i    index of the argument
   * @param args arguments passed to Manager
   */
  private static void handleScript(int i, String[] args) {
    if (i + 1 < args.length) {
      scriptFilePath = args[++i];
      GimpRunMode = RunMode.SCRIPT;
    } else {
      System.err.println("-file must be followed with a path to a valid .tg file");
    }
  }

  /**
   * Handle the -output argument, Obtain the output path and set the output stream.
   *
   * @param i    index of the argument
   * @param args arguments passed to Manager
   */
  private static void handleOutput(int i, String[] args) {
    if (i + 1 < args.length) {
      try {
        createFileOutputStream(args[++i]);
      } catch (IOException e) {
        System.err.println("Failed to set output to file:" + e.getMessage());
      }
    } else {
      System.err.println("-output must be followed with a path to a valid file");
    }
  }

  /**
   * Create a new file output stream.
   *
   * @param path path to the file to be created.
   * @throws IOException when file cannot be created.
   */
  private static void createFileOutputStream(String path) throws IOException {
    File f = new File(path);

    // create file if it does not exist
    if (!f.exists()) {
      if (!f.createNewFile()) {
        throw new IOException("Failed to create Output file");
      }
    }

    // truncate the file if it exists
    FileWriter fw = new FileWriter(f);
    fw.write("");
    fw.close();

    // create output stream
    output = new FileOutputStream(f);
  }

  /**
   * Run modes for the application.
   */
  private enum RunMode {
    TEXT, SCRIPT, GUI
  }
}
