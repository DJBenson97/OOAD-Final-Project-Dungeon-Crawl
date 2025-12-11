package dungeoncrawl.model;

import dungeoncrawl.config.SimulationConfig;
import dungeoncrawl.strategy.NavigationStrategy;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AgentTest {

    private static class FakeMaze extends Maze {
        int notifyCount = 0;

        public FakeMaze(SimulationConfig c) {
            super(c.getRows(), c.getCols(), c);
        }

        @Override
        public void notifyObservers() {
            notifyCount++;
        }
    }

    private static class FakeStrategy implements NavigationStrategy {

        List<Position> steps = new ArrayList<>();
        boolean goal = false;

        @Override
        public void initialize(Maze maze, Position start) {}

        @Override
        public Position step(Maze maze, Position current) {
            if (steps.isEmpty()) return null;
            return steps.remove(0);
        }

        @Override
        public boolean goalFound() {
            return goal;
        }

        @Override
        public Set<Position> getVisitedSet() { return Set.of(); }

        @Override
        public Set<Position> getFrontierSet() { return Set.of(); }

        @Override
        public Set<Position> getFinalPath() { return Set.of(); }
    }

    @Test
    public void testAgentInitialization() {
        SimulationConfig config = new SimulationConfig(
                5, 5, 0,
                new Color(40,40,40),
                new Color(190,190,190),
                Color.RED,
                20
        );

        Maze maze = new Maze(5, 5, config);
        Position start = new Position(0, 0);
        FakeStrategy strategy = new FakeStrategy();

        Agent a = new Agent(maze, start, strategy);

        assertEquals(start, a.getPosition());
        assertEquals(strategy, a.getStrategy());
        assertEquals(maze, a.getMaze());
    }

    @Test
    public void testStartEarlyReturnWhenAlreadyRunning() throws Exception {
        SimulationConfig config = new SimulationConfig(
                5, 5, 0,
                Color.BLACK, Color.GRAY, Color.RED,
                20
        );

        FakeMaze maze = new FakeMaze(config);
        FakeStrategy strat = new FakeStrategy();

        strat.steps.add(new Position(0,1)); // one step
        // no goal yet

        Agent a = new Agent(maze, new Position(0,0), strat);

        // run first time
        a.start();
        maze.notifyCount = 0;

        // force agent to pretend it's still running
        Field f = a.getClass().getDeclaredField("running");
        f.setAccessible(true);
        f.setBoolean(a, true);

        // second call must immediately return
        a.start();

        assertEquals(0, maze.notifyCount);
    }

    @Test
    public void testStartMovesAgentThroughSteps() {
        SimulationConfig config = new SimulationConfig(
                5, 5, 0,
                Color.BLACK, Color.GRAY, Color.RED,
                20
        );

        FakeMaze maze = new FakeMaze(config);
        FakeStrategy strat = new FakeStrategy();

        // let steps run before set goal=true
        strat.steps.add(new Position(0,1));
        strat.steps.add(new Position(0,2));

        Agent a = new Agent(maze, new Position(0,0), strat);
        a.start();

        assertEquals(new Position(0,2), a.getPosition());
        assertTrue(maze.notifyCount >= 2);
    }
}