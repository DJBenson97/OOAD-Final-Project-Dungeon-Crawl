package dungeoncrawl.config;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonPaletteTest {

    // tests that wallColor returns correct color
    @Test
    public void testWallColor() {
        DungeonPalette p = new DungeonPalette();
        Color c = p.wallColor();
        assertEquals(new Color(40, 40, 40), c);
    }

    // tests that pathColor returns correct color
    @Test
    public void testPathColor() {
        DungeonPalette p = new DungeonPalette();
        Color c = p.pathColor();
        assertEquals(new Color(200, 200, 200), c);
    }

    // tests that agentColor returns correct color
    @Test
    public void testAgentColor() {
        DungeonPalette p = new DungeonPalette();
        Color c = p.agentColor();
        assertEquals(Color.RED, c);
    }

    // tests that visitedColor returns correct color
    @Test
    public void testVisitedColor() {
        DungeonPalette p = new DungeonPalette();
        Color c = p.visitedColor();
        assertEquals(new Color(160, 160, 160), c);
    }
}