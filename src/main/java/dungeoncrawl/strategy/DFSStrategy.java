package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.*;

public class DFSStrategy implements NavigationStrategy {

    private Deque<Position> stack;
    private Set<Position> visited;
    private Set<Position> frontier;
    private boolean goalFound;
    private Position goal;

    @Override
    public void initialize(Maze maze, Position start) {
        stack = new ArrayDeque<>();
        visited = new HashSet<>();
        frontier = new HashSet<>();
        goalFound = false;

        // find goal
        goal = findGoal(maze);

        stack.push(start);
        frontier.add(start);
    }

    @Override
    public Position step(Maze maze, Position current) {
        if (stack.isEmpty()) return null;

        Position next = stack.pop();
        frontier.remove(next);

        if (visited.contains(next)) {
            return current;
        }

        visited.add(next);

        if (next.equals(goal)) {
            goalFound = true;
            return next;
        }

        for (Position n : neighbors(maze, next)) {
            if (!visited.contains(n)) {
                stack.push(n);
                frontier.add(n);
            }
        }

        return next;
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

    private List<Position> neighbors(Maze maze, Position p) {
        List<Position> list = new ArrayList<>();
        int r = p.row();
        int c = p.col();

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            Position np = new Position(r + d[0], c + d[1]);
            if (maze.inBounds(np) && !maze.getCell(np).isWall()) {
                list.add(np);
            }
        }
        return list;
    }

    private Position findGoal(Maze maze) {
        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                Position p = new Position(r, c);
                if (maze.getCell(p).isGoal()) return p;
            }
        }
        return null;
    }
}