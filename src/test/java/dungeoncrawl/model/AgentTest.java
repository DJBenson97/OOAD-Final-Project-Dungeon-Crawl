package dungeoncrawl.model;

import dungeoncrawl.strategy.NavigationStrategy;
import dungeoncrawl.strategy.BFSStrategy;

import dungeoncrawl.config.SimulationConfig;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgentTest {

    @Test
    public void testAgentMoves() {

        SimulationConfig cfg = new SimulationConfig(
                5,
                5,
                200,
                new java.awt.Color(40, 40, 40),
                new java.awt.Color(190, 190, 190),
                java.awt.Color.RED,
                30
        );

        Maze m = new Maze(5, 5, cfg);
        m.generateRandomMaze();

        Position start = new Position(0, 0);
        m.setGoal(new Position(4, 4));

        NavigationStrategy s = new BFSStrategy();
        s.initialize(m, start);

        Agent a = new Agent(m, start, s);

        assertEquals(start, a.getPosition());
    }
}