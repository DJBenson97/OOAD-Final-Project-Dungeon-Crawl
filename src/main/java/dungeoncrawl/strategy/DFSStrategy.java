package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.*;

public class DFSStrategy implements NavigationStrategy {

    private final Deque<Position> stack = new ArrayDeque<>();
    private final Set<Position> visited = new HashSet<>();
    private final Set<Position> frontier = new HashSet<>();
    private boolean goalReached = false;

    @Override
    public void initialize(Maze maze, Position start) {
        stack.clear();
        visited.clear();
        frontier.clear();
        goalReached = false;

        stack.push(start);
        frontier.add(start);
    }

    @Override
    public Position step(Maze maze, Position current) {

        if (stack.isEmpty()) {
            return null;
        }

        Position next = stack.pop();
        frontier.remove(next);

        if (!visited.contains(next)) {
            visited.add(next);

            if (maze.getCell(next).isGoal()) {
                goalReached = true;
            }

            for (Position p : getWalkableNeighbors(maze, next)) {
                if (!visited.contains(p) && !frontier.contains(p)) {
                    frontier.add(p);
                    stack.push(p);
                }
            }
        }

        return next;
    }

    private List<Position> getWalkableNeighbors(Maze maze, Position pos) {
        List<Position> out = new ArrayList<>();

        int r = pos.row();
        int c = pos.col();

        Position down = new Position(r + 1, c);
        Position up = new Position(r - 1, c);
        Position right = new Position(r, c + 1);
        Position left = new Position(r, c - 1);

        if (maze.inBounds(down) && !maze.getCell(down).isWall()) out.add(down);
        if (maze.inBounds(up) && !maze.getCell(up).isWall()) out.add(up);
        if (maze.inBounds(right) && !maze.getCell(right).isWall()) out.add(right);
        if (maze.inBounds(left) && !maze.getCell(left).isWall()) out.add(left);

        return out;
    }

    @Override
    public boolean goalFound() {
        return goalReached;
    }

    @Override
    public Set<Position> getVisitedSet() {
        return visited;
    }

    @Override
    public Set<Position> getFrontierSet() {
        return frontier;
    }
}