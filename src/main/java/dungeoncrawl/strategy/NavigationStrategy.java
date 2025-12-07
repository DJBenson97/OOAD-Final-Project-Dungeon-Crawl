package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.Set;

public interface NavigationStrategy {

    // called once at start
    void initialize(Maze maze, Position start);

    // called every animation tick
    Position step(Maze maze, Position current);

    // true when the strategy reached the maze goal
    boolean goalFound();

    // used by MazePanel for coloring
    Set<Position> getVisitedSet();
    Set<Position> getFrontierSet();
}