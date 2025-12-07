package dungeoncrawl.ui;

import dungeoncrawl.config.SimulationConfig;
import dungeoncrawl.model.Agent;
import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;
import dungeoncrawl.strategy.AStarStrategy;
import dungeoncrawl.strategy.BFSStrategy;
import dungeoncrawl.strategy.DFSStrategy;
import dungeoncrawl.strategy.NavigationStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;

public class GameWindow extends JFrame {

    private Maze maze;
    private MazePanel mazePanel;
    private Agent agent;

    public GameWindow() {
        setTitle("Dungeon Crawl Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SimulationConfig config = new SimulationConfig(
                33,
                33,
                200,
                new java.awt.Color(40, 40, 40),
                new java.awt.Color(190, 190, 190),
                java.awt.Color.RED,
                30
        );

        maze = new Maze(config.getRows(), config.getCols(), config);
        maze.generateRandomMaze();

        Position start = new Position(0, 0);
        Position goal = new Position(config.getRows() - 1, config.getCols() - 1);
        maze.setGoal(goal);

        JPanel controls = new JPanel();
        controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<String> strategyBox = new JComboBox<>(
                new String[]{"DFS", "BFS", "A*"}
        );

        JButton startButton = new JButton("Start Simulation");

        controls.add(strategyBox);
        controls.add(startButton);

        add(controls, BorderLayout.NORTH);

        mazePanel = new MazePanel(maze, config);
        add(mazePanel, BorderLayout.CENTER);

        startButton.addActionListener(e -> startSimulation(strategyBox, startButton, start));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startSimulation(JComboBox<String> box, JButton btn, Position start) {

        if (agent != null) {
            return;
        }

        String chosen = (String) box.getSelectedItem();
        NavigationStrategy strategy;

        if (chosen.equals("DFS")) {
            strategy = new DFSStrategy();
        } else if (chosen.equals("BFS")) {
            strategy = new BFSStrategy();
        } else {
            strategy = new AStarStrategy();
        }

        agent = new Agent(maze, start, strategy);
        mazePanel.setAgent(agent);

        btn.setEnabled(false);

        Thread t = new Thread(() -> {
            agent.start();   // fixed

            javax.swing.SwingUtilities.invokeLater(() -> btn.setEnabled(true));
        });

        t.start();
    }
}