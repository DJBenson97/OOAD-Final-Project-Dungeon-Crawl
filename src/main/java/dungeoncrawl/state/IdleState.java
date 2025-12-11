package dungeoncrawl.state;

import dungeoncrawl.ui.GameWindow;

public class IdleState implements GameState {

    @Override
    public void onEnter(GameWindow gw) {
        // enable controls appropriate for the idle state
        gw.enableStrategy();
        gw.enableStart();
        gw.disableClear();
        gw.enableReset();
    }

    @Override
    public void start(GameWindow gw) {
        // begin the simulation and transition to the running state
        gw.startSimulationInternal();
        gw.getStateManager().setState(new RunningState(), gw);
    }

    @Override
    public void clear(GameWindow gw) {
        // clear action has no effect while idle
    }

    @Override
    public void resetMaze(GameWindow gw) {
        // rebuild the maze while remaining in the idle state
        gw.resetMazeInternal();
    }
}