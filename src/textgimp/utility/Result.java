package textgimp.utility;

/**
 * Blueprint of what a result must support. Result is used to communicate success or failure.
 */
public interface Result {

  /**
   * Indicates whether the operation was successful or not.
   *
   * @return true if operation is successful.
   */
  boolean isSuccess();

  /**
   * Contains informative message about operation.
   *
   * @return string containing information on the operation.
   */
  String getMessage();

  /**
   * Indicates whether the operating is terminating.
   *
   * @return true if operation is terminating.
   */
  boolean isTerminating();

}
