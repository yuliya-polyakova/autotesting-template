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
 * Example of using allure annotations
 * <p>
 * https://github.com/allure-examples/allure-junit5-example
 *
 * @author Iuliia Poliakova
 */
@DisplayName("Name Suite")
public class TestAllure {
    @Test
    @Story("Name of User Story")
    @DisplayName("Name of Test Case")
    @Owner("Test author")
    @Timeout(40)
    void testOutput() {
        firstStep();
        secondStep("parameter value");
    }

    @Step("Step description")
    private void firstStep() {
        // Example of adding a file
        Allure.addAttachment("name", "image/png", getClass().getResourceAsStream("/example.png"), ".png");
    }

    @Step("Description of a step with a parameter \"{s}\"")
    private void secondStep(String s) {
        assertNotNull(s);
    }
}
