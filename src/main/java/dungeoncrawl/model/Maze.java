package dungeoncrawl.model;

import dungeoncrawl.config.SimulationConfig;
import dungeoncrawl.observers.MazeSubject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze extends MazeSubject {

    // number of rows in the maze
    private final int rows;

    // number of columns in the maze
    private final int cols;

    // grid storing all cells in the maze
    private final Cell[][] grid;

    // random generator used during maze generation
    private final Random random = new Random();

    // configuration values used for rendering and setup
    private final SimulationConfig config;

    public Maze(int rows, int cols, SimulationConfig config) {
        // ensure the config dimensions match the maze dimensions
        if (config.getRows() != rows || config.getCols() != cols) {
            throw new IllegalArgumentException("config rows/cols must match Maze constructor rows/cols");
        }

        this.rows = rows;
        this.cols = cols;
        this.config = config;

        // allocate the grid and initialize all cells as walls
        this.grid = new Cell[rows][cols];
        initializeGrid();
    }

    // fills the grid with wall cells at every position
    private void initializeGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(new Position(r, c), true);
            }
        }
    }

    // generates a random maze layout using recursive carving
    public void generateRandomMaze() {
        carvePassagesFrom(0, 0);
        notifyObservers();
    }

    // recursively carves passages through the grid
    private void carvePassagesFrom(int row, int col) {
        // mark the current cell as open
        grid[row][col].setWall(false);

        // define possible movement directions
        List<int[]> dirs = new ArrayList<>();
        dirs.add(new int[]{1, 0});
        dirs.add(new int[]{-1, 0});
        dirs.add(new int[]{0, 1});
        dirs.add(new int[]{0, -1});

        // randomize direction order to vary maze structure
        java.util.Collections.shuffle(dirs, random);

        for (int[] d : dirs) {
            int newRow = row + d[0] * 2;
            int newCol = col + d[1] * 2;

            // carve a passage if the target cell is valid and still a wall
            if (isInBounds(newRow, newCol) && grid[newRow][newCol].isWall()) {
                grid[row + d[0]][col + d[1]].setWall(false);
                carvePassagesFrom(newRow, newCol);
            }
        }
    }

    // clears all search related flags from every cell
    public void clearSearchData() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = grid[r][c];
                cell.setVisited(false);
                cell.setFrontier(false);
                cell.setFinalPath(false);
            }
        }
        notifyObservers();
    }

    // checks whether the given row and column are inside the maze
    public boolean isInBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    // checks whether a position is inside the maze
    public boolean inBounds(Position p) {
        return isInBounds(p.row(), p.col());
    }

    // returns the cell at the given row and column
    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    // returns the cell at the given position
    public Cell getCell(Position p) {
        return grid[p.row()][p.col()];
    }

    // returns the simulation configuration
    public SimulationConfig getConfig() {
        return config;
    }

    // marks the given position as the goal cell
    public void setGoal(Position p) {
        getCell(p).setGoal(true);
        notifyObservers();
    }

    // removes the goal flag from the given position
    public void clearGoal(Position p) {
        getCell(p).setGoal(false);
        notifyObservers();
    }

    // marks the given position as the start cell
    public void setStart(Position p) {
        getCell(p).setStart(true);
        notifyObservers();
    }

    // removes the start flag from the given position
    public void clearStart(Position p) {
        getCell(p).setStart(false);
        notifyObservers();
    }

    // returns the number of rows in the maze
    public int getRows() { return rows; }

    // returns the number of columns in the maze
    public int getCols() { return cols; }

    // returns the underlying grid of cells
    public Cell[][] getGrid() { return grid; }
}