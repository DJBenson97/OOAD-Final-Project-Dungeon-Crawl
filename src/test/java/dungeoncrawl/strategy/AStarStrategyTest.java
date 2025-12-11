package dungeoncrawl.strategy;

import dungeoncrawl.config.SimulationConfig;
import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AStarStrategyTest {

    // builds a small maze with a fixed wall layout for testing
    private Maze buildMaze() {
        SimulationConfig config = config();
        Maze maze = new Maze(3, 3, config);

        // clear all walls to start with an open grid
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                maze.getCell(r, c).setWall(false);
            }
        }

        // add walls to force a non trivial path
        maze.getCell(1, 0).setWall(true);
        maze.getCell(1, 1).setWall(true);

        // set the goal position
        maze.setGoal(new Position(2, 2));
        return maze;
    }

    // creates a configuration used by the test maze
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
    // verifies that a star search is able to reach the goal
    public void testAStarFindsGoal() {
        Maze maze = buildMaze();
        AStarStrategy as = new AStarStrategy();
        Position start = new Position(0, 0);

        // initialize the strategy
        as.initialize(maze, start);

        Position current = start;
        int safety = 20;

        // step the strategy until the goal is found or safety runs out
        while (!as.goalFound() && safety-- > 0) {
            current = as.step(maze, current);
        }

        // confirm that the goal was reached
        assertTrue(as.goalFound());
    }

    @Test
    // verifies that a star explores multiple cells while searching
    public void testAStarVisitedCount() {
        Maze maze = buildMaze();
        AStarStrategy as = new AStarStrategy();
        Position start = new Position(0, 0);

        // initialize the strategy
        as.initialize(maze, start);

        Position current = start;
        int safety = 20;

        // advance the search until completion or timeout
        while (!as.goalFound() && safety-- > 0) {
            current = as.step(maze, current);
        }

        // ensure that several cells were visited during the search
        assertTrue(as.getVisitedSet().size() >= 3);
    }
}