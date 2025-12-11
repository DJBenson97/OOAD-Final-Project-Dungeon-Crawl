package dungeoncrawl.config;

import java.awt.Color;

public interface ColorPalette {

    // returns the color used for maze walls
    Color wallColor();

    // returns the color used for walkable paths
    Color pathColor();

    // returns the color used to draw the agent
    Color agentColor();

    // returns the color used for visited cells
    Color visitedColor();
}