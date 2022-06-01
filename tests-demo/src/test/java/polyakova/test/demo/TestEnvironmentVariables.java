package polyakova.test.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polyakova.test.utils.EnvironmentVariables;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Example of using the environment variables required to run the test
 * <p>
 * the values are in the file {@code environment_dev.properties}
 *
 * @author Iuliia Poliakova
 */
public class TestEnvironmentVariables {
    // variable initialization (reading from a file)
    public final String API_URL = EnvironmentVariables.getString("api.users.url");

    @Test
    @DisplayName("Example of using environment variables")
    public void test() {
        // using a Variable
        assertNotNull(API_URL);
    }
}
