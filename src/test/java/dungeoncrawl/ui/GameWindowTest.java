package dungeoncrawl.ui;

import dungeoncrawl.state.*;
import dungeoncrawl.model.*;
import dungeoncrawl.strategy.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class GameWindowTest {

    // helper to extract private fields via reflection
    private Object getPrivateField(Object obj, String name) {
        try {
            Field f = obj.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // tests that getStateManager returns the internal state manager
    @Test
    public void testGetStateManager() {
        GameWindow gw = new GameWindow(true);
        assertNotNull(gw.getStateManager());
        assertTrue(gw.getStateManager().getState() instanceof IdleState);
    }

    // tests that enable and disable strategy work correctly
    @Test
    public void testStrategyEnableDisable() {
        GameWindow gw = new GameWindow(true);

        JComboBox<?> strategyBox = (JComboBox<?>) getPrivateField(gw, "strategyBox");

        gw.enableStrategy();
        assertTrue(strategyBox.isEnabled());

        gw.disableStrategy();
        assertFalse(strategyBox.isEnabled());
    }

    // tests that enable and disable start work correctly
    @Test
    public void testStartEnableDisable() {
        GameWindow gw = new GameWindow(true);
        JButton btn = (JButton) getPrivateField(gw, "startButton");

        gw.enableStart();
        assertTrue(btn.isEnabled());

        gw.disableStart();
        assertFalse(btn.isEnabled());
    }

    // tests that enable and disable clear work correctly
    @Test
    public void testClearEnableDisable() {
        GameWindow gw = new GameWindow(true);
        JButton btn = (JButton) getPrivateField(gw, "clearButton");

        gw.enableClear();
        assertTrue(btn.isEnabled());

        gw.disableClear();
        assertFalse(btn.isEnabled());
    }

    // tests that enable and disable reset work correctly
    @Test
    public void testResetEnableDisable() {
        GameWindow gw = new GameWindow(true);
        JButton btn = (JButton) getPrivateField(gw, "resetButton");

        gw.enableReset();
        assertTrue(btn.isEnabled());

        gw.disableReset();
        assertFalse(btn.isEnabled());
    }

    // tests that clearSearchData sets agent to null and clears the panel reference
    @Test
    public void testClearSearchData() {
        GameWindow gw = new GameWindow(true);

        Maze maze = (Maze) getPrivateField(gw, "maze");
        MazePanel panel = (MazePanel) getPrivateField(gw, "mazePanel");

        Agent agent = new Agent(maze, new Position(0,0), new BFSStrategy());
        Field agentField;
        try {
            agentField = gw.getClass().getDeclaredField("agent");
            agentField.setAccessible(true);
            agentField.set(gw, agent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        panel.setAgent(agent);

        gw.clearSearchData();

        Agent updatedAgent = (Agent) getPrivateField(gw, "agent");
        assertNull(updatedAgent);
        assertNull(panel.getAgent());
    }

    // tests that resetMazeInternal produces a new maze instance
    @Test
    public void testResetMazeInternal() {
        GameWindow gw = new GameWindow(true);

        Maze mazeBefore = (Maze) getPrivateField(gw, "maze");

        gw.resetMazeInternal();

        Maze mazeAfter = (Maze) getPrivateField(gw, "maze");

        assertNotSame(mazeBefore, mazeAfter);
    }
}