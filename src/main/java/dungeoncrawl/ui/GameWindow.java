package dungeoncrawl.ui;

import dungeoncrawl.config.SimulationConfig;
import dungeoncrawl.config.SimulationConfigManager;
import dungeoncrawl.model.Agent;
import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;
import dungeoncrawl.state.*;
import dungeoncrawl.strategy.NavigationStrategy;
import dungeoncrawl.factory.NavigationStrategyFactory;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    // current maze instance used by the simulation
    private Maze maze;

    // panel responsible for rendering the maze and overlays
    private MazePanel mazePanel;

    // agent that performs the navigation strategy
    private Agent agent;

    // dropdown for selecting the navigation strategy
    private JComboBox<String> strategyBox;

    // control buttons for simulation actions
    private JButton startButton;
    private JButton clearButton;
    private JButton resetButton;

    // manages the current ui and simulation state
    private final StateManager stateManager;

    // special constructor used only for testing
    protected GameWindow(boolean testingMode) {
        super();
        this.stateManager = new StateManager();

        // create a minimal configuration suitable for tests
        SimulationConfig config = new SimulationConfig(
            5, 5, 20,
            new Color(50, 50, 50),
            new Color(200, 200, 200),
            Color.RED,
            20
        );

        // initialize and generate a simple maze
        this.maze = new Maze(config.getRows(), config.getCols(), config);
        this.maze.generateRandomMaze();

        // create ui controls without attaching listeners
        this.strategyBox = new JComboBox<>(new String[]{"DFS", "BFS", "A*"});
        this.startButton = new JButton("Start");
        this.clearButton = new JButton("Clear");
        this.resetButton = new JButton("Reset Maze");

        // create the maze panel and define the start cell
        this.mazePanel = new MazePanel(maze, config);
        this.mazePanel.setStartCell(new Position(0, 0));

        // enter the initial state
        stateManager.getState().onEnter(this);
    }

    public GameWindow() {

        // set basic window properties
        setTitle("Dungeon Crawl Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        stateManager = new StateManager();

        // load simulation configuration
        SimulationConfig config = SimulationConfigManager.getInstance().getConfig();

        // build and generate the maze
        maze = new Maze(config.getRows(), config.getCols(), config);
        maze.generateRandomMaze();

        // set the goal position in the maze
        Position goal = new Position(config.getRows() - 1, config.getCols() - 1);
        maze.setGoal(goal);

        // create the control panel
        JPanel controls = new JPanel();
        controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // initialize controls
        strategyBox = new JComboBox<>(new String[]{"DFS", "BFS", "A*"});
        startButton = new JButton("Start");
        clearButton = new JButton("Clear");
        resetButton = new JButton("Reset Maze");

        // add controls to the panel
        controls.add(strategyBox);
        controls.add(startButton);
        controls.add(clearButton);
        controls.add(resetButton);

        // place control panel at the top
        add(controls, BorderLayout.NORTH);

        // create and add the maze panel
        mazePanel = new MazePanel(maze, config);
        add(mazePanel, BorderLayout.CENTER);

        // define the starting cell
        mazePanel.setStartCell(new Position(0, 0));

        // connect button actions to state behavior
        startButton.addActionListener(e ->
            stateManager.getState().start(this)
        );

        clearButton.addActionListener(e ->
            stateManager.getState().clear(this)
        );

        resetButton.addActionListener(e ->
            stateManager.getState().resetMaze(this)
        );

        // enter the initial state
        stateManager.getState().onEnter(this);

        // finalize window layout
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // provides access to the state manager
    public StateManager getStateManager() {
        return stateManager;
    }

    // helpers for enabling and disabling ui controls
    public void enableStrategy() { strategyBox.setEnabled(true); }
    public void disableStrategy() { strategyBox.setEnabled(false); }

    public void enableStart() { startButton.setEnabled(true); }
    public void disableStart() { startButton.setEnabled(false); }

    public void enableClear() { clearButton.setEnabled(true); }
    public void disableClear() { clearButton.setEnabled(false); }

    public void enableReset() { resetButton.setEnabled(true); }
    public void disableReset() { resetButton.setEnabled(false); }

    // starts the simulation logic on a background thread
    public void startSimulationInternal() {

        // determine which strategy was selected
        String chosen = (String) strategyBox.getSelectedItem();

        // create the selected navigation strategy
        NavigationStrategy strategy = NavigationStrategyFactory.create(chosen);

        // define the start position
        Position start = new Position(0, 0);

        // create the agent and attach it to the maze panel
        agent = new Agent(maze, start, strategy);
        mazePanel.setAgent(agent);

        // run the agent in a separate thread
        Thread t = new Thread(() -> {
            agent.start();
            SwingUtilities.invokeLater(() ->
                stateManager.setState(new FinishedState(), this)
            );
        });

        t.start();
    }

    // clears all search overlays and agent state
    public void clearSearchData() {
        maze.clearSearchData();
        agent = null;
        mazePanel.setAgent(null);
        maze.notifyObservers();
    }

    // rebuilds the maze and resets all simulation state
    public void resetMazeInternal() {

        // clear any existing strategy overlays
        if (agent != null && agent.getStrategy() != null) {
            agent.getStrategy().getVisitedSet().clear();
            agent.getStrategy().getFrontierSet().clear();
            agent.getStrategy().getFinalPath().clear();
        }

        agent = null;
        mazePanel.clearOverlay();

        // create a fresh maze instance
        SimulationConfig config = SimulationConfigManager.getInstance().getConfig();
        maze = new Maze(config.getRows(), config.getCols(), config);
        maze.generateRandomMaze();

        // reset start and goal positions
        Position start = new Position(0, 0);
        Position goal = new Position(config.getRows() - 1, config.getCols() - 1);
        maze.setGoal(goal);

        // update the maze panel with the new maze
        mazePanel.setMaze(maze);
        mazePanel.setStartCell(start);

        mazePanel.repaint();
    }
}