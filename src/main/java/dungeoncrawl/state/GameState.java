package dungeoncrawl.state;

import dungeoncrawl.ui.GameWindow;

public interface GameState {

    // called when the state becomes active
    void onEnter(GameWindow gw);

    // called when the user attempts to start the simulation
    void start(GameWindow gw);

    // called when the user tries to clear the current search
    void clear(GameWindow gw);

    // called when the user requests a full maze reset
    void resetMaze(GameWindow gw);
}