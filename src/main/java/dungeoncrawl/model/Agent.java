package dungeoncrawl.model;

import dungeoncrawl.strategy.NavigationStrategy;

public class Agent {

    // reference to the maze the agent is navigating
    private final Maze maze;

    // navigation strategy used to decide movement
    private final NavigationStrategy strategy;

    // current position of the agent in the maze
    private Position position;

    // indicates whether the agent is currently running
    private boolean running;

    public Agent(Maze maze, Position start, NavigationStrategy strategy) {
        // store maze reference
        this.maze = maze;

        // set initial position
        this.position = start;

        // assign the navigation strategy
        this.strategy = strategy;

        // agent starts in a non running state
        this.running = false;
    }

    // returns the current agent position
    public Position getPosition() {
        return position;
    }

    // exposes the strategy so the ui can inspect search state
    public NavigationStrategy getStrategy() {
        return strategy;
    }

    // returns the maze associated with this agent
    public Maze getMaze() {
        return maze;
    }    

    // begins executing the navigation strategy
    public void start() {
        // prevent starting if already running
        if (running) return;
        running = true;

        // initialize the strategy before stepping
        strategy.initialize(maze, position);

        // continue stepping until stopped or goal is found
        while (running && !strategy.goalFound()) {
            Position next = strategy.step(maze, position);

            // stop if no further moves are possible
            if (next == null) {
                running = false;
                break;
            }

            // update agent position
            position = next;

            // notify observers so the ui can update
            maze.notifyObservers();

            try {
                // pause between moves for visualization
                Thread.sleep(maze.getConfig().getMoveDelayMs());
            } catch (InterruptedException e) {
                break;
            }
        }

        // mark the agent as no longer running
        running = false;

        // notify observers one final time
        maze.notifyObservers();
    }
}