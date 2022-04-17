package polyakova.test.demo;

import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Пример использования аннотаций allure
 * <p>
 * https://github.com/allure-examples/allure-junit5-example
 *
 * @author Iuliia Poliakova
 */
@DisplayName("Наименование Suite")
public class TestAllure {
    @Test
    @Story("Наименование User Story")
    @DisplayName("Наименование Test Case")
    @Owner("Разработчик теста")
    @Timeout(40)
    void testOutput() {
        firstStep();
        secondStep("значение параметра");
    }

    @Step("Описание шага")
    private void firstStep() {
        // Пример добавления файла
        Allure.addAttachment("name", "image/png", getClass().getResourceAsStream("/example.png"), ".png");
    }

    @Step("Описание шага с параметром \"{s}\"")
    private void secondStep(String s) {
        assertNotNull(s);
    }
}
