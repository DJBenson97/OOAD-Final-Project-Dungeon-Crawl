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
import java.util.Set;

public class MazePanel extends JPanel implements MazeObserver {

    private Maze maze;
    private final SimulationConfig config;
    private Agent agent;

    private Position startCell = new Position(0,0);

    private final Color visitedColor = new Color(70, 130, 180);
    private final Color frontierColor = new Color(230, 200, 40);
    private final Color pathColor = new Color(0, 200, 0);
    private final Color startColor = new Color(255, 140, 0);

    // creates a panel that draws the maze and registers itself as an observer
    public MazePanel(Maze maze, SimulationConfig config) {
        this.maze = maze;
        this.config = config;

        maze.addObserver(this);

        int w = maze.getCols() * config.getTileSize();
        int h = maze.getRows() * config.getTileSize();
        setPreferredSize(new Dimension(w, h));
    }

    // sets the current agent being drawn
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    // replaces the maze and re-registers as observer
    public void setMaze(Maze maze) {
        this.maze = maze;
        this.maze.addObserver(this);
        repaint();
    }

    // updates which tile is drawn as the start
    public void setStartCell(Position p) {
        this.startCell = p;
        repaint();
    }

    // clears all overlay drawings such as agent and start cell
    public void clearOverlay() {
        this.agent = null;
        this.startCell = new Position(0,0);
        repaint();
    }

    // called when the maze notifies observers
    @Override
    public void onMazeUpdated() {
        repaint();
    }

    // draws the maze, overlays, visited sets, frontier, path, and agent
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int size = config.getTileSize();

        Set<Position> visited = null;
        Set<Position> frontier = null;
        Set<Position> finalPath = null;

        // obtains overlay data if an agent and strategy exist
        if (agent != null && agent.getStrategy() != null) {
            visited = agent.getStrategy().getVisitedSet();
            frontier = agent.getStrategy().getFrontierSet();
            finalPath = agent.getStrategy().getFinalPath();
        }

        // draws each tile in the maze
        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {

                Position p = new Position(r, c);
                Cell cell = maze.getCell(r, c);

                int x = c * size;
                int y = r * size;

                Color baseColor = cell.isWall() ? config.getWallColor() : config.getFloorColor();
                g.setColor(baseColor);
                g.fillRect(x, y, size, size);

                // draws start cell
                if (p.equals(startCell)) {
                    g.setColor(startColor);
                    g.fillRect(x, y, size, size);
                    continue;
                }

                // draws goal cell
                if (cell.isGoal()) {
                    Color goalColor = config.getFloorColor().brighter();
                    g.setColor(goalColor);
                    g.fillRect(x, y, size, size);
                    continue;
                }

                // draws final path tiles
                if (finalPath != null && finalPath.contains(p)) {
                    g.setColor(pathColor);
                    g.fillRect(x, y, size, size);
                    continue;
                }

                // draws visited tiles
                if (visited != null && visited.contains(p)) {
                    g.setColor(visitedColor);
                    g.fillRect(x, y, size, size);
                    continue;
                }

                // draws frontier tiles
                if (frontier != null && frontier.contains(p)) {
                    g.setColor(frontierColor);
                    g.fillRect(x, y, size, size);
                }
            }
        }

        // draws the agent as a circle
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

    // returns the currently assigned agent
    public Agent getAgent() {
        return agent;
    }
}