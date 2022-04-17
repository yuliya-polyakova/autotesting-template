package polyakova.test.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polyakova.test.utils.EnvironmentVariables;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Пример использования переменных окружения, необходимых для выполнения теста
 * <p>
 * значения находятся в файле {@code environment_dev.properties}
 *
 * @author Iuliia Poliakova
 */
public class TestEnvironmentVariables {
    // Инициализация переменной (считывание из файла)
    public final String API_URL = EnvironmentVariables.getString("api.users.url");

    @Test
    @DisplayName("Пример использования переменных окружения")
    public void test() {
        // Использование переменной
        assertNotNull(API_URL);
    }
}
