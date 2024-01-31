package textgimp.guiview.mainwindow.imageinfo;

import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * This class represents a panel that displays the histogram of the image. Histogram is a 2D line
 * chart that shows the distribution of the pixels in the image.
 */
public class GHistogramViewer extends ChartPanel implements ImageInfoViewer {

  private static final long serialVersionUID = 1L;

  private final XYPlot plot;

  private final XYSplineRenderer renderer;

  private final XYSeriesCollection seriesCollection;

  /**
   * Initialize the histogram panel and setup JFreeChart configuration. Line chart is drawn when
   * displayHistogram is called.
   *
   * @param preferredSize the preferred size of the panel.
   */
  public GHistogramViewer(Dimension preferredSize) {
    super(null);
    setPreferredSize(preferredSize);
    setBorder(new BevelBorder(BevelBorder.RAISED));
    XYDataset dataset = new XYSeriesCollection();
    JFreeChart chart = ChartFactory.createXYLineChart(
        "Histogram",
        "Pixel Value",
        "Frequency",
        dataset);
    plot = chart.getXYPlot();
    renderer = new XYSplineRenderer();
    seriesCollection = new XYSeriesCollection();
    setChart(chart);
  }

  @Override
  public void displayHistogram(int[] histogram, String label) {
    XYSeries series = new XYSeries(label);
    for (int i = 0; i < histogram.length; i++) {
      series.add(i, histogram[i]);
    }
    seriesCollection.addSeries(series);
    plot.setDataset(seriesCollection);
    plot.setRenderer(renderer);
  }

  @Override
  public void clearHistograms() {
    seriesCollection.removeAllSeries();
    plot.setDataset(seriesCollection);
    plot.setRenderer(renderer);
  }
}
