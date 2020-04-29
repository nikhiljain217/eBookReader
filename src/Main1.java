import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main1 {

    public static void main(String[] args) throws IOException 
    { 
    	Logger log = Logger.getInstance();
    	String path = new String("D:\\hw\\OOAD\\New folder");
    	Path dir = Paths.get(path);
    	log.info("Creating WatchService for monitoring Folders");
        WatchLibraryFolder  ws = new WatchLibraryFolder(dir,false);
        
        Thread WatchFolderThread = new Thread(ws);
        log.info("Starting the thread for Watch Service");
        WatchFolderThread.start();
        log.info("Thread Started for watch Service");
        MainWindow mw = new MainWindow(ws);
        
        
    }
}
