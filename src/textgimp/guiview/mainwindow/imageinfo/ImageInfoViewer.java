package textgimp.guiview.mainwindow.imageinfo;

/**
 * This interface represents the Histogram panel in the main window. Provides methods to display and
 * clear histograms.
 */
public interface ImageInfoViewer {

  /**
   * Given an integer array with image histogram, display the histogram chart.
   *
   * @param histogram histogram to display.
   * @param label     label of the histogram.
   */
  void displayHistogram(int[] histogram, String label);

  /**
   * Remove all histograms being displayed.
   */
  void clearHistograms();
}
