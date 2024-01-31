package utility;

import java.util.Random;

/**
 * This is a utility class for testing to generate random strings.
 */
public class TestHelper {

  public static final Random RANDOM_GEN = new Random(0);

  /**
   * Generates a random string of the given size.
   *
   * @param size the size of the string to generate
   * @return a random string of the given size
   */
  public static String generateRandomStringOfSize(int size) {
    String helperString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder randomString = new StringBuilder();

    // generate a character at a time and append it to the string
    while (randomString.length() < size) {
      int index = (int) (RANDOM_GEN.nextFloat() * helperString.length());
      randomString.append(helperString.charAt(index));
    }
    return randomString.toString();
  }

  /**
   * Generates a random string of size 100.
   *
   * @return a random string of size 100
   */
  public static String generateRandomString() {
    return generateRandomStringOfSize(100);
  }
}
