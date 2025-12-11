package dungeoncrawl.config;

import java.awt.Color;

public class SimulationConfig {

    private final int rows;
    private final int cols;
    private final int moveDelayMs;
    private final Color wallColor;
    private final Color floorColor;
    private final Color agentColor;
    private final int tileSize;

    // stores the configuration settings used for building and running the simulation
    public SimulationConfig(
            int rows,
            int cols,
            int moveDelayMs,
            Color wallColor,
            Color floorColor,
            Color agentColor,
            int tileSize
    ) {
        this.rows = rows;
        this.cols = cols;
        this.moveDelayMs = moveDelayMs;
        this.wallColor = wallColor;
        this.floorColor = floorColor;
        this.agentColor = agentColor;
        this.tileSize = tileSize;
    }

    // returns the number of maze rows
    public int getRows() {
        return rows;
    }

    // returns the number of maze columns
    public int getCols() {
        return cols;
    }

    // returns movement delay for the agent
    public int getMoveDelayMs() {
        return moveDelayMs;
    }

    // returns floor color for non wall tiles
    public Color getWallColor() {
        return wallColor;
    }

    // returns background color used for walkable tiles
    public Color getFloorColor() {
        return floorColor;
    }

    // returns color used to draw the agent
    public Color getAgentColor() {
        return agentColor;
    }

    // returns pixel size of each maze tile
    public int getTileSize() {
        return tileSize;
    }
}
