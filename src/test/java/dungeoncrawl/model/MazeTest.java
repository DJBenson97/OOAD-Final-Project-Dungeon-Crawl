package dungeoncrawl.model;

import dungeoncrawl.config.SimulationConfig;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    // builds a valid config for tests
    private SimulationConfig makeConfig() {
        return new SimulationConfig(
                5, 5,           // rows, cols
                0,              // move delay (unused for test)
                Color.BLACK,    // wall color
                Color.WHITE,    // floor color
                Color.RED,      // agent color
                20              // tile size
        );
    }

    // tests that maze initializes with correct row and column counts
    @Test
    public void testMazeInitialization() {
        SimulationConfig config = makeConfig();
        Maze maze = new Maze(5, 5, config);
        assertEquals(5, maze.getRows());
        assertEquals(5, maze.getCols());
    }

    // tests that the internal grid is fully allocated
    @Test
    public void testGridNotNull() {
        SimulationConfig config = makeConfig();
        Maze maze = new Maze(5, 5, config);
        assertNotNull(maze.getGrid()[0][0]);
        assertNotNull(maze.getGrid()[4][4]);
    }

    // tests that inBounds returns true for valid coordinates
    @Test
    public void testInBoundsTrue() {
        SimulationConfig config = makeConfig();
        Maze maze = new Maze(5, 5, config);
        assertTrue(maze.isInBounds(0, 0));
        assertTrue(maze.isInBounds(4, 4));
    }

    // tests that inBounds returns false for invalid coordinates
    @Test
    public void testInBoundsFalse() {
        SimulationConfig config = makeConfig();
        Maze maze = new Maze(5, 5, config);
        assertFalse(maze.isInBounds(-1, 0));
        assertFalse(maze.isInBounds(0, 5));
    }

    // tests that setting a goal correctly marks the cell
    @Test
    public void testSetGoal() {
        SimulationConfig config = makeConfig();
        Maze maze = new Maze(5, 5, config);
        Position goal = new Position(4, 4);
        maze.setGoal(goal);
        assertTrue(maze.getCell(goal).isGoal());
    }

    // tests that setting start correctly marks the cell
    @Test
    public void testSetStart() {
        SimulationConfig config = makeConfig();
        Maze maze = new Maze(5, 5, config);
        Position start = new Position(0, 0);
        maze.setStart(start);
        assertTrue(maze.getCell(start).isStart());
    }

    // tests that clearing the maze search data removes all visited flags
    @Test
    public void testClearSearchData() {
        SimulationConfig config = makeConfig();
        Maze maze = new Maze(5, 5, config);

        // mark a cell visited manually
        maze.getCell(1, 1).setVisited(true);

        maze.clearSearchData();

        assertFalse(maze.getCell(1, 1).isVisited());
    }
}