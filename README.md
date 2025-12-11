# Dungeon Crawl
## Object Oriented Analysis and Development Final Project
#### Team Members: David Benson

### Overview
My final project, named Dungeon Crawl, is a Java based maze navigation simulator game that uses multiple design patterns, real-time UI rendering, and pathfinding algorithms. The point of the game is to show the user a visualization of the differences between BFS, DFS, and A* (A Star) algorithms in how they tackle the problem of solving a maze. The game begins by randomly generating a map, after which it allows the user to select their preferred algorithm and visually shows the user the progress of the algorithm as it moves towards the goal.

### Features
- Random maze generation
- Implemented via the Strategy Pattern:
  - Depth-First Search (DFS)
  - Breadth-First Search (BFS)
  - A* Search (A Star)

- The UI shows:
  - Agent/maze start tile (orange)
  - Visited tiles (blue)
  - Frontier tiles (yellow)
  - Final reconstructed path (green)
  - Agent movement (red dot moving through the maze)

- UI implementing Observer Pattern:
- Simulation controls are governed by three states:
  - IdleState - waiting to start
  - RunningState - simulation in progress
  - FinishedState - simulation completed
- The StateManager cleanly transitions between them and updates the UI controls accordingly

- Factory Pattern for Strategy Creation:
  - NavigationStrategyFactory decouples UI selection from specific class instantiation

- High test coverage (>90%)

- Technologies Used:
  - Java 17+
  - Swing for graphical UI
  - JUnit 5 for testing
  - Gradle for build automation and test execution

### How to Run
Using Gradle, running:
./gradlew clean build
will build the project.

Using Gradle, running:
./gradlew run
will boot up an instance of the game.

Using Gradle, running:
./gradlew clean test
will allow for easy running of the test suite.

### Project Structure

<details>
<summary><strong>Project Structure</strong></summary>
## Project Structure

- OOADFinalProject
  - README.md
  - build.gradle
  - settings.gradle
  - gradle.properties
  - gradlew
  - gradlew.bat
  - gradle
    - wrapper
      - gradle-wrapper.jar
      - gradle-wrapper.properties
  - src
    - main
      - java
        - dungeoncrawl
          - Main.java
          - config
            - ColorPalette.java
            - DungeonPalette.java
            - SimulationConfig.java
            - SimulationConfigManager.java
          - factory
            - NavigationStrategyFactory.java
          - model
            - Agent.java
            - Cell.java
            - Maze.java
            - Position.java
          - observers
            - MazeObserver.java
            - MazeSubject.java
          - state
            - GameState.java
            - StateManager.java
            - IdleState.java
            - RunningState.java
            - FinishedState.java
          - strategy
            - NavigationStrategy.java
            - BFSStrategy.java
            - DFSStrategy.java
            - AStarStrategy.java
          - ui
            - GameWindow.java
            - MazePanel.java
    - test
      - java
        - dungeoncrawl
          - MainTest.java
          - config
            - DungeonPaletteTest.java
          - factory
            - NavigationStrategyFactoryTest.java
          - model
            - AgentTest.java
            - CellTest.java
            - MazeTest.java
            - PositionTest.java
          - observers
            - MazeSubjectTest.java
          - state
            - FakeGameWindowHelper.java
            - IdleStateTest.java
            - RunningStateTest.java
            - FinishedStateTest.java
            - StateManagerTest.java
          - strategy
            - BFSStrategyTest.java
            - DFSStrategyTest.java
            - AStarStrategyTest.java
            - NavigationStrategyTest.java
          - ui
            - GameWindowTest.java
            - MazePanelTest.java
</details>

### Design Patterns Implemented
- Strategy Pattern
- Used for pathfinding algorithms:
  - Each algorithm implements NavigationStrategy
  - UI selects strategy dynamically through a factory

- Factory Pattern
  - NavigationStrategyFactory.create(String) returns an appropriate strategy instance, isolating UI from concrete types

- State Pattern
- Simulation uses:
  - IdleState
  - RunningState
  - FinishedState
- Each state determines:
  - Which buttons are enabled
  - What actions are allowed
  - When transitions occur

- Observer Pattern
  - Maze notifies registered listeners when the agent moves, causing UI re-rendering without tight coupling

- Singleton Pattern
  - SimulationConfigManager provides a single, globally accessible instance
  - Loads and stores SimulationConfig
  - Both the UI (GameWindow) and the maze related classes pull configuration from this same instance

- User Configuration
- Users may use SimulationConfig.java to adjust:
  - Maze size
  - Tile size
  - Colors
  - Agent movement delay