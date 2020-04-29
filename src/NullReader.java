import java.io.*;
import java.awt.image.BufferedImage;
public class NullReader extends Reader{

	public NullReader()
	{
		
	}
	
	public int getTotalPageNumber()
	{return 0;}
	
	public String getText(int numberofPages)
	{
		return "";
				
				
	}
	public BufferedImage getCover()
	{
		return new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
	}
	
}
