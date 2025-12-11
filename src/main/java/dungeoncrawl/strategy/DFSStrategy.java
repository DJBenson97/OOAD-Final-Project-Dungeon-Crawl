package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.*;

public class DFSStrategy implements NavigationStrategy {

    // stack used to implement depth first search behavior
    private Deque<Position> stack;

    // set of positions that have already been visited
    private Set<Position> visited;

    // set of positions currently in the frontier
    private Set<Position> frontier;

    // set representing the final path from start to goal
    private Set<Position> finalPath;

    // flag indicating whether the goal has been found
    private boolean goalFound;

    // cached goal position in the maze
    private Position goal;

    // map used to reconstruct the path once the goal is found
    private Map<Position, Position> cameFrom;

    @Override
    public void initialize(Maze maze, Position start) {
        // initialize all data structures for a new search
        stack = new ArrayDeque<>();
        visited = new HashSet<>();
        frontier = new HashSet<>();
        finalPath = new HashSet<>();
        goalFound = false;

        cameFrom = new HashMap<>();

        // locate the goal position in the maze
        goal = findGoal(maze);

        // push the start position onto the stack
        stack.push(start);
        frontier.add(start);

        // start has no predecessor
        cameFrom.put(start, null);
    }

    @Override
    public Position step(Maze maze, Position current) {
        // if there is nothing left to explore, stop
        if (stack.isEmpty()) return null;

        // take the next position from the stack
        Position next = stack.pop();
        frontier.remove(next);

        // skip already visited positions
        if (visited.contains(next)) {
            return current;
        }

        // mark this position as visited
        visited.add(next);

        // check if the goal has been reached
        if (next.equals(goal)) {
            goalFound = true;
            buildFinalPath(next);
            return next;
        }

        // add valid neighbors to the stack
        for (Position n : neighbors(maze, next)) {
            if (!visited.contains(n) && !frontier.contains(n)) {
                stack.push(n);
                frontier.add(n);
                cameFrom.put(n, next);
            }
        }

        // return the newly visited position
        return next;
    }

    // reconstructs the path from the goal back to the start
    private void buildFinalPath(Position end) {
        Position cur = end;
        while (cur != null) {
            finalPath.add(cur);
            cur = cameFrom.get(cur);
        }
    }

    @Override
    public boolean goalFound() {
        return goalFound;
    }

    @Override
    public Set<Position> getVisitedSet() {
        return visited;
    }

    @Override
    public Set<Position> getFrontierSet() {
        return frontier;
    }

    @Override
    public Set<Position> getFinalPath() {
        return finalPath;
    }

    // scans the maze to locate the goal cell
    private Position findGoal(Maze maze) {
        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                Position p = new Position(r, c);
                if (maze.getCell(p).isGoal()) {
                    return p;
                }
            }
        }
        return null;
    }

    // returns all valid non wall neighboring positions
    private List<Position> neighbors(Maze maze, Position p) {
        List<Position> list = new ArrayList<>();
        int r = p.row();
        int c = p.col();

        // possible movement directions: up, down, left, right
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            Position np = new Position(r + d[0], c + d[1]);
            if (maze.inBounds(np) && !maze.getCell(np).isWall()) {
                list.add(np);
            }
        }
        return list;
    }
}