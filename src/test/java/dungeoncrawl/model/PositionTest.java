package dungeoncrawl.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void testConstructorAndGetters() {
        Position p = new Position(3, 5);
        assertEquals(3, p.getRow());
        assertEquals(5, p.getCol());
    }

    @Test
    public void testMovementMethods() {
        Position p = new Position(2, 2);

        assertEquals(new Position(1, 2), p.up());
        assertEquals(new Position(3, 2), p.down());
        assertEquals(new Position(2, 1), p.left());
        assertEquals(new Position(2, 3), p.right());
    }

    @Test
    public void testEqualsAndHashCode() {
        Position p1 = new Position(4, 4);
        Position p2 = new Position(4, 4);
        Position p3 = new Position(5, 4);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
    }

    @Test
    public void testToString() {
        Position p = new Position(1, 7);
        assertEquals("(1, 7)", p.toString());
    }
}