package dungeoncrawl.model;

import dungeoncrawl.config.SimulationConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    @Test
    public void testMazeInitialization() {
        SimulationConfig config = new SimulationConfig(
                11, 11,
                200,
                java.awt.Color.BLACK,
                java.awt.Color.GRAY,
                java.awt.Color.RED,
                30
        );

        Maze maze = new Maze(11, 11, config);

        assertEquals(11, maze.getRows());
        assertEquals(11, maze.getCols());

        // All cells should initially be walls
        for (int r = 0; r < 11; r++) {
            for (int c = 0; c < 11; c++) {
                assertTrue(maze.getCell(r, c).isWall());
            }
        }
    }

    @Test
    public void testSetGoal() {
        SimulationConfig config = new SimulationConfig(
                11, 11,
                200,
                java.awt.Color.BLACK,
                java.awt.Color.GRAY,
                java.awt.Color.RED,
                30
        );

        Maze maze = new Maze(11, 11, config);
        Position goal = new Position(5, 5);

        maze.setGoal(goal);

        assertTrue(maze.getCell(goal).isGoal());
    }

    @Test
    public void testInBounds() {
        SimulationConfig config = new SimulationConfig(
                11, 11,
                200,
                java.awt.Color.BLACK,
                java.awt.Color.GRAY,
                java.awt.Color.RED,
                30
        );

        Maze maze = new Maze(11, 11, config);

        assertTrue(maze.isInBounds(0, 0));
        assertTrue(maze.isInBounds(10, 10));
        assertFalse(maze.isInBounds(-1, 0));
        assertFalse(maze.isInBounds(11, 5));
    }
}