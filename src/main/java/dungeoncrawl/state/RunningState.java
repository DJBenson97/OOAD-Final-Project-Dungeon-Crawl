package dungeoncrawl.state;

import dungeoncrawl.ui.GameWindow;

public class RunningState implements GameState {

    @Override
    public void onEnter(GameWindow gw) {
        // disable all controls while the simulation is running
        gw.disableStrategy();
        gw.disableStart();
        gw.disableClear();
        gw.disableReset();
    }

    @Override
    public void start(GameWindow gw) {
        // start action is ignored while already running
    }

    @Override
    public void clear(GameWindow gw) {
        // clear action is ignored while running
    }

    @Override
    public void resetMaze(GameWindow gw) {
        // reset action is ignored while running
    }
}