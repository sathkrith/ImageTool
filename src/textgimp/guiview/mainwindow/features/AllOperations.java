package textgimp.guiview.mainwindow.features;

import static textgimp.guiview.mainwindow.Theme.setPanelColors;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import textgimp.control.Features;
import textgimp.guiview.mainwindow.Theme;
import textgimp.guiview.mainwindow.status.StatusPanel;
import textgimp.utility.Result;
import textgimp.utility.ResultImpl;

/**
 * This class represents a group of all operations. Multiple classes have been moved to this file to
 * remain under the file limit.
 */
public class AllOperations {

  /**
   * This class represents an image operation on the GUI. It contains a method to perform the
   * operation and a method to get the support panel. Support panel is used to obtain additional
   * information from the user.
   */
  static abstract class AbstractOperation implements ImageOperation {

    protected final StatusPanel statusPanel;

    protected final Features features;

    protected JPanel supportPanel;

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    AbstractOperation(StatusPanel statusPanel, Features features) {
      this.statusPanel = statusPanel;
      this.features = features;
      this.supportPanel = new JPanel();
      setPanelColors(supportPanel);
    }

    @Override
    public JPanel getSupportPanel() {
      return supportPanel;
    }
  }

  /**
   * This class represents the callback for Brighten operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class Brighten extends AbstractOperation {

    private JSlider slider;

    private int brightness;

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    Brighten(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
      brightness = 0;
      this.setupBrightenPanel();
    }

    /**
     * Set up the buffer panel for brighten operation with a slider.
     */
    private void setupBrightenPanel() {
      // add a slider to the buffer panel to set the brightness
      // then call featureControl.brighten() with the brightness value
      slider = new JSlider(JSlider.HORIZONTAL, -255, 255, 0);
      slider.setBackground(Theme.getPrimaryColor());
      slider.setForeground(Theme.getSecondaryColor());
      slider.setMajorTickSpacing(255);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      JLabel label = new JLabel("Brightness: " + slider.getValue());
      Theme.setLabelColors(label);

      // create a label to display the current value of the slider
      slider.addChangeListener((e) -> {
        label.setText("Brightness: " + slider.getValue());
        brightness = slider.getValue();
      });
      JPanel wrapperPanel = new JPanel(new GridLayout(2, 1));
      Theme.setPanelColors(wrapperPanel);
      wrapperPanel.add(label);
      wrapperPanel.add(slider);
      this.supportPanel.add(wrapperPanel);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.brighten(brightness);
      this.statusPanel.setStatus("Brighten", res);
      this.slider.setValue(0);
      return res;
    }
  }

  /**
   * This class represents the callback for Blur operation on the GUI. It delegates the operation to
   * the feature controller.
   */
  static class Blur extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    Blur(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.filter("blur");
      this.statusPanel.setStatus("Blur", res);
      return res;
    }
  }

  /**
   * This class represents the callback for Sharpen operation on the GUI. It delegates the operation
   * to the feature controller.
   */
  static class Sharpen extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    Sharpen(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.filter("sharpen");
      this.statusPanel.setStatus("Sharpen", res);
      return res;
    }
  }

  /**
   * This class represents a callback for the HorizontalFlip operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class HorizontalFlip extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    HorizontalFlip(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.horizontalFlip();
      this.statusPanel.setStatus("Horizontal Flip", res);
      return res;
    }
  }


  /**
   * This class represents a callback for the VerticalFlip operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class VerticalFlip extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    VerticalFlip(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.verticalFlip();
      this.statusPanel.setStatus("Vertical Flip", res);
      return res;
    }
  }

  /**
   * This class represents a callback for the Sepia operation on the GUI. It delegates the operation
   * to the feature controller.
   */
  static class Sepia extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    Sepia(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.transform("sepia");
      this.statusPanel.setStatus("Sepia", res);
      return res;
    }
  }

  /**
   * This class represents a callback for the Greyscale Red operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class GreyscaleRed extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    GreyscaleRed(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.transform("greyscale-red-component");
      this.statusPanel.setStatus("Greyscale Red", res);
      return res;
    }
  }

  /**
   * This class represents a callback for the Greyscale Blue operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class GreyscaleBlue extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    GreyscaleBlue(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.transform("greyscale-blue-component");
      this.statusPanel.setStatus("Greyscale Blue", res);
      return res;
    }
  }

  /**
   * This class represents a callback for the Greyscale Green operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class GreyscaleGreen extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    GreyscaleGreen(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.transform("greyscale-green-component");
      this.statusPanel.setStatus("Greyscale Green", res);
      return res;
    }
  }

  /**
   * This class represents a callback for the Greyscale Luma operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class GreyscaleLuma extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    GreyscaleLuma(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.transform("greyscale-luma-component");
      this.statusPanel.setStatus("Greyscale Luma", res);
      return res;
    }
  }

  /**
   * This class represents a callback for the Greyscale Intensity operation on the GUI. It delegates
   * the operation to the feature controller.
   */
  static class GreyscaleIntensity extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    GreyscaleIntensity(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.transform("greyscale-intensity-component");
      this.statusPanel.setStatus("Greyscale Intensity", res);
      return res;
    }
  }

  /**
   * This class represents a callback for the Greyscale Value operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class GreyscaleValue extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    GreyscaleValue(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.transform("greyscale-value-component");
      this.statusPanel.setStatus("Greyscale Value", res);
      return res;
    }
  }

  /**
   * This class represents a callback for the Dither operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class Dither extends AbstractOperation {

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    Dither(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
    }

    @Override
    public Result run() {
      // delegate the operation to the feature controller
      Result res = features.dither();
      this.statusPanel.setStatus("Dither", res);
      return res;
    }
  }

  /**
   * This class represents abstract class for RGB split and RGB combine operations. This creates the
   * buffer panels to load three images.
   */
  static abstract class RGBOperator extends AbstractOperation {

    protected final JTextField redPath;
    protected final JTextField greenPath;
    protected final JTextField bluePath;
    private final JPanel innerPanel;

    /**
     * Initialize the operation with status panel and features.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    RGBOperator(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
      innerPanel = new JPanel(new GridBagLayout());
      Theme.setPanelColors(innerPanel);

      // create three labels to show the image paths
      bluePath = new JTextField("Blue image file path");
      redPath = new JTextField("Red image file path");
      greenPath = new JTextField("Green image file path");
      supportPanel.setLayout(new FlowLayout());

      // setup image loaders
      setupLoader("Red image", redPath, 0);
      setupLoader("Green image", greenPath, 1);
      setupLoader("Blue image", bluePath, 2);
      supportPanel.add(innerPanel);
    }

    /**
     * Fetch constraints for the panel.
     *
     * @return constraints object
     */
    private GridBagConstraints createConstraints() {
      GridBagConstraints constraints = new GridBagConstraints();
      constraints.weighty = 1;
      constraints.weightx = 0;
      constraints.fill = GridBagConstraints.NONE;
      constraints.anchor = GridBagConstraints.WEST;
      constraints.insets = new Insets(5, 5, 5, 5);
      return constraints;
    }

    /**
     * Create file picker buttons and add action listeners.
     *
     * @param label         label for the button
     * @param fieldToUpdate text field to update
     * @return button
     */
    private JButton createSubOperationButton(String label, JTextField fieldToUpdate) {
      // create a new button
      JButton button = new JButton(label);
      Theme.setButtonColors(button);
      button.setPreferredSize(new Dimension(120, 25));

      // set action listener for the button
      button.addActionListener((e) -> {
        JFileChooser fileChooser = new JFileChooser();
        int retCode;

        // show file picker based on the operation type
        if (getOperationType() == FileOperationType.OPEN) {
          retCode = fileChooser.showOpenDialog(this.supportPanel);
        } else {
          retCode = fileChooser.showSaveDialog(this.supportPanel);
        }

        // if the user does not select a file, set the text field to "No File selected"
        if (retCode != JFileChooser.APPROVE_OPTION) {
          fieldToUpdate.setText("No File selected");
          fieldToUpdate.setToolTipText("No File selected");
        } else {
          fieldToUpdate.setText(fileChooser.getSelectedFile().getPath());
          fieldToUpdate.setToolTipText(fileChooser.getSelectedFile().getPath());
        }
      });
      return button;
    }

    /**
     * Set up the loader panel.
     *
     * @param buttonLabel label for the button
     * @param textArea    text field to update
     * @param index       index of the panel
     */
    private void setupLoader(String buttonLabel, JTextField textArea, int index) {
      // create constraints for the panel
      GridBagConstraints constraints = createConstraints();
      JPanel panel = new JPanel(new GridBagLayout());
      Theme.setPanelColors(panel);
      constraints.gridx = 0;

      // create buttons and add them to the panel
      panel.add(createSubOperationButton(buttonLabel, textArea), constraints, 0);
      constraints.gridx = 1;
      constraints.weightx = 1;
      textArea.setColumns(20);

      // set the position
      constraints.anchor = GridBagConstraints.EAST;
      panel.add(textArea, constraints, 1);
      constraints.gridy = index;
      innerPanel.add(panel, constraints, index);
    }

    /**
     * Validator for the image loaders in the panel. Validate whether all the image paths have been
     * chosen in the panel.
     *
     * @return result object
     */
    protected Result validate() {
      if (redPath.getText().isEmpty()) {
        return new ResultImpl(false, "Empty red image path");
      }
      if (greenPath.getText().isEmpty()) {
        return new ResultImpl(false, "Empty green image path");

      }
      if (bluePath.getText().isEmpty()) {
        return new ResultImpl(false, "Empty blue image path");
      }
      return new ResultImpl(true, "file paths are not empty");
    }

    abstract FileOperationType getOperationType();

    /**
     * Enum representing file operation type.
     */
    protected enum FileOperationType {
      SAVE, OPEN
    }
  }

  /**
   * This class represents a callback for the RGB split operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class RGBCombine extends RGBOperator {

    private final FileOperationType type;

    /**
     * Initialize the operation with status panel and features. Set the FileOperationType to OPEN.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    RGBCombine(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
      type = FileOperationType.OPEN;
    }

    @Override
    FileOperationType getOperationType() {
      return type;
    }

    @Override
    public Result run() {
      // validate the image paths
      Result res = validate();
      if (res.isSuccess()) {
        // delegate the operation to the feature controller
        res = features.rgbCombine(redPath.getText(), greenPath.getText(), bluePath.getText());
        this.statusPanel.setStatus("RGB combine", res);
      }
      return res;
    }
  }

  /**
   * This class represents a callback for the RGB split operation on the GUI. It delegates the
   * operation to the feature controller.
   */
  static class RGBSplit extends RGBOperator {

    private final FileOperationType type;

    /**
     * Initialize the operation with status panel and features. Set the FileOperationType to SAVE.
     *
     * @param statusPanel status panel from the UI
     * @param features    feature controller
     */
    RGBSplit(StatusPanel statusPanel, Features features) {
      super(statusPanel, features);
      type = FileOperationType.SAVE;
    }

    @Override
    FileOperationType getOperationType() {
      return type;
    }

    @Override
    public Result run() {
      // validate the image paths
      Result res = validate();

      if (res.isSuccess()) {
        // delegate the operation to the feature controller
        res = features.rgbSplit(redPath.getText(), greenPath.getText(),
            bluePath.getText());
        this.statusPanel.setStatus("RGB Split", res);
      }
      return res;
    }
  }
}
