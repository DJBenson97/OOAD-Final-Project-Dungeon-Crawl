package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.Set;

// defines a common interface for all maze navigation strategies
public interface NavigationStrategy {

    // called once before navigation begins to set up internal state
    void initialize(Maze maze, Position start);

    // performs a single navigation step and returns the next position
    Position step(Maze maze, Position current);

    // indicates whether the strategy has found the goal
    boolean goalFound();

    // returns all positions that have been visited so far
    Set<Position> getVisitedSet();

    // returns the current frontier of positions to be explored
    Set<Position> getFrontierSet();

    // returns the final path from start to goal, if found
    Set<Position> getFinalPath();
}
