import java.io.FileWriter; 
import java.io.IOException; 
 /**
  * 
  * 
  * This is logger class implementing singleton pattern
  *
  *
  */
 public class Logger {
	static Logger log;
	private Logger()
	{}
	
	public static Logger getInstance()
	{
		if(log==null)
			log =new Logger();
		return log;
	}
	
	
	//This function write the corresponding string to the file
	public void info(String logText)
	{
		
		try {
			FileWriter writer = new FileWriter("debugging.txt",true);
			
			writer.write(logText);
			writer.close();
		} catch (Exception e) {
			
			
			e.printStackTrace();
		}
		
		
	}
}
