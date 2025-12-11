package dungeoncrawl.strategy;

import dungeoncrawl.model.Maze;
import dungeoncrawl.model.Position;

import java.util.*;

public class AStarStrategy implements NavigationStrategy {

    private PriorityQueue<Node> pq;
    private Set<Position> visited;
    private Set<Position> frontier;
    private Set<Position> finalPath;
    private boolean goalFound;
    private Position goal;

    private Map<Position, Position> cameFrom;
    private Map<Position, Integer> gScore;

    private static class Node {
        Position p;
        int f;
        Node(Position p, int f) { this.p = p; this.f = f; }
    }

    @Override
    public void initialize(Maze maze, Position start) {
        visited = new HashSet<>();
        frontier = new HashSet<>();
        finalPath = new HashSet<>();
        goalFound = false;

        cameFrom = new HashMap<>();
        gScore = new HashMap<>();

        goal = findGoal(maze);

        gScore.put(start, 0);
        cameFrom.put(start, null);

        pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        pq.add(new Node(start, h(start)));
        frontier.add(start);
    }

    @Override
    public Position step(Maze maze, Position current) {
        if (pq.isEmpty()) return null;

        Node node = pq.poll();
        Position next = node.p;
        frontier.remove(next);

        if (visited.contains(next)) {
            return current;
        }

        visited.add(next);

        if (next.equals(goal)) {
            goalFound = true;
            buildFinalPath(next);
            return next;
        }

        for (Position nb : neighbors(maze, next)) {
            if (!visited.contains(nb)) {
                int tentative = gScore.get(next) + 1;

                if (!gScore.containsKey(nb) || tentative < gScore.get(nb)) {
                    gScore.put(nb, tentative);
                    int f = tentative + h(nb);
                    pq.add(new Node(nb, f));
                    frontier.add(nb);
                    cameFrom.put(nb, next);
                }
            }
        }

        return next;
    }

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

    private int h(Position p) {
        return Math.abs(p.row() - goal.row()) + Math.abs(p.col() - goal.col());
    }

    // find goal location
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

    // returns valid non wall neighbors
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
}