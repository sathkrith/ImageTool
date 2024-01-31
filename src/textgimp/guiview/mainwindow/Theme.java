package textgimp.guiview.mainwindow;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class represents a theme for the application.
 */
public class Theme {

  /**
   * Set the colors of the panel.
   *
   * @param panel panel to set the colors.
   */
  public static void setPanelColors(JPanel panel) {
    panel.setBackground(getPrimaryColor());
  }

  /**
   * Set the colors of the button.
   *
   * @param button button to set the colors.
   */
  public static void setButtonColors(JButton button) {
    button.setOpaque(true);
    button.setBorderPainted(false);
    button.setBackground(getSecondaryColor());
    button.setFont(getSecondaryFont());
    button.setForeground(new Color(255, 255, 255));
    button.setRolloverEnabled(true);

    // change colors on state change
    button.getModel().addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        ButtonModel model = (ButtonModel) e.getSource();
        if (model.isRollover()) {
          button.setBackground(getOnHoverBackground());
        } else {
          button.setBackground(getSecondaryColor());
        }
      }
    });
  }

  /**
   * Set the colors of the label.
   *
   * @param label label to set the colors.
   */
  public static void setLabelColors(JLabel label) {
    label.setFont(getFont());
    label.setForeground(getSecondaryColor());
  }

  /**
   * Set the colors of the text area.
   *
   * @param textArea text area to set the colors.
   */
  public static void setTextAreaColors(JTextField textArea) {
    textArea.setBackground(getTertiaryColor());
    textArea.setForeground(getSecondaryColor());
    textArea.setFont(getSecondaryFont());
  }

  /**
   * Get the font for the application.
   *
   * @return font object to use in the application.
   */
  public static Font getFont() {
    return new Font("TimesRoman", Font.BOLD, 14);
  }

  /**
   * Get the secondary font for the application.
   *
   * @return font object to use in the application.
   */
  public static Font getSecondaryFont() {
    return new Font("SansSerif", Font.BOLD, 12);
  }

  /**
   * Get the primary color for the application.
   *
   * @return color object to use in the application.
   */
  public static Color getPrimaryColor() {
    return new Color(246, 241, 235);
  }

  /**
   * Get the secondary color for the application.
   *
   * @return color object to use in the application.
   */
  public static Color getSecondaryColor() {
    return new Color(0, 38, 51);
  }

  /**
   * Get the tertiary color for the application.
   *
   * @return color object to use in the application.
   */
  public static Color getTertiaryColor() {
    return new Color(255, 255, 255);
  }

  /**
   * Get the color to use when the mouse is hovering over a button.
   *
   * @return color object to use in the application.
   */
  public static Color getOnHoverBackground() {
    return new Color(55, 145, 214);
  }
}

