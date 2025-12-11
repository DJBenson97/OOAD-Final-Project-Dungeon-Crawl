package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.*;

public class BFSStrategy implements NavigationStrategy {

    // queue used to process positions in breadth first order
    private Queue<Position> queue;

    // set of positions that have already been visited
    private Set<Position> visited;

    // set of positions currently waiting to be explored
    private Set<Position> frontier;

    // set representing the final path from start to goal
    private Set<Position> finalPath;

    // flag indicating whether the goal has been reached
    private boolean goalFound;

    // cached goal position in the maze
    private Position goal;

    // map used to reconstruct the path once the goal is found
    private Map<Position, Position> cameFrom;

    @Override
    public void initialize(Maze maze, Position start) {
        // initialize all data structures for a new search
        queue = new LinkedList<>();
        visited = new HashSet<>();
        frontier = new HashSet<>();
        finalPath = new HashSet<>();
        goalFound = false;

        cameFrom = new HashMap<>();

        // locate the goal position in the maze
        goal = findGoal(maze);

        // add the starting position to the queue
        queue.add(start);
        frontier.add(start);

        // starting position has no predecessor
        cameFrom.put(start, null);
    }

    @Override
    public Position step(Maze maze, Position current) {
        // stop if there are no more positions to explore
        if (queue.isEmpty()) return null;

        // retrieve the next position in bfs order
        Position next = queue.poll();
        frontier.remove(next);

        // skip positions that were already visited
        if (visited.contains(next)) {
            return current;
        }

        // mark the position as visited
        visited.add(next);

        // check if the goal has been reached
        if (next.equals(goal)) {
            goalFound = true;
            buildFinalPath(next);
            return next;
        }

        // add valid neighboring positions to the queue
        for (Position n : neighbors(maze, next)) {
            if (!visited.contains(n) && !frontier.contains(n)) {
                queue.add(n);
                frontier.add(n);
                cameFrom.put(n, next);
            }
        }

        // return the newly visited position
        return next;
    }

    // reconstructs the final path by walking backward from the goal
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

    // returns all valid neighboring positions that are not walls
    private List<Position> neighbors(Maze maze, Position p) {
        List<Position> list = new ArrayList<>();
        int r = p.row();
        int c = p.col();

        // possible movement directions
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