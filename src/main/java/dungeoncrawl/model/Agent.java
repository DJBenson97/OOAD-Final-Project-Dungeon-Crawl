package dungeoncrawl.model;

import dungeoncrawl.strategy.NavigationStrategy;

public class Agent {

    private final Maze maze;
    private final NavigationStrategy strategy;

    private Position position;
    private boolean running;

    public Agent(Maze maze, Position start, NavigationStrategy strategy) {
        this.maze = maze;
        this.position = start;
        this.strategy = strategy;
        this.running = false;
    }

    public Position getPosition() {
        return position;
    }

    public void start() {
        if (running) return;
        running = true;
    
        // init strategy first
        strategy.initialize(maze, position);
    
        while (running && !strategy.goalFound()) {
    
            Position next = strategy.step(maze, position);
    
            if (next == null) {
                running = false;
                break;
            }
    
            position = next;
            maze.notifyObservers();
    
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                break;
            }
        }
    
        running = false;
        maze.notifyObservers();
    }    
}