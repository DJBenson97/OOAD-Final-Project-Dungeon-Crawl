package dungeoncrawl.state;

import dungeoncrawl.ui.GameWindow;

public class StateManager {

    // holds the current active game state
    private GameState current;

    public StateManager() {
        // start in the idle state by default
        this.current = new IdleState();
    }

    // returns the current game state
    public GameState getState() {
        return current;
    }

    // switches to a new state and triggers its entry behavior
    public void setState(GameState next, GameWindow gw) {
        this.current = next;
        next.onEnter(gw);
    }
}