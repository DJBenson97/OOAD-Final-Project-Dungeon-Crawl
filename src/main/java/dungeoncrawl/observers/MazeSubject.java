package dungeoncrawl.observers;

import java.util.ArrayList;
import java.util.List;

public class MazeSubject {

    // list of observers that are notified when the maze changes
    private final List<MazeObserver> observers = new ArrayList<>();

    // registers a new observer to receive maze updates
    public void addObserver(MazeObserver observer) {
        observers.add(observer);
    }

    // removes an observer so it no longer receives updates
    public void removeObserver(MazeObserver observer) {
        observers.remove(observer);
    }

    // notifies all registered observers of a maze update
    public void notifyObservers() {
        for (MazeObserver observer : observers) {
            observer.onMazeUpdated();
        }
    }
}