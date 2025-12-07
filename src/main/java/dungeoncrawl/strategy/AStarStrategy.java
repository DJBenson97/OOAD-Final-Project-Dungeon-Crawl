package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.*;

public class AStarStrategy implements NavigationStrategy {

    private PriorityQueue<Node> pq;
    private Set<Position> visited;
    private Set<Position> frontier;
    private boolean goalFound;
    private Position goal;

    private static class Node {
        Position p;
        int f;
        Node(Position p, int f) { this.p = p; this.f = f; }
    }

    @Override
    public void initialize(Maze maze, Position start) {
        visited = new HashSet<>();
        frontier = new HashSet<>();
        goalFound = false;

        goal = findGoal(maze);

        pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        pq.add(new Node(start, h(start)));
        frontier.add(start);
    }

    @Override
    public Position step(Maze maze, Position current) {
        if (pq.isEmpty()) return null;

        Node n = pq.poll();
        Position next = n.p;
        frontier.remove(next);

        if (visited.contains(next)) {
            return current;
        }

        visited.add(next);

        if (next.equals(goal)) {
            goalFound = true;
            return next;
        }

        for (Position nb : neighbors(maze, next)) {
            if (!visited.contains(nb) && !frontier.contains(nb)) {
                pq.add(new Node(nb, h(nb)));
                frontier.add(nb);
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

    private int h(Position p) {
        return Math.abs(p.row() - goal.row()) + Math.abs(p.col() - goal.col());
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