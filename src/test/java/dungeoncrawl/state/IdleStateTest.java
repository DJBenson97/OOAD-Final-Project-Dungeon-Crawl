package dungeoncrawl.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IdleStateTest {

    @Test
    // tests that onEnter sets the correct button states
    public void testOnEnter() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        IdleState state = new IdleState();
        state.onEnter(gw);

        assertTrue(gw.strategyEnabled);
        assertTrue(gw.startEnabled);
        assertFalse(gw.clearEnabled);
        assertTrue(gw.resetEnabled);
    }

    @Test
    // tests that start transitions to RunningState and calls internal start
    public void testStart() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        IdleState state = new IdleState();
        state.start(gw);

        assertTrue(gw.startCalled); // simulation started
        assertTrue(sm.getState() instanceof RunningState);
    }

    @Test
    // tests that clear does nothing in idle state
    public void testClearDoesNothing() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        IdleState state = new IdleState();
        state.clear(gw);

        assertFalse(gw.clearCalled);
        assertTrue(sm.getState() instanceof IdleState);
    }

    @Test
    // tests that resetMaze stays in idle but calls resetMazeInternal
    public void testResetMaze() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        IdleState state = new IdleState();
        state.resetMaze(gw);

        assertTrue(gw.resetCalled);
        assertTrue(sm.getState() instanceof IdleState);
    }
}