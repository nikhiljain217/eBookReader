import java.io.*;
import java.util.HashMap;
import java.awt.image.BufferedImage;

/*
 * NullReader - Null object Pattern for the files which are not supported yet
 * */
public class NullReader extends Reader{

	public NullReader()
	{
		
	}
	
	public int getTotalPageNumber()
	{return 0;}
	
	public HashMap<Integer,String> getText()
	{
		return (new HashMap<Integer,String>());
				
				
	}
	public BufferedImage getCover() throws IOException
	{
		return new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
	}
	
}
