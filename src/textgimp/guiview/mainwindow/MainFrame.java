package textgimp.guiview.mainwindow;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import textgimp.control.Features;
import textgimp.guiview.mainwindow.features.GFeaturesPane;
import textgimp.guiview.mainwindow.image.GImagePane;
import textgimp.guiview.mainwindow.image.ImageViewer;
import textgimp.guiview.mainwindow.imageinfo.GHistogramViewer;
import textgimp.guiview.mainwindow.status.GStatusPane;
import textgimp.guiview.mainwindow.status.StatusPanel;
import textgimp.model.ReadOnlyModel;
import textgimp.utility.Result;
import textgimp.utility.ResultImpl;

/**
 * This class implements the main window of the application. It contains the image, the status
 * panel, and the feature panel.
 */
public class MainFrame extends JFrame implements MainWindow {

  private final ReadOnlyModel readOnlyModel;
  private final Features features;
  private JPanel mainPanel;
  private ImageViewer imagePanel;
  private GHistogramViewer imageDetailPanels;
  private StatusPanel statusPane;

  /**
   * Initialize the main window with read only model and feature handler.
   *
   * @param rd       read only model.
   * @param features feature handler.
   */
  public MainFrame(ReadOnlyModel rd, Features features) {
    super();
    this.readOnlyModel = rd;
    this.features = features;
    setTitle("Swing GIMP");
    setMinimumSize(new Dimension(1050, 850));
    setUpPanels();
  }

  /**
   * Initialize different panels on the frame.
   */
  private void setUpPanels() {
    mainPanel = new JPanel();
    LayoutManager layout = new GridBagLayout();
    mainPanel.setLayout(layout);
    add(mainPanel);
    Theme.setPanelColors(mainPanel);
    setUpStatusPane(0);
    setUpImagePane(1);
    setUpFeaturesPane(2);
    setUpHistogramPanel(3);
  }

  /**
   * Initialize the image panel.
   *
   * @param idx index to the panel object in the frame.
   */
  private void setUpImagePane(int idx) {
    imagePanel = new GImagePane(new Dimension(625, 700));
    JScrollPane imageScrollPane = new JScrollPane((Component) imagePanel);
    imageScrollPane.setBackground(Theme.getPrimaryColor());
    imageScrollPane.setBorder(new BevelBorder(BevelBorder.RAISED));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.gridheight = 6;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.anchor = GridBagConstraints.WEST;
    mainPanel.add(imageScrollPane, constraints, idx);
  }

  /**
   * Initialize the histogram panel.
   *
   * @param idx index to the panel object in the frame.
   */
  private void setUpHistogramPanel(int idx) {
    imageDetailPanels = new GHistogramViewer(new Dimension(400, 400));
    imageDetailPanels.setPreferredSize(new Dimension(400, 400));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.NONE;
    constraints.gridheight = 4;
    constraints.gridx = 1;
    constraints.gridy = 5;
    constraints.anchor = GridBagConstraints.NORTH;
    mainPanel.add(imageDetailPanels, constraints, idx);
  }

  /**
   * Initialize the status panel.
   *
   * @param idx index to the panel object in the frame.
   */
  private void setUpStatusPane(int idx) {
    statusPane = new GStatusPane();
    ((JPanel) statusPane).setBorder(new BevelBorder(BevelBorder.RAISED));
    ((JPanel) statusPane).setPreferredSize(new Dimension(600, 100));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.gridheight = 1;
    constraints.weightx = 1;
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.WEST;
    statusPane.setStatus("Click Load button to load an image", null);
    mainPanel.add(((JPanel) statusPane), constraints, idx);
  }

  /**
   * Initialize the feature panel.
   *
   * @param idx index to the panel object in the frame.
   */
  private void setUpFeaturesPane(int idx) {
    JPanel featurePanels = new GFeaturesPane(features, statusPane, this);
    featurePanels.setBorder(new BevelBorder(BevelBorder.RAISED));
    featurePanels.setPreferredSize(new Dimension(400, 400));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridheight = 4;
    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.WEST;
    mainPanel.add(featurePanels, constraints, idx);
  }

  @Override
  public void displayImage(String name) {
    try {
      // fetch the image and display the image
      imagePanel.setImage(readOnlyModel.getBytesOfImage(name));

      // clear existing histogram
      imageDetailPanels.clearHistograms();

      // fetch red, green, blue and intensity histogram and display them
      imageDetailPanels.displayHistogram(
          readOnlyModel.getHistogramOfGreyscale(name, Histograms.RED.toString()), "red");
      imageDetailPanels.displayHistogram(
          readOnlyModel.getHistogramOfGreyscale(name, Histograms.GREEN.toString()), "green");
      imageDetailPanels.displayHistogram(
          readOnlyModel.getHistogramOfGreyscale(name, Histograms.BLUE.toString()), "blue");
      imageDetailPanels.displayHistogram(
          readOnlyModel.getHistogramOfGreyscale(name,
              Histograms.INTENSITY.toString()), "intensity");
    } catch (IOException e) {
      Result res = new ResultImpl(false, e.getMessage());
      statusPane.setStatus("View image", res);
    }
  }

  @Override
  public void clearImage() {
    imagePanel.clearImage();
    imageDetailPanels.clearHistograms();
    statusPane.setStatus("Click Load button to load an image", null);
  }

  /**
   * This enum is used to represent the different types of histogram.
   */
  private enum Histograms {
    RED("greyscale-red-component"),
    GREEN("greyscale-green-component"),
    BLUE("greyscale-blue-component"),

    INTENSITY("greyscale-intensity-component");

    private final String name;

    /**
     * Initialize the enum with the name of the histogram.
     *
     * @param value name of the histogram.
     */
    Histograms(String value) {
      this.name = value;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
