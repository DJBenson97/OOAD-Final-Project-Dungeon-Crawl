package dungeoncrawl.strategy;

import dungeoncrawl.config.SimulationConfig;
import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BFSStrategyTest {

    // helper to build a deterministic 3x3 maze
    private Maze buildMaze() {
        SimulationConfig config = new SimulationConfig(
                3, 3, 20,
                new java.awt.Color(40, 40, 40),
                new java.awt.Color(190, 190, 190),
                java.awt.Color.RED,
                20
        );

        Maze maze = new Maze(3, 3, config);

        // clear all walls
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                maze.getCell(r, c).setWall(false);
            }
        }

        // set walls
        maze.getCell(1, 0).setWall(true);
        maze.getCell(1, 1).setWall(true);

        // set goal
        maze.setGoal(new Position(2, 2));

        return maze;
    }

    @Test
    // tests that bfs finds the goal
    public void testBFSFindsGoal() {
        Maze maze = buildMaze();
        Position start = new Position(0, 0);

        BFSStrategy bfs = new BFSStrategy();
        bfs.initialize(maze, start);

        Position current = start;
        int safety = 20;

        while (!bfs.goalFound() && safety-- > 0) {
            current = bfs.step(maze, current);
        }

        assertTrue(bfs.goalFound());
    }

    @Test
    // tests that bfs visits expected nodes
    public void testBFSVisitedCount() {
        Maze maze = buildMaze();
        Position start = new Position(0, 0);

        BFSStrategy bfs = new BFSStrategy();
        bfs.initialize(maze, start);

        Position current = start;
        int safety = 20;

        while (!bfs.goalFound() && safety-- > 0) {
            current = bfs.step(maze, current);
        }

        assertTrue(bfs.getVisitedSet().size() >= 4);
    }
}