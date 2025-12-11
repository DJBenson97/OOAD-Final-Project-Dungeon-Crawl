package dungeoncrawl.strategy;

import dungeoncrawl.config.SimulationConfig;
import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NavigationStrategyTest {

    // creates a small configuration used for testing
    private SimulationConfig config() {
        return new SimulationConfig(
                3, 3, 20,
                new java.awt.Color(40, 40, 40),
                new java.awt.Color(190, 190, 190),
                java.awt.Color.RED,
                20
        );
    }

    @Test
    // verifies that bfs satisfies the navigation strategy interface
    // and returns initialized data structures
    public void testInterfaceCompliance() {

        // create a bfs strategy instance
        NavigationStrategy s = new BFSStrategy();

        // create a simple maze and starting position
        Maze maze = new Maze(3, 3, config());
        Position start = new Position(0, 0);

        // remove all walls so the maze is fully open
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                maze.getCell(r, c).setWall(false);

        // set the goal position
        maze.setGoal(new Position(2, 2));

        // initialize the strategy
        s.initialize(maze, start);

        // ensure required sets are created and accessible
        assertNotNull(s.getVisitedSet());
        assertNotNull(s.getFrontierSet());
        assertNotNull(s.getFinalPath());
    }
}