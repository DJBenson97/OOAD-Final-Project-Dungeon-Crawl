package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.*;

public class AStarStrategy implements NavigationStrategy {

    private final PriorityQueue<Node> pq = new PriorityQueue<>();
    private final Set<Position> visited = new HashSet<>();
    private final Set<Position> frontier = new HashSet<>();
    private final Map<Position, Integer> gCost = new HashMap<>();
    private Position goalPos;
    private boolean goalReached = false;

    private static class Node implements Comparable<Node> {
        final Position pos;
        final int f;

        Node(Position p, int f) {
            this.pos = p;
            this.f = f;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }
    }

    @Override
    public void initialize(Maze maze, Position start) {
        pq.clear();
        visited.clear();
        frontier.clear();
        gCost.clear();

        goalReached = false;

        // find the goal
        this.goalPos = null;
        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                Position p = new Position(r, c);
                if (maze.getCell(p).isGoal()) {
                    goalPos = p;
                    break;
                }
            }
            if (goalPos != null) break;
        }

        gCost.put(start, 0);
        pq.add(new Node(start, heuristic(start)));
        frontier.add(start);
    }

    @Override
    public Position step(Maze maze, Position currentUnused) {

        if (pq.isEmpty()) {
            return null;
        }

        Node node = pq.poll();
        Position next = node.pos;
        frontier.remove(next);

        if (visited.contains(next)) {
            return next;
        }

        visited.add(next);

        if (next.equals(goalPos)) {
            goalReached = true;
        }

        for (Position p : neighbors(maze, next)) {
            if (visited.contains(p)) continue;

            int newCost = gCost.get(next) + 1;

            if (!gCost.containsKey(p) || newCost < gCost.get(p)) {
                gCost.put(p, newCost);
                int f = newCost + heuristic(p);
                pq.add(new Node(p, f));

                if (!frontier.contains(p)) {
                    frontier.add(p);
                }
            }
        }

        return next;
    }

    private int heuristic(Position p) {
        if (goalPos == null) return 0;
        return Math.abs(p.row() - goalPos.row()) + Math.abs(p.col() - goalPos.col());
    }

    private List<Position> neighbors(Maze maze, Position pos) {
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