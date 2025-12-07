package dungeoncrawl.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

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

    @Test
    public void testSetters() {
        Cell c = new Cell(new Position(0, 0), false);

        c.setWall(true);
        c.setVisited(true);
        c.setGoal(true);
        c.setStart(true);

        assertTrue(c.isWall());
        assertTrue(c.isVisited());
        assertTrue(c.isGoal());
        assertTrue(c.isStart());
    }
}