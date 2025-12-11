package dungeoncrawl.strategy;

import dungeoncrawl.config.SimulationConfig;
import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DFSStrategyTest {

    // builds a simple deterministic 3x3 maze
    private Maze buildMaze() {
        SimulationConfig config = config();
        Maze maze = new Maze(3, 3, config);

        // clear walls
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                maze.getCell(r, c).setWall(false);
            }
        }

        // add 2 blocking walls
        maze.getCell(1, 0).setWall(true);
        maze.getCell(1, 1).setWall(true);

        maze.setGoal(new Position(2, 2));
        return maze;
    }

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
    // tests dfs finds the goal
    public void testDFSFindsGoal() {
        Maze maze = buildMaze();
        DFSStrategy dfs = new DFSStrategy();
        Position start = new Position(0, 0);

        dfs.initialize(maze, start);

        Position current = start;
        int safety = 20;

        while (!dfs.goalFound() && safety-- > 0) {
            current = dfs.step(maze, current);
        }

        assertTrue(dfs.goalFound());
    }

    @Test
    // tests dfs visits nodes
    public void testDFSVisitedCount() {
        Maze maze = buildMaze();
        DFSStrategy dfs = new DFSStrategy();
        Position start = new Position(0, 0);

        dfs.initialize(maze, start);

        Position current = start;
        int safety = 20;

        while (!dfs.goalFound() && safety-- > 0) {
            current = dfs.step(maze, current);
        }

        assertTrue(dfs.getVisitedSet().size() >= 4);
    }
}