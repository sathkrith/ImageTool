package textgimp.utility;

/**
 * This class implements a result object. Result object contains a message and status. This is used
 * to communicate between different classes.
 */
public class ResultImpl implements Result {

  private final String message;

  private final boolean status;

  private final boolean isTerminating;

  /**
   * Construct new Result object and initialize fields.
   *
   * @param isSuccess True of successful.
   * @param message   information on the operation.
   */
  public ResultImpl(boolean isSuccess, String message) {
    this.message = message;
    this.status = isSuccess;
    this.isTerminating = false;
  }

  /**
   * Construct new Result from params and initialize fields. Overloads the constructor above with an
   * additional parameter.
   *
   * @param isSuccess     True of successful.
   * @param isTerminating True if terminating.
   * @param message       information on the operation.
   */
  public ResultImpl(boolean isSuccess, boolean isTerminating, String message) {
    this.message = message;
    this.status = isSuccess;
    this.isTerminating = isTerminating;
  }

  @Override
  public boolean isSuccess() {
    return status;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public boolean isTerminating() {
    return isTerminating;
  }
}
