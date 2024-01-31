package textgimp;

import java.util.ArrayList;
import java.util.List;
import textgimp.model.Model;

/**
 * This class represents a logging mock model for the TextGimp program. The model writes the
 * commands it receives to a log.
 */
public abstract class LoggerMockModel implements Model {

  private final List<String[]> log;

  /**
   * Constructs a new LoggerMockModel and initializes the log.
   */
  LoggerMockModel() {
    log = new ArrayList<>();
  }

  /**
   * Adds the given string to the log.
   *
   * @param s the string to add to the log
   */
  public void addToLog(String[] s) {
    log.add(s);
  }

  /**
   * Read the stored log data.
   *
   * @return stored log data
   */
  public List<String[]> getLog() {
    return this.log;
  }
}
