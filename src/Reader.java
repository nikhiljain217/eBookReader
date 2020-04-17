import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Reader {
	public abstract int getTotalPageNumber();
	public abstract String getText(int numberOfPages);
	public abstract BufferedImage getCover() throws IOException;
}
