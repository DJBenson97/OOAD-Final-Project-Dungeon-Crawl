package dungeoncrawl.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunningStateTest {

    // When entering RunningState, ALL controls must be disabled.
    @Test
    public void testOnEnterDisablesAllControls() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        RunningState rs = new RunningState();
        rs.onEnter(gw);

        assertFalse(gw.strategyEnabled);
        assertFalse(gw.startEnabled);
        assertFalse(gw.clearEnabled);
        assertFalse(gw.resetEnabled);
    }

    // Calling start() while running should do nothing (no internal start).
    @Test
    public void testStartDoesNothing() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        RunningState rs = new RunningState();
        rs.start(gw);

        assertFalse(gw.startCalled);
    }

    // Calling clear() while running should do nothing (no clearSearchData call).
    @Test
    public void testClearDoesNothing() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        RunningState rs = new RunningState();
        rs.clear(gw);

        assertFalse(gw.clearCalled);
    }

    // Calling resetMaze() while running should do nothing (no resetMazeInternal call).
    @Test
    public void testResetDoesNothing() {
        StateManager sm = new StateManager();
        FakeGameWindowHelper gw = new FakeGameWindowHelper(sm);

        RunningState rs = new RunningState();
        rs.resetMaze(gw);

        assertFalse(gw.resetCalled);
    }
}