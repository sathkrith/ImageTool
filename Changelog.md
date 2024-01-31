## Changelog

### Model interface
- Create a new Read-only model interface with two read methods.
  - One for fetching the histogram of an image.
  - One for fetching the image to show on the GUI.
- Existing Model interface extends the new Read-only model interface.

### Model implementation
- Implement the two methods added to read-only model.
  - Histogram generation.
  - Method to get the image to show on the GUI.

### GUI Controller and Features
- Implement a new controller that handles the GUI and provides a features object.
- Implement a `Features` class - that provides callbacks for user input on the GUI.
- Features implementation uses existing command objects to delegate the operations.

### GUI View
- Implement a new view that shows a graphical user interface for the application.
- Uses the feature controller to handle actions performed on the GUI.
- Uses read-only model to fetch the image and histogram to show on the GUI.