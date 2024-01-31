package textgimp.model.imagebuilder;

import java.io.IOException;
import textgimp.model.betterimage.Image;

/**
 * This class represents a builder for BMP images. This class is responsible for encoding and
 * decoding BMP images.
 */
public class BMPBuilder extends AbstractImageBuilder implements ImageBuilder {

  @Override
  public Image loadImage(byte[] data) throws IllegalArgumentException {
    return this.parseRGB(data, "bmp");
  }

  @Override
  public byte[] writeImage(Image image) throws IllegalArgumentException, IOException {
    return this.writeRGB(image, "bmp");
  }
}
