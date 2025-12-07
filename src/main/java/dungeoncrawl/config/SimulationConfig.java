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

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMoveDelayMs() {
        return moveDelayMs;
    }

    public Color getWallColor() {
        return wallColor;
    }

    public Color getFloorColor() {
        return floorColor;
    }

    public Color getAgentColor() {
        return agentColor;
    }

    public int getTileSize() {
        return tileSize;
    }
}