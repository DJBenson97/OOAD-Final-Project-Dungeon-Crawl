package dungeoncrawl.config;

public class SimulationConfigManager {

    // the single instance
    private static SimulationConfigManager instance;

    // the config held by the singleton
    private final SimulationConfig config;

    // private constructor so nobody else can make one
    private SimulationConfigManager() {
        config = new SimulationConfig(
                33,                       // rows
                33,                       // cols
                150,                      // move delay
                new java.awt.Color(40, 40, 40),     // wall
                new java.awt.Color(190, 190, 190),  // floor
                java.awt.Color.RED,                  // agent
                20                                    // tile size
        );
    }

    // global access point
    public static SimulationConfigManager getInstance() {
        if (instance == null) {
            instance = new SimulationConfigManager();
        }
        return instance;
    }

    public SimulationConfig getConfig() {
        return config;
    }
}