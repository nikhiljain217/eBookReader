import java.io.FileWriter; 
import java.io.IOException; 
 
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
