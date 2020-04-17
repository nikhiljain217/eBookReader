import java.nio.file.WatchEvent;
import java.util.*;

public interface Subject {
	ArrayList<MyObserver> observers = new ArrayList<MyObserver>();
	
	default void notifyObservers(WatchEvent<?> event)
    {
        for (MyObserver o: observers)
        {
            o.update(event);
        }
    }
	
	 default void registerObserver(MyObserver observer)
	    {
	        observers.add(observer);
	    }
	 
	 default void removeObserver(MyObserver observer)
	    {
	        observers.remove(observer);
	    }
}
