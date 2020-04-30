import java.nio.file.WatchEvent;
import java.util.*;
/*
 * 
 * This is subject class of observer pattern
 * */
public interface Subject {
	ArrayList<MyObserver> observers = new ArrayList<MyObserver>();
	
	//function to notify observer for a change
	default void notifyObservers(WatchEvent<?> event)
    {
        for (MyObserver o: observers)
        {
            o.update(event);
        }
    }
	
	//Function to register the observer
	 default void registerObserver(MyObserver observer)
	    {
	        observers.add(observer);
	    }
	 //Function to remove the observer
	 default void removeObserver(MyObserver observer)
	    {
	        observers.remove(observer);
	    }
}
