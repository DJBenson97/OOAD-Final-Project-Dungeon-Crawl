package dungeoncrawl.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FinishedStateTest {

    @Test
    // tests that onEnter enables/disables the correct controls
    public void testOnEnter() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        FinishedState state = new FinishedState();
        state.onEnter(gw);

        assertTrue(gw.strategyEnabled);
        assertFalse(gw.startEnabled);
        assertTrue(gw.clearEnabled);
        assertTrue(gw.resetEnabled);
    }

    @Test
    // tests that clear transitions to IdleState and erases search data
    public void testClearTransitionsToIdle() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        FinishedState state = new FinishedState();
        state.clear(gw);

        assertTrue(gw.clearCalled);
        assertTrue(sm.getState() instanceof IdleState);
    }

    @Test
    // tests that reset transitions to IdleState and calls resetMazeInternal
    public void testResetTransitionsToIdle() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        FinishedState state = new FinishedState();
        state.resetMaze(gw);

        assertTrue(gw.resetCalled);
        assertTrue(sm.getState() instanceof IdleState);
    }
}