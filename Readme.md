# TextGimp
This project implements a simple image processing program with a Text User Interface.

- Supported commands are provided in the [Useme.md](Useme.md)
- Changelog of the application is in the [Changelog.md](Changelog.md)

## Supported image types
The application supports the following image formats (extensions).
- PNG
- JPG
- BMP
- PPM

You can load the image in any format and save it to any other format as you wish.

## Program arguments
- Running the `GUIGimp.jar` file with no arguments will launch the GUI application.
  - Run `java -jar GUIGimp.jar` or `./GUIGimp.jar` with appropriate permissions
- The application supports the following arguments
  - `-file`: Runs the script file and exits.
    - `java -jar GUIGimp.jar -file script.tg` will run `script.tg` script and exit. The script
    file must have a `.tg` extension.
  - `-text`: Runs the program in interactive text mode.
    - `java -jar GUIGimp.jar -text` will run the program in interactive text mode.
  - `-output`: specify a log file where the output from ImageManager will be
    recorded.
    - `java -jar GUIGimp.jar -output pathToLog.txt` will record all output from ImageManager to `pathToLog.txt`.

## How to run using Jarfile
- Setup JDK 11 and check if `java` and `javac` commands are available in the terminal
- A jar executable of the application is provided in the `/res` folder - [Click to download](/res/GUIGimp.jar)
- Provide appropriate permissions to the downloaded jar file to use it as an executable.
- Run the jar file using `java -jar GUIGimp.jar`
- You can provide any command line arguments supported

## High Level MVC Structure
- All the code resides in the `/src` folder
- The program is implemented using the Model-View-Controller design pattern.
- ImageManager
  - `ImageManager.java` is the main class with entrypoint to the program.
  - This creates a TextGimpModel, TextGimpController and TextGimpView and connects them together.
  - `System.in` and `System.out` are passed to the TextGimpView to read and
    write commands if ImageManager is launched without any commands.
  - Passing `-file` at launch will run the program in script mode, using the passed script file as input source.
  - Passing `-output` at launch will make the program write all output to the passed file.
- TextGimpModel
  - `src/model/TextGimpModel.java` is the model class that stores the images in-memory and performs the image processing operations.
  - This implements the `Model` interface found in `src/model/Model.java`
- TextGimpController
  - `src/controller/TextGimpController.java` is the controller class that reads the commands from the view and calls the appropriate methods in the model.
  - This implements the `Controller` interface found in `src/controller/Controller.java`
- TextGimpView
  - `src/view/TextGimpView.java` is the view class that reads the commands from the user and writes the output to the user.
  - This implements the `View` interface found in `src/view/View.java`
- GUIGimpView
  - `src/guiview/SwingUI.java` is a GUI view representing the GUI application.
  - This implements the `GUIView` interface found in `src/guiview/GUIView.java`

## Packages
- `textgimp`: Top level package for the program found in `src/textgimp`
  - `control`: Package representing the controller for TextGimp program
    - `command`: This package contains all the commands supported by the program.
  - `model`: Package representing the models for TextGimp program
    - `betterimage`: This package contains all the image level and pixel level operations for supported image types.
    - `imagebuilder`: This package contains all the image builder classes for different image types.
    - `macro`: This package contains all the macro operations to transform an image.
  - `utility`: Package containing utility classes for the program.
  - `view`: Package representing the view for TextGimp program
  - `guiview`: Package representing the GUI view for TextGimp program

## Model
TextGimpModel is the single model used by the program, providing methods for all the operations.
This model uses ImageBuilder, Image and Pixel models internally to delegate the operations to the appropriate classes.
- ### TextGimpModel
  - TextGimpModel class in `src/model/TextGimpModel.java` implements the model interface.
  - Stores the images in-memory and performs the image processing operations.
  - It has a map of image name vs image object. This map is used to find the right object for an image name.
  - Also stores a map of ImageBuilder objects for each image type supported by TextGimp.
  - ImageBuilder objects are used to load and save images of a specific type.
  - Loading an Image:
    - User enters a command to load an image from a file.
    - Controller opens the file and reads the data.
    - Controller calls the loadImage method of TextGimpModel with the data.
    - TextGimpModel finds the appropriate ImageBuilder object for the image type.
    - ImageBuilder object is used to load the image from the file.
    - Image object is stored in the map of images.
  - Saving an Image:
    - User enters a command to save an image to a file.
    - Controller calls the saveImage method in TextGimpModel.
    - TextGimp model finds the image object for the image name.
    - TextGimpModel finds the appropriate ImageBuilder object for the image type.
    - For loading and saving images, it uses the ImageBuilders of the appropriate type.
  - User enters a command to perform an operation on the image.
    - Controller calls the appropriate method in TextGimpModel.
    - TextGimpModel finds the image object for the image name.
    - Delegate the operation to the image object.

- ### ImageBuilder
  - ImageBuilder interface is found in the `imagebuilder` package.
  - Common methods are written in the `AbstractImageBuilder` class found in the `imagebuilder` package.
  - For every type supported by TextGimp, a class implementing a builder is created in the `src/model/imagebuilder` folder.
  - Builders are used by TextGimpModel to load and save an object representing an image.
  - Encoding and decoding of the images is done by the ImageBuilder classes.

- ### Image
  - Image interface is found in `betterimage` package.
  - All image formats are stored by a single class `GenericImage`.
  - Each image stores width, height, max color value and a 2D array of pixels.

- ### Pixel
  - Pixel interface is found in `betterimage` package.
  - `RGBPixel` class found in the `betterimage` package implements the Pixel interface.
  - An RGBPixel stores red, green, blue and max color value of the pixel.
  - `PNGPixel` class extends the `RGBPixel` class to add an additional `transparency` attribute.

- ### Macro
  - A macro represents an operation that can be performed on an Image.
  - Every macro has an apply method with an image argument.
  - The apply method returns a new image with the operation that the macro
    represented applied.
  - Macros are broadly classified into two types:
    - Color Transformations: These transform an image at the pixel level.
      The new value of the pixel depends only upon the previous value of that pixel. Ex: greyscale operations, brighten etc.
    - Image Transformations: These represent more complex transformation
      where new values of pixel depend on values of other pixels. Ex: blur,
      flip etc.
  - To support a new operation, a new macro is created either in Color transformation package or in Image transformation package.

## Controller
- TextGimpController is the single controller used by the program.
- It handles user input/output, processing user input and deciding what to display to the user.
- It acts as a bridge between the model and the view.
- TextGimp controller implements the Controller interface found in `src/textgimp/control/Controller.java`
- Has a single public method `run` which takes control of the execution loop.

- ### TextGimpController
  - TextGimpController class in `control` package, implements the Controller interface.
  - Accepts a Model and View object in the constructor.
  - Receives user input from view, processes it, and runs the appropriate method found in Model interface.
  - Maintains map of Commands supported and Command object found in the `commnands` package.
  - This map is used to run appropriate command based on the user input.

- ### commands
  - This package contains all the commands supported by the program.
  - Command interface is found in `src/textgimp/control/commands/Command.java` file.
  - Each command object represents a command that can be run by the user.
  - Each command object contains an `execute` and `help` method to run the command and display help for the command.
  - They also take care of input validation and file I/O when necessary.

- ### GuiFeatures
  - `GuiFeatures` class represents a set of callbacks for the GUI view.
  - The callbacks internally delegate the operations to command objects found in the `commands` package.

## View
- TextGimpView is the text view used by the program.
  - It takes care of displaying output to the user and providing an input stream to the controller.
- GUIView is the GUI view used by the program.
  - It takes care of drawing a graphical user interface for the program.

## Image citation for sample images
- All the images available in the `/res` folder are created by me (Madhukara S Holla)
- I hereby authorize the use of uploaded images for educational purposes only.
