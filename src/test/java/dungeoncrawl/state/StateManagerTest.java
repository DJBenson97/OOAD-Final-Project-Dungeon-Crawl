package dungeoncrawl.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StateManagerTest {

    // fake game window used to isolate state manager behavior
    private static class FakeGameWindow extends dungeoncrawl.ui.GameWindow {

        // invoke the testing constructor to avoid full ui setup
        public FakeGameWindow() {
            super(true);
        }

        // override ui control methods with no behavior
        @Override public void enableStrategy() {}
        @Override public void disableStrategy() {}
        @Override public void enableStart() {}
        @Override public void disableStart() {}
        @Override public void enableClear() {}
        @Override public void disableClear() {}
        @Override public void enableReset() {}
        @Override public void disableReset() {}
    }

    @Test
    // verifies the state manager starts in the idle state
    public void testInitialState() {
        StateManager sm = new StateManager();
        assertTrue(sm.getState() instanceof IdleState);
    }

    @Test
    // verifies that setState correctly updates the current state
    public void testSetState() {
        StateManager sm = new StateManager();
        FakeGameWindow gw = new FakeGameWindow();

        sm.setState(new RunningState(), gw);
        assertTrue(sm.getState() instanceof RunningState);
    }
}