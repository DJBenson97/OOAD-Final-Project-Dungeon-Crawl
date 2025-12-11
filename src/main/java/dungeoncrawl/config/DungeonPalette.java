package dungeoncrawl.config;

import java.awt.Color;

public class DungeonPalette implements ColorPalette {

    // returns the color used for maze walls
    @Override
    public Color wallColor() {
        return new Color(40, 40, 40);  // dark gray
    }

    // returns the color used for open paths
    @Override
    public Color pathColor() {
        return new Color(200, 200, 200);  // light gray
    }

    // returns the color used to draw the agent
    @Override
    public Color agentColor() {
        return Color.RED;
    }

    // returns the color used for visited cells
    @Override
    public Color visitedColor() {
        return new Color(160, 160, 160);  // mid gray
    }
}