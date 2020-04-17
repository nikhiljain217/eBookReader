import java.nio.file.WatchEvent;

public interface MyObserver {
	
	    void update(WatchEvent<?> event);
	
}
