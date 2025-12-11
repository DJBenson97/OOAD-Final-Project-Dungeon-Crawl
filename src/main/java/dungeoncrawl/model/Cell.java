package dungeoncrawl.model;

public class Cell {

    // position of this cell within the maze
    private final Position position;

    // indicates whether this cell is a wall
    private boolean wall;

    // indicates whether this cell has been visited during search
    private boolean visited;

    // indicates whether this cell is the goal
    private boolean goal;

    // indicates whether this cell is the start
    private boolean start;

    // indicates whether this cell is currently in the frontier
    private boolean frontier;

    // indicates whether this cell is part of the final path
    private boolean finalPath;

    public Cell(Position position, boolean wall) {
        // assign the cell position and initial wall state
        this.position = position;
        this.wall = wall;

        // initialize all search and marker flags to false
        this.visited = false;
        this.goal = false;
        this.start = false;
        this.frontier = false;
        this.finalPath = false;
    }

    // returns the position of this cell
    public Position getPosition() {
        return position;
    }

    // returns true if this cell is a wall
    public boolean isWall() {
        return wall;
    }

    // sets whether this cell is a wall
    public void setWall(boolean wall) {
        this.wall = wall;
    }

    // returns true if this cell has been visited
    public boolean isVisited() {
        return visited;
    }

    // sets the visited flag for this cell
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // returns true if this cell is marked as the goal
    public boolean isGoal() {
        return goal;
    }

    // sets the goal flag for this cell
    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    // returns true if this cell is marked as the start
    public boolean isStart() {
        return start;
    }

    // sets the start flag for this cell
    public void setStart(boolean start) {
        this.start = start;
    }

    // returns true if this cell is in the frontier
    public boolean isFrontier() {
        return frontier;
    }

    // sets the frontier flag for this cell
    public void setFrontier(boolean frontier) {
        this.frontier = frontier;
    }

    // returns true if this cell is part of the final path
    public boolean isFinalPath() {
        return finalPath;
    }

    // sets the final path flag for this cell
    public void setFinalPath(boolean finalPath) {
        this.finalPath = finalPath;
    }
}