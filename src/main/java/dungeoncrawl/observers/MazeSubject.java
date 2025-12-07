package dungeoncrawl.observers;

import java.util.ArrayList;
import java.util.List;

public class MazeSubject {

    private final List<MazeObserver> observers = new ArrayList<>();

    public void addObserver(MazeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MazeObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (MazeObserver observer : observers) {
            observer.onMazeUpdated();
        }
    }
}