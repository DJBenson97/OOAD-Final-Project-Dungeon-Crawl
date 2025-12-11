package dungeoncrawl.observers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeSubjectTest {

    private static class FakeObserver implements MazeObserver {
        boolean notified = false;

        @Override
        public void onMazeUpdated() {
            notified = true;
        }
    }

    // tests that an added observer is notified when notifyObservers is called
    @Test
    public void testNotifyObserversCallsUpdate() {
        MazeSubject subject = new MazeSubject();
        FakeObserver observer = new FakeObserver();

        subject.addObserver(observer);
        subject.notifyObservers();

        assertTrue(observer.notified);
    }

    // tests that a removed observer is not notified after removal
    @Test
    public void testRemovedObserverDoesNotReceiveUpdates() {
        MazeSubject subject = new MazeSubject();
        FakeObserver observer = new FakeObserver();

        subject.addObserver(observer);
        subject.removeObserver(observer);
        subject.notifyObservers();

        assertFalse(observer.notified);
    }

    // tests that multiple observers all receive notifications
    @Test
    public void testMultipleObserversNotified() {
        MazeSubject subject = new MazeSubject();
        FakeObserver o1 = new FakeObserver();
        FakeObserver o2 = new FakeObserver();

        subject.addObserver(o1);
        subject.addObserver(o2);
        subject.notifyObservers();

        assertTrue(o1.notified);
        assertTrue(o2.notified);
    }
}