import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main1 {

    public static void main(String[] args) throws IOException 
    { 
    	String path = new String("D:\\hw\\OOAD\\New folder");
    	Path dir = Paths.get(path);
        WatchLibraryFolder  ws = new WatchLibraryFolder(dir,false);
        Thread WatchFolderThread = new Thread(ws);
        WatchFolderThread.start();
        MainWindow mw = new MainWindow(ws);
        
        
    }
}
