package dungeoncrawl.factory;

import dungeoncrawl.strategy.*;

public class NavigationStrategyFactory {

    // creates and returns a navigation strategy based on the given type string
    public static NavigationStrategy create(String type) {

        // default to bfs if no type is provided
        if (type == null) {
            return new BFSStrategy();
        }

        // normalize the input string and select the appropriate strategy
        switch (type.trim().toUpperCase()) {
            case "DFS":
                return new DFSStrategy();
            case "BFS":
                return new BFSStrategy();
            case "A*":
            case "ASTAR":
            case "A_STAR":
                return new AStarStrategy();
            default:
                // fallback to bfs for unknown strategy types
                return new BFSStrategy();
        }
    }
}