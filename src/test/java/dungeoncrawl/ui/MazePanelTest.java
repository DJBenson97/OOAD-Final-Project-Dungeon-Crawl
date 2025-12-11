package dungeoncrawl.ui;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Cell;
import dungeoncrawl.model.Position;
import dungeoncrawl.model.Agent;
import dungeoncrawl.strategy.NavigationStrategy;
import dungeoncrawl.config.SimulationConfig;

import org.junit.jupiter.api.Test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MazePanelTest {

    // simple fake strategy used to control sets
    private static class FakeStrategy implements NavigationStrategy {
        private final Set<Position> visited = new HashSet<>();
        private final Set<Position> frontier = new HashSet<>();
        private final Set<Position> finalPath = new HashSet<>();

        @Override
        public void initialize(Maze maze, Position start) {}
        @Override
        public Position step(Maze maze, Position current) { return null; }
        @Override
        public boolean goalFound() { return false; }

        @Override
        public Set<Position> getVisitedSet() { return visited; }
        @Override
        public Set<Position> getFrontierSet() { return frontier; }
        @Override
        public Set<Position> getFinalPath() { return finalPath; }
    }

    private SimulationConfig smallConfig() {
        return new SimulationConfig(
            3, 3,             // rows, cols
            10,               // delay
            java.awt.Color.BLACK,
            java.awt.Color.WHITE,
            java.awt.Color.RED,
            20                // tile size
        );
    }

    private Graphics makeGraphics() {
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        return img.getGraphics();
    }

    // test that finalpath branch executes in paintcomponent
    @Test
    public void testPaintFinalPathBranch() {
        SimulationConfig config = smallConfig();
        Maze maze = new Maze(3, 3, config);
        Cell goal = maze.getCell(2, 2);
        goal.setGoal(false);

        MazePanel panel = new MazePanel(maze, config);

        FakeStrategy fs = new FakeStrategy();
        fs.finalPath.add(new Position(1, 1));

        Agent agent = new Agent(maze, new Position(0, 0), fs);
        panel.setAgent(agent);

        panel.paintComponent(makeGraphics());
    }

    // test that visited branch executes in paintcomponent
    @Test
    public void testPaintVisitedBranch() {
        SimulationConfig config = smallConfig();
        Maze maze = new Maze(3, 3, config);

        MazePanel panel = new MazePanel(maze, config);

        FakeStrategy fs = new FakeStrategy();
        fs.visited.add(new Position(1, 0));

        Agent agent = new Agent(maze, new Position(0, 0), fs);
        panel.setAgent(agent);

        panel.paintComponent(makeGraphics());
    }

    // test that frontier branch executes in paintcomponent
    @Test
    public void testPaintFrontierBranch() {
        SimulationConfig config = smallConfig();
        Maze maze = new Maze(3, 3, config);

        MazePanel panel = new MazePanel(maze, config);

        FakeStrategy fs = new FakeStrategy();
        fs.frontier.add(new Position(2, 0));

        Agent agent = new Agent(maze, new Position(0, 0), fs);
        panel.setAgent(agent);

        panel.paintComponent(makeGraphics());
    }

    // test that goal branch executes in paintcomponent
    @Test
    public void testPaintGoalBranch() {
        SimulationConfig config = smallConfig();
        Maze maze = new Maze(3, 3, config);

        // make a goal cell at 1,2
        maze.getCell(1, 2).setGoal(true);

        MazePanel panel = new MazePanel(maze, config);

        FakeStrategy fs = new FakeStrategy();
        Agent agent = new Agent(maze, new Position(0, 0), fs);
        panel.setAgent(agent);

        panel.paintComponent(makeGraphics());
    }

    // test that agent oval branch executes in paintcomponent
    @Test
    public void testPaintAgentOval() {
        SimulationConfig config = smallConfig();
        Maze maze = new Maze(3, 3, config);

        MazePanel panel = new MazePanel(maze, config);

        FakeStrategy fs = new FakeStrategy();
        Agent agent = new Agent(maze, new Position(2, 2), fs);
        panel.setAgent(agent);

        panel.paintComponent(makeGraphics());
    }

    // test observer repaint executes
    @Test
    public void testObserverRepaint() {
        SimulationConfig config = smallConfig();
        Maze maze = new Maze(config.getRows(), config.getCols(), config);
        MazePanel panel = new MazePanel(maze, config);

        assertNotNull(panel);

        maze.notifyObservers();
    }
}