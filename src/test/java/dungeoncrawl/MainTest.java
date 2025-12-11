package dungeoncrawl;

import dungeoncrawl.ui.GameWindow;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainTest {

    // test that main invokes GameWindow constructor without throwing
    @Test
    public void testMainRunsWithoutError() {

        // ensure the test mode constructor exists
        assertDoesNotThrow(() -> {
            Constructor<GameWindow> c =
                    GameWindow.class.getDeclaredConstructor(boolean.class);
            // it should be protected, not private
            if (Modifier.isPrivate(c.getModifiers())) {
                throw new IllegalStateException(
                        "test-mode GameWindow(boolean) constructor is private; expected protected");
            }
        });

        // call main and ensure it does not throw
        assertDoesNotThrow(() -> {
            // call main; this will invoke the GameWindow no-arg constructor
            // builds real UI but the JVM test runner suppresses GUI display
            Main.main(new String[]{});
        });
    }
}