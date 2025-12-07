package dungeoncrawl.model;

public class Cell {
    private final Position position;
    private boolean wall;
    private boolean visited;
    private boolean goal;
    private boolean start;

    public Cell(Position position, boolean wall) {
        this.position = position;
        this.wall = wall;
        this.visited = false;
        this.goal = false;
        this.start = false;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}