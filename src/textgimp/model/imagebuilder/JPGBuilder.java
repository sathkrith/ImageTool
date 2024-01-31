package textgimp.model.imagebuilder;

import java.io.IOException;
import textgimp.model.betterimage.Image;

/**
 * This class represents a builder for JPG images. This class is responsible for encoding and
 * decoding JPG images.
 */
public class JPGBuilder extends AbstractImageBuilder implements ImageBuilder {

  @Override
  public Image loadImage(byte[] data) throws IllegalArgumentException {
    return this.parseRGB(data, "jpg");
  }

  @Override
  public byte[] writeImage(Image image) throws IllegalArgumentException, IOException {
    return this.writeRGB(image, "jpg");
  }
}
