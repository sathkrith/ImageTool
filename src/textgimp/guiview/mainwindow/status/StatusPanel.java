package textgimp.guiview.mainwindow.status;

import textgimp.utility.Result;

/**
 * Status panel indicates current status of the application and shows information on last performed
 * operation.
 */
public interface StatusPanel {

  /**
   * Update the status panel with the last action performed and the result of the action.
   *
   * @param action last action performed.
   * @param res    result of the last action.
   */
  void setStatus(String action, Result res);
}
