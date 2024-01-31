package textgimp.guiview.mainwindow.image;

import static textgimp.guiview.mainwindow.Theme.setPanelColors;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import textgimp.guiview.mainwindow.Theme;

/**
 * This class represents an image panel in the main window. It displays the image and provides
 * methods to clear the image.
 */
public class GImagePane extends JPanel implements ImageViewer {

  private final JLabel displayedImage;
  private final LayoutManager manager;

  /**
   * Initialize the image panel with scrollbar and background panels.
   *
   * @param maximumSize maximum size of the image.
   */
  public GImagePane(Dimension maximumSize) {
    super();
    manager = new GridBagLayout();
    displayedImage = new JLabel();
    addImageLabel(maximumSize);
    setPreferredSize(maximumSize);
    setPanelColors(this);
    displayedImage.setBackground(Theme.getPrimaryColor());
  }

  /**
   * Get the constraints for the image panel.
   * @return constraints for the image panel.
   */
  private GridBagConstraints getConstraints() {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.WEST;
    return constraints;
  }

  /**
   * Add the image label to the panel.
   * @param maximumSize maximum size of the image.
   */
  private void addImageLabel(Dimension maximumSize) {
    setLayout(manager);
    JScrollPane scrollImage = new JScrollPane(displayedImage);
    scrollImage.setBounds(0, 0, maximumSize.width, maximumSize.height);
    add(scrollImage, getConstraints(), 0);
  }

  @Override
  public void setImage(byte[] imageBytes) throws IOException {
    ByteArrayInputStream bIs = new ByteArrayInputStream(imageBytes);
    BufferedImage bImage = ImageIO.read(bIs);
    displayedImage.setIcon(new ImageIcon(bImage));
  }

  @Override
  public void clearImage() {
    displayedImage.setIcon(null);
  }

}
