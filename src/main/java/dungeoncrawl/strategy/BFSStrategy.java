package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.*;

public class BFSStrategy implements NavigationStrategy {

    private boolean goalFound = false;

    private Queue<Position> frontier;
    private Set<Position> visited;

    @Override
    public void initialize(Maze maze, Position start) {
        frontier = new LinkedList<>();
        visited = new HashSet<>();

        frontier.add(start);
        visited.add(start);

        goalFound = false;
    }

    @Override
    public Position step(Maze maze, Position current) {

        if (goalFound) return current;

        if (frontier.isEmpty()) {
            return current;  // nowhere to go
        }

        // BFS pop
        Position pos = frontier.remove();
        current = pos;

        // goal check based on maze grid
        if (maze.getCell(pos).isGoal()) {
            goalFound = true;
            return pos;
        }

        // neighbors
        for (Position next : getWalkableNeighbors(maze, pos)) {
            if (!visited.contains(next)) {
                visited.add(next);
                frontier.add(next);
            }
        }

        return current;
    }

    private List<Position> getWalkableNeighbors(Maze maze, Position p) {
        List<Position> list = new ArrayList<>();
        int r = p.row();
        int c = p.col();

        int[][] dirs = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };

        for (int[] d : dirs) {
            int nr = r + d[0];
            int nc = c + d[1];

            if (maze.isInBounds(nr, nc) && !maze.getCell(nr, nc).isWall()) {
                list.add(new Position(nr, nc));
            }
        }

        return list;
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
        return new HashSet<>(frontier);
    }
}