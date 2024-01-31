package textgimp.guiview.mainwindow.status;

import static textgimp.guiview.mainwindow.Theme.setPanelColors;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import textgimp.guiview.mainwindow.Theme;
import textgimp.utility.Result;

/**
 * Implements the status panel of SwingGimp. The status panel shows the last action performed and
 * the result of the action.
 */
public class GStatusPane extends JPanel implements StatusPanel {

  private JLabel lastAction;
  private JLabel status;

  /**
   * Create a new status panel and initialize the components.
   */
  public GStatusPane() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 5, 40));
    addLastActionPane();
    addResultPane();
    setToolTipText("Displays the status of last action performed");
    setPanelColors(this);
  }

  /**
   * Add the last action label to the status panel.
   */
  private void addLastActionPane() {
    lastAction = new JLabel();
    lastAction.setToolTipText("Last action performed.");
    Theme.setLabelColors(lastAction);
    add(lastAction, 0);
  }

  /**
   * Add the result label to the status panel.
   */
  private void addResultPane() {
    status = new JLabel();
    status.setToolTipText("Status of last action.");
    Theme.setLabelColors(status);
    add(status, 1);
  }

  @Override
  public void setStatus(String action, Result res) {
    // update the status panel
    lastAction.setText(action + ": ");

    // if result is null, clear the status panel
    if (res == null) {
      status.setText("");
      return;
    }
    status.setText(res.getMessage());

    // show error popup if result is an error
    if (!res.isSuccess()) {
      JOptionPane.showMessageDialog(this, res.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
