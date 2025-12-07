package dungeoncrawl.config;

import java.awt.Color;

public class DungeonPalette implements ColorPalette {

    @Override
    public Color wallColor() {
        return new Color(40, 40, 40);  // dark gray
    }

    @Override
    public Color pathColor() {
        return new Color(200, 200, 200);  // light gray
    }

    @Override
    public Color agentColor() {
        return Color.RED;
    }

    @Override
    public Color visitedColor() {
        return new Color(160, 160, 160);  // mid gray
    }
}