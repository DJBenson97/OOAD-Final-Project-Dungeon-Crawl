package dungeoncrawl.model;

import dungeoncrawl.config.SimulationConfig;
import dungeoncrawl.observers.MazeSubject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze extends MazeSubject {

    private final int rows;
    private final int cols;
    private final Cell[][] grid;
    private final Random random = new Random();
    private final SimulationConfig config;

    public Maze(int rows, int cols, SimulationConfig config) {
        if (config.getRows() != rows || config.getCols() != cols) {
            throw new IllegalArgumentException("config rows/cols must match Maze constructor rows/cols");
        }

        this.rows = rows;
        this.cols = cols;
        this.config = config;

        this.grid = new Cell[rows][cols];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(new Position(r, c), true);
            }
        }
    }

    public void generateRandomMaze() {
        carvePassagesFrom(0, 0);
        notifyObservers();
    }

    private void carvePassagesFrom(int row, int col) {
        grid[row][col].setWall(false);

        List<int[]> dirs = new ArrayList<>();
        dirs.add(new int[]{1, 0});
        dirs.add(new int[]{-1, 0});
        dirs.add(new int[]{0, 1});
        dirs.add(new int[]{0, -1});

        java.util.Collections.shuffle(dirs, random);

        for (int[] d : dirs) {
            int newRow = row + d[0] * 2;
            int newCol = col + d[1] * 2;

            if (isInBounds(newRow, newCol) && grid[newRow][newCol].isWall()) {
                grid[row + d[0]][col + d[1]].setWall(false);
                carvePassagesFrom(newRow, newCol);
            }
        }
    }

    public boolean isInBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public boolean inBounds(Position p) {
        return isInBounds(p.row(), p.col());
    }

    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    public Cell getCell(Position p) {
        return grid[p.row()][p.col()];
    }

    public SimulationConfig getConfig() {
        return config;
    }

    public void setGoal(Position p) {
        getCell(p).setGoal(true);
        notifyObservers();
    }

    public void clearGoal(Position p) {
        getCell(p).setGoal(false);
        notifyObservers();
    }

    public void setStart(Position p) {
        getCell(p).setStart(true);
        notifyObservers();
    }

    public void clearStart(Position p) {
        getCell(p).setStart(false);
        notifyObservers();
    }

    public void resetVisited() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c].setVisited(false);
            }
        }
        notifyObservers();
    }

    public int getRows() { 
        return rows; 
    }

    public int getCols() { 
        return cols; 
    }

    public Cell[][] getGrid() { 
        return grid; 
    }
}