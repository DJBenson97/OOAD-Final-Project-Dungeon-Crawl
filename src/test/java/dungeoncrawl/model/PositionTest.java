package dungeoncrawl.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    // tests that row and column are stored correctly
    @Test
    public void testPositionValues() {
        Position p = new Position(3, 5);
        assertEquals(3, p.row());
        assertEquals(5, p.col());
    }

    // tests equality between two positions with same values
    @Test
    public void testEqualsTrue() {
        Position p1 = new Position(2, 2);
        Position p2 = new Position(2, 2);
        assertEquals(p1, p2);
    }

    // tests inequality between two positions with different values
    @Test
    public void testEqualsFalse() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(3, 2);
        assertNotEquals(p1, p2);
    }

    // tests that hashcodes match for equal positions
    @Test
    public void testHashCode() {
        Position p1 = new Position(1, 1);
        Position p2 = new Position(1, 1);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}