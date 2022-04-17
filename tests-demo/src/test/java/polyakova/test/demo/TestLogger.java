package polyakova.test.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Пример использования логирования
 *
 * @author Iuliia Poliakova
 */
public class TestLogger {
    private static final Logger log = LogManager.getLogger(TestLogger.class);

    @Test
    @DisplayName("Пример использования логирования")
    public void test() {
        log.info("Some text");
    }
}
