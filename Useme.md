### GUI Operations
- The GUI has 5 panels in the main frame: [Click to view screenshot](res/panel_reference.png)
  - There are 2 panels in the left:
    - **Status panel**: This panel shows the status of the last performed operation.
    - **Image panel**: This panel displays the image that is currently loaded in the application.
  - There are 3 panels in the right:
    - **File operations panel**: This panel has 3 buttons.
      - Load: This button opens a file chooser dialog to select an image file to load.
      - Save: This button opens a file chooser dialog to select a file to save the image to.
      - Reset: This button resets the application to its initial state.
    - **Image operation panel**
      - This panel has a dropdown menu with all supported operations.
      - This panel also has an `Apply` button to apply the selected operation.
    - **Histogram Panel**: This panel displays the histogram of the image that is currently loaded in the application.
- **Loading an image**
  - The user needs to load an image before performing any operation.
  - The user can load an image by clicking the `Load` button in the file operations panel.
  - It will open a File Chooser dialog to select an image file.
  - Once the image is loaded, it will be displayed in the image panel.
- **Saving an image**
  - An image needs to be loaded first before it can be saved.
  - The user can save the image by clicking the `Save` button in the file operations panel.
  - It will open a File Chooser dialog to select a file to save the image to.
- **Performing an operation**
  - An image needs to be loaded first before an operation can be performed.
  - Operations are available in the Dropdown menu in the image operation panel.
  - The user can select an operation from the dropdown menu.
  - If the operation needs any additional input, it will be shown in the input panel.
  - The user can click the `Apply` button to apply the selected operation.
  - The operation will be applied and the resulting image will be displayed in the image panel.
- In case of errors, the program throws an Error pop-up dialog with the error message.
- Error and success messages are also shown in the status panel.

### Panel reference
View the image below for a reference of the panels in the GUI.
![Image panels](res/panel_reference.png)

### Supported commands
**All commands except load expect an image that is already loaded in the application.
Therefore, the `load` command must be run first.**

The program supports the following operations:
- Load an image from a file : `load image-path image-name`
- Save an image to a file: `save image-path image-name`
- Store the loaded images in-memory and perform operations
- Brighten or Darken an image by certain amount: `brighten increment image-name  dest-image-name`
- Flip an image horizontally or vertically:
  - `vertical-flip image-name dest-image-name`
  - `horizontal-flip image-name dest-image-name`
- Split an image into its Red, Green, Blue components: `rgb-split image-name  red-image-name green-image-name blue-image-name`
- Combine the Red, Green, Blue components into a single image: `rgb-combine dest-image-name red-image-name green-image-name  blue-image-name`
- Obtain grey-scale image from a color image using the following methods:
  - Red component: `greyscale red-component image-name dest-image-name`
  - Green component: `greyscale green-component image-name dest-image-name`
  - Blue component: `greyscale blue-component image-name dest-image-name`
  - Value component (max of red, green, blue): : `greyscale value-component image-name dest-image-name`
  - Intensity component: `greyscale intensity-component image-name dest-image-name`
  - Luma component: `greyscale luma-component image-name dest-image-name`
- Blur an image: `blur image-name dest-image-name`
- Sharpen an image: `sharpen image-name dest-image-name`
- Dither an image: `dither image-name dest-image-name`
- Get Sepia tone of an image: `sepia image-name dest-image-name`
- Quit the application: `quit` or `q`
