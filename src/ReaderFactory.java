import java.io.IOException;
import java.util.*;
import org.apache.commons.io.FilenameUtils;
/*
 * 
 * This is class is implementing simple Factory pattern to create reader object
 * */
public class ReaderFactory {

	ReaderFactory()
	{
		
	}
	
	//Create the reader object based on extension
	public Reader createReader(String path)
	{	
		Reader reader = null;
		try
		{
		String fileExtension = new String(); 
		fileExtension = FilenameUtils.getExtension(path);
		if(fileExtension.equals("pdf"))
			reader = new PDFReader(path);
		else
			reader  = new NullReader();
		}
		
		catch(Exception e)
		{
			System.out.println("Exception in createReader function with error message "+e);
		}
		
		return reader;
		
		
	}
}
