package dungeoncrawl.factory;

import dungeoncrawl.strategy.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NavigationStrategyFactoryTest {

    @Test
    // tests dfs selection
    public void testCreateDFS() {
        assertTrue(NavigationStrategyFactory.create("DFS") instanceof DFSStrategy);
    }

    @Test
    // tests bfs selection
    public void testCreateBFS() {
        assertTrue(NavigationStrategyFactory.create("BFS") instanceof BFSStrategy);
    }

    @Test
    // tests a* selection
    public void testCreateAStar() {
        assertTrue(NavigationStrategyFactory.create("A*") instanceof AStarStrategy);
    }

    @Test
    // tests default fallback when unknown input is passed
    public void testCreateDefault() {
        assertTrue(NavigationStrategyFactory.create("asdf") instanceof BFSStrategy);
    }
}