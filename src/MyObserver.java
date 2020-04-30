import java.nio.file.WatchEvent;

/*
 * 
 * Observer class for Library
 * */

public interface MyObserver {
	
	    void update(WatchEvent<?> event);
	
}
