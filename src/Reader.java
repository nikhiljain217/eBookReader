import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/*
 * Abstract reader class which can be extended for supporting more formats
 * */

public abstract class Reader {
	public abstract int getTotalPageNumber();
	public abstract HashMap<Integer,String> getText();
	public abstract BufferedImage getCover() throws IOException;
}
