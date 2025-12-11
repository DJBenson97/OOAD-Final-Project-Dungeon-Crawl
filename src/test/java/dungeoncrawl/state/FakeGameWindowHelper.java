package dungeoncrawl.state;

import dungeoncrawl.ui.GameWindow;

public class FakeGameWindowHelper extends GameWindow {

    // state manager used to track state transitions
    private final StateManager sm;

    public FakeGameWindowHelper(StateManager sm) {
        // use testing constructor to avoid full ui setup
        super(true);
        this.sm = sm;
    }

    // flags used to track button enabled states
    public boolean strategyEnabled = false;
    public boolean startEnabled = false;
    public boolean clearEnabled = false;
    public boolean resetEnabled = false;

    // flags used to track internal method calls
    public boolean startCalled = false;
    public boolean clearCalled = false;
    public boolean resetCalled = false;

    @Override
    public void enableStrategy() {
        strategyEnabled = true;
    }

    @Override
    public void disableStrategy() {
        strategyEnabled = false;
    }

    @Override
    public void enableStart() {
        startEnabled = true;
    }

    @Override
    public void disableStart() {
        startEnabled = false;
    }

    @Override
    public void enableClear() {
        clearEnabled = true;
    }

    @Override
    public void disableClear() {
        clearEnabled = false;
    }

    @Override
    public void enableReset() {
        resetEnabled = true;
    }

    @Override
    public void disableReset() {
        resetEnabled = false;
    }

    // overrides internal actions to record when they are invoked

    @Override
    public void startSimulationInternal() {
        startCalled = true;
    }

    @Override
    public void clearSearchData() {
        clearCalled = true;
    }

    @Override
    public void resetMazeInternal() {
        resetCalled = true;
    }

    // returns the injected state manager for testing
    @Override
    public StateManager getStateManager() {
        return sm;
    }
}