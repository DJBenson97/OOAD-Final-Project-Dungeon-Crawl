package dungeoncrawl.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    // tests the constructor and verifies all default values are set correctly
    @Test
    public void testConstructorAndDefaults() {
        Position p = new Position(1, 2);
        Cell c = new Cell(p, true);

        assertEquals(p, c.getPosition());
        assertTrue(c.isWall());
        assertFalse(c.isVisited());
        assertFalse(c.isGoal());
        assertFalse(c.isStart());
    }

    // tests the ability to set and unset wall
    @Test
    public void testSetWall() {
        Cell c = new Cell(new Position(0, 0), true);
        c.setWall(false);
        assertFalse(c.isWall());
    }

    // tests the visited flag setter and getter
    @Test
    public void testSetVisited() {
        Cell c = new Cell(new Position(0, 0), false);
        c.setVisited(true);
        assertTrue(c.isVisited());
    }

    // tests the goal flag setter and getter
    @Test
    public void testSetGoal() {
        Cell c = new Cell(new Position(0, 0), false);
        c.setGoal(true);
        assertTrue(c.isGoal());
    }

    // tests the start flag setter and getter
    @Test
    public void testSetStart() {
        Cell c = new Cell(new Position(0, 0), false);
        c.setStart(true);
        assertTrue(c.isStart());
    }
}