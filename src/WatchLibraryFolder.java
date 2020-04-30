import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.LinkOption.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.*;


/*
 * 
 * Class which monitors the Library folder
 * This run as separate thread and use in-built pattern observer pattern
 * */
public class WatchLibraryFolder implements Runnable, Subject {

	 private WatchService watcher;
	 private Map<WatchKey,Path> keys;
	 private boolean trace = false;
	 private ArrayList<Path> createFileList;
	 private ArrayList<Path> deleteFileList;
	 private Logger log = Logger.getInstance();
	 
	 @SuppressWarnings("unchecked")
	    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
	        return (WatchEvent<T>)event;
	    }
	 
	  
	 public WatchLibraryFolder(Path dir, Object o) throws IOException {
	        this.watcher = FileSystems.getDefault().newWatchService();
	        this.keys = new HashMap<WatchKey,Path>();
	        
	        register(dir);
	        // enable trace after initial registration
	        log.info("Directory registered"+dir.toString());
	        this.trace = true;
	        
	    }
	 
	 
	 //For registering path to the service
	 private void register(Path dir) throws IOException {
		 	log.info("Registering the directory "+dir.toString()+"for watch Service");
	        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE);
	        if (trace) {
	            Path prev = keys.get(key);
	            if (prev == null) {
	                System.out.format("register: %s\n", dir);
	                
	                
	            } else {
	                if (!dir.equals(prev)) {
	                	
	                    System.out.format("update: %s -> %s\n", prev, dir);
	                }
	            }
	        }
	        keys.put(key, dir);
	
	  }
	 
	 
	 //This class process new events on the folder.
	 void processEvents() {
	        for (;;) {
	 
	            // wait for key to be signalled
	            WatchKey key;
	            try {
	                key = watcher.take();
	            } catch (InterruptedException x) {
	                return;
	            }
	 
	            Path dir = keys.get(key);
	            if (dir == null) {
	                System.err.println("WatchKey not recognized!!");
	                continue;
	            }
	 
	            for (WatchEvent<?> event: key.pollEvents()) {
	                WatchEvent.Kind kind = event.kind();
	                
	                log.info("Notifying the observers");
	                this.notifyObservers(event);
	                // TBD - provide example of how OVERFLOW event is handled
	                if (kind == OVERFLOW) {
	                    continue;
	                }
	 
	                // Context for directory entry event is the file name of entry
	                WatchEvent<Path> ev = cast(event);
	                Path name = ev.context();
	                Path child = dir.resolve(name);
	 
	                // print out event
	                System.out.format("%s: %s\n", event.kind().name(), child);
	                log.info("New Event: "+event.kind().name()+"on file "+child.toString());
	 
	                // if directory is created, and watching recursively, then
	                // register it and its sub-directories
	                
	            }
	 
	            // reset key and remove from set if directory no longer accessible
	            boolean valid = key.reset();
	            if (!valid) {
	                keys.remove(key);
	 
	                // all directories are inaccessible
	                if (keys.isEmpty()) {
	                	log.info("WatchService -  Directories are in accessible");
	                    break;
	                }
	            }
	        }
	    }
	 
	 
	@Override
	public void run() {
		// 
		this.processEvents();
		
	}
}