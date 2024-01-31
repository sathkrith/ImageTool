package textgimp.guiview.mainwindow.features;

import static textgimp.guiview.mainwindow.Theme.setPanelColors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import textgimp.control.Features;
import textgimp.guiview.mainwindow.MainWindow;
import textgimp.guiview.mainwindow.Theme;
import textgimp.guiview.mainwindow.status.StatusPanel;
import textgimp.utility.Result;
import textgimp.utility.ResultImpl;

/**
 * This class implements the feature panel of the application. It contains buttons for file
 * operations, image operations, and image filters.
 */
public class GFeaturesPane extends JPanel {

  private final Features featureControl;

  private final StatusPanel statusPanel;
  private final Map<String, ImageOperation> operations;
  private JPanel bufferPanel;
  private JLabel bufferLabel;
  private boolean isImageLoaded;
  private ImageOperation currentOperation;
  private MainWindow parent;

  /**
   * Initialize the feature panel with feature handler. Set up the buttons and action listeners for
   * file operations, image operations, and image filters.
   *
   * @param featureControl feature handler for callbacks
   * @param statusPanel    status panel for showing status
   * @param parent         main window for clearing data
   */
  public GFeaturesPane(Features featureControl, StatusPanel statusPanel, MainWindow parent) {
    super();
    this.featureControl = featureControl;
    this.statusPanel = statusPanel;
    this.operations = new HashMap<>();
    this.parent = parent;

    // set layout
    LayoutManager layoutManager = new GridBagLayout();
    setLayout(layoutManager);

    // setup panels
    setupFileOperations();
    loadImageOperations();
    setupImageOperations();
    setupBufferPanel();
    setupApply();
    setPanelColors(this);
  }

  /**
   * Fetch the constraints for the feature panel layout.
   *
   * @return constraints object for the panel
   */
  private GridBagConstraints getConstraints() {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = GridBagConstraints.RELATIVE;
    constraints.weightx = 1;
    constraints.weighty = 0;
    constraints.fill = GridBagConstraints.NONE;
    constraints.anchor = GridBagConstraints.WEST;
    return constraints;
  }

  /**
   * Set up the file operations panel with buttons and actions.
   */
  private void setupFileOperations() {
    GridBagConstraints constraints = getConstraints();
    constraints.anchor = GridBagConstraints.NORTHWEST;

    // file operations title at the top
    JLabel fileOperationsLabel = new JLabel("File Operations");
    Theme.setLabelColors(fileOperationsLabel);
    fileOperationsLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
    add(fileOperationsLabel, constraints, 0);
    JPanel fileOperationsPanel = new JPanel();
    Theme.setPanelColors(fileOperationsPanel);

    // load button
    JPanel fileOperationsButtonsPanel = new JPanel();
    fileOperationsButtonsPanel.setLayout(new FlowLayout());
    Theme.setPanelColors(fileOperationsButtonsPanel);
    JButton loadButton = new JButton("Load");
    Theme.setButtonColors(loadButton);
    loadButton.addActionListener((e) -> {
      loadFileHandler();
    });
    fileOperationsButtonsPanel.add(loadButton);

    // save button
    JButton saveButton = new JButton("Save");
    saveButton.addActionListener((e) -> {
      saveFileHandler();
    });
    Theme.setButtonColors(saveButton);
    fileOperationsButtonsPanel.add(saveButton);

    // reset button
    JButton resetButton = new JButton("Reset");
    resetButton.addActionListener((e) -> {
      resetHandler();
    });
    Theme.setButtonColors(resetButton);
    fileOperationsButtonsPanel.add(resetButton);

    fileOperationsPanel.add(fileOperationsButtonsPanel, BorderLayout.CENTER);
    constraints.anchor = GridBagConstraints.CENTER;
    add(fileOperationsPanel, constraints, 1);
  }

  /**
   * Set up the apply button and action. Apply button is clicked for any image operation.
   */
  private void setupApply() {
    // create an apply button to perform operations
    JPanel applyPanel = new JPanel();
    Theme.setPanelColors(applyPanel);
    applyPanel.setLayout(new BoxLayout(applyPanel, BoxLayout.Y_AXIS));
    JButton applyButton = new JButton("Apply");
    Theme.setButtonColors(applyButton);
    JPanel applyButtonWrapper = new JPanel(new FlowLayout());
    Theme.setPanelColors(applyButtonWrapper);
    applyButton.setPreferredSize(new Dimension(75, 30));

    // validate the state and perform the operations
    applyButton.addActionListener((e) -> {
      if (validateState()) {
        currentOperation.run();
      }
    });
    applyButtonWrapper.add(applyButton);
    applyPanel.add(applyButtonWrapper);

    // create a label help text below the apply button
    bufferLabel = new JLabel("Click apply to perform the operation");
    Theme.setLabelColors(bufferLabel);
    bufferLabel.setFont(Theme.getSecondaryFont());
    bufferLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
    applyPanel.add(bufferLabel);
    GridBagConstraints constraints = getConstraints();
    constraints.anchor = GridBagConstraints.CENTER;
    add(applyPanel, constraints, 5);
  }

  /**
   * Set up the buffer panel for displaying the additional options for the image operations. This
   * panel is empty by default, updated whenever an operation needs additional data.
   */
  private void setupBufferPanel() {
    GridBagConstraints constraints = getConstraints();
    constraints.gridheight = 3;
    constraints.weighty = 1;
    this.bufferPanel = new JPanel();
    Theme.setPanelColors(bufferPanel);
    this.bufferPanel.setLayout(new FlowLayout());
    constraints.gridy = 4;
    constraints.anchor = GridBagConstraints.CENTER;
    add(bufferPanel, constraints, 4);
  }

  /**
   * Set up the image operations menu and actions.
   */
  private void setupImageOperations() {
    // label for image operations
    GridBagConstraints constraints = getConstraints();
    JLabel imageOperationsLabel = new JLabel("Image Operations");
    Theme.setLabelColors(imageOperationsLabel);
    imageOperationsLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
    JPanel imageOperations = new JPanel();
    Theme.setPanelColors(imageOperations);
    imageOperations.setLayout(new BorderLayout());
    add(imageOperationsLabel, constraints, 2);

    JPanel imageOperationsButtons = new JPanel();
    Theme.setPanelColors(imageOperationsButtons);
    imageOperationsButtons.setLayout(new FlowLayout());

    // create a dropdown menu for image operations
    JComboBox<Object> imageOperationsMenu = new JComboBox<>(this.operations.keySet().toArray());
    imageOperationsMenu.setForeground(Theme.getSecondaryColor());
    imageOperationsMenu.setFont(Theme.getSecondaryFont());
    imageOperationsMenu.addItemListener((e) -> {
      String operation = (String) imageOperationsMenu.getSelectedItem();
      comboBoxHandler(operation);
    });
    imageOperationsButtons.add(imageOperationsMenu);
    imageOperations.add(imageOperationsButtons, BorderLayout.CENTER);
    currentOperation = operations.get((String) imageOperationsMenu.getSelectedItem());
    constraints.anchor = GridBagConstraints.CENTER;
    add(imageOperations, constraints, 3);
  }

  /**
   * Load file button handler. Shows a file picker and loads the image.
   */
  private void loadFileHandler() {
    boolean continueLoad = true;
    // if an image is currently loaded, warn the user
    if (isImageLoaded) {
      continueLoad = resetWarning();
    }

    // if the user does not want to continue, return
    if (!continueLoad) {
      return;
    }

    // load file into model
    String filePath = this.genericFileHandler("open");
    Result res = featureControl.load(filePath);

    if (res.isSuccess()) {
      isImageLoaded = true;
    }
    this.statusPanel.setStatus("Load file", res);
  }

  /**
   * Save file button handler. Shows a file picker and saves the image.
   */
  private void saveFileHandler() {
    // if no image is loaded, return
    if (!validateState()) {
      return;
    }

    // save file into model
    String filePath = this.genericFileHandler("save");
    Result res = featureControl.save(filePath);
    this.statusPanel.setStatus("Save file", res);
  }

  /**
   * Reset the application to its initial state. Clear the image and histogram.
   */
  private void resetHandler() {
    // throw a warning to the user before reset
    boolean reset = resetWarning();
    if (reset) {
      this.parent.clearImage();
      isImageLoaded = false;
    }
  }

  /**
   * Validate the state of the application - check if the image is loaded.
   *
   * @return true if the image is loaded, false otherwise
   */
  private boolean validateState() {
    Result res = new ResultImpl(false, "No image loaded");
    if (!isImageLoaded) {
      this.statusPanel.setStatus("Error", res);
      return false;
    }
    return true;
  }

  /**
   * Show a warning to the user when resetting the state.
   *
   * @return true if the user wants to continue, false otherwise
   */
  private boolean resetWarning() {
    int dialogResult = JOptionPane.showConfirmDialog(this,
        "This will discard the currently loaded image. Do you wish to continue?",
        "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    return dialogResult == JOptionPane.YES_OPTION;
  }

  /**
   * Combo box action handler. Whenever an operation is selected, update the buffer panel to display
   * the additional options for that operation.
   *
   * @param operation a string representing the operation
   */
  private void comboBoxHandler(String operation) {
    this.bufferLabel.setText("Click apply to perform " + operation);
    this.remove(4);

    // fetch the operation from the map and fetch the support panel
    this.bufferPanel = operations.get(operation).getSupportPanel();

    // repaint the panels
    this.bufferPanel.revalidate();
    this.bufferPanel.repaint();
    this.revalidate();
    this.repaint();
    GridBagConstraints constraints = getConstraints();
    constraints.gridheight = 3;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.anchor = GridBagConstraints.CENTER;
    this.add(bufferPanel, constraints, 4);

    // update the current operation
    this.currentOperation = operations.get(operation);
  }


  /**
   * Generic file picker pop-up for loading and saving file.
   *
   * @param loadType String representing load type - open or save
   * @return chosen file path
   */
  private String genericFileHandler(String loadType) {
    // open a file picker and fetch the file path
    JFileChooser fileChooser = new JFileChooser();
    int returnVal = -1;

    if (loadType.equals("open")) {
      returnVal = fileChooser.showOpenDialog(this);
    } else if (loadType.equals("save")) {
      returnVal = fileChooser.showSaveDialog(this);
    }
    String filePath = "";

    // if no file selected
    if (returnVal != JFileChooser.APPROVE_OPTION) {
      return "";
    }

    // return file path
    filePath = fileChooser.getSelectedFile().getAbsolutePath();
    return filePath;
  }

  /**
   * List of image operations and their respective event handlers.
   */
  private void loadImageOperations() {
    this.operations.put("Blur", new AllOperations.Blur(statusPanel, featureControl));
    this.operations.put("Brighten", new AllOperations.Brighten(statusPanel, featureControl));
    this.operations.put("Dither", new AllOperations.Dither(statusPanel, featureControl));
    this.operations.put("Greyscale Blue",
        new AllOperations.GreyscaleBlue(statusPanel, featureControl));
    this.operations.put("Greyscale Green",
        new AllOperations.GreyscaleGreen(statusPanel, featureControl));
    this.operations.put("Greyscale Red",
        new AllOperations.GreyscaleRed(statusPanel, featureControl));
    this.operations.put("Greyscale Intensity", new AllOperations.GreyscaleIntensity(statusPanel,
        featureControl));
    this.operations.put("Greyscale Luma",
        new AllOperations.GreyscaleLuma(statusPanel, featureControl));
    this.operations.put("Greyscale Value", new AllOperations.GreyscaleValue(statusPanel,
        featureControl));
    this.operations.put("Horizontal Flip", new AllOperations.HorizontalFlip(statusPanel,
        featureControl));
    this.operations.put("Vertical Flip",
        new AllOperations.VerticalFlip(statusPanel, featureControl));
    this.operations.put("RGB Combine", new AllOperations.RGBCombine(statusPanel, featureControl));
    this.operations.put("RGB Split", new AllOperations.RGBSplit(statusPanel, featureControl));
    this.operations.put("Sharpen", new AllOperations.Sharpen(statusPanel, featureControl));
    this.operations.put("Sepia", new AllOperations.Sepia(statusPanel, featureControl));
  }
}
