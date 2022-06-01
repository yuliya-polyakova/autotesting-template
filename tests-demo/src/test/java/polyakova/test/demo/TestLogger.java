package polyakova.test.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example of using logging
 *
 * @author Iuliia Poliakova
 */
public class TestLogger {
    private static final Logger log = LoggerFactory.getLogger(TestLogger.class);

    @Test
    @DisplayName("Example of using logging")
    public void test() {
        log.info("Some text");
    }
}
