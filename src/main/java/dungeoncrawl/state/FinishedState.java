package dungeoncrawl.state;

import dungeoncrawl.ui.GameWindow;

public class FinishedState implements GameState {

    @Override
    public void onEnter(GameWindow gw) {
        // enable controls appropriate after the simulation has finished
        gw.enableStrategy();
        gw.disableStart();
        gw.enableClear();
        gw.enableReset();
    }

    @Override
    public void start(GameWindow gw) {
        // start action is ignored until the search is cleared
    }

    @Override
    public void clear(GameWindow gw) {
        // remove search data and return to the idle state
        gw.clearSearchData();
        gw.getStateManager().setState(new IdleState(), gw);
    }

    @Override
    public void resetMaze(GameWindow gw) {
        // rebuild the maze and return to the idle state
        gw.resetMazeInternal();
        gw.getStateManager().setState(new IdleState(), gw);
    }
}