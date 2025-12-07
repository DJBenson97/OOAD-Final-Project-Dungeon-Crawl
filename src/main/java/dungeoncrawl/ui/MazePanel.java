package dungeoncrawl.ui;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Cell;
import dungeoncrawl.model.Position;
import dungeoncrawl.model.Agent;
import dungeoncrawl.observers.MazeObserver;
import dungeoncrawl.config.SimulationConfig;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class MazePanel extends JPanel implements MazeObserver {

    private final Maze maze;
    private final SimulationConfig config;
    private Agent agent;

    public MazePanel(Maze maze, SimulationConfig config) {
        this.maze = maze;
        this.config = config;

        maze.addObserver(this);

        int w = maze.getCols() * config.getTileSize();
        int h = maze.getRows() * config.getTileSize();
        setPreferredSize(new Dimension(w, h));
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public void onMazeUpdated() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int size = config.getTileSize();

        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                Cell cell = maze.getCell(r, c);

                Color color;
                if (cell.isWall()) {
                    color = config.getWallColor();
                } else if (cell.isGoal()) {
                    color = config.getFloorColor().brighter();
                } else {
                    color = config.getFloorColor();
                }

                g.setColor(color);
                g.fillRect(c * size, r * size, size, size);
            }
        }

        if (agent != null) {
            Position p = agent.getPosition();
            g.setColor(config.getAgentColor());
            g.fillOval(
                p.col() * size + size / 4,
                p.row() * size + size / 4,
                size / 2,
                size / 2
            );
        }
    }
}