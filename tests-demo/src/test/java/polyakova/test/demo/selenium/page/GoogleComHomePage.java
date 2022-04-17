package polyakova.test.demo.selenium.page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import polyakova.test.selenium.page.AbstractPage;
import polyakova.test.utils.EnvironmentVariables;

/**
 * Главная страница сайта google.com
 *
 * @author Iuliia Poliakova
 */
public class GoogleComHomePage extends AbstractPage {

    public final static String GOOGLE_COM = EnvironmentVariables.getString("ui.google.url");

    @FindBy(xpath = "//input[@name='q']")
    public WebElement queryTextField;

    public GoogleComHomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть https://www.google.com/")
    public static GoogleComHomePage openGoogleCom(WebDriver driver) throws Exception {
        driver.get(GOOGLE_COM);
        final GoogleComHomePage googleComHomePage = new GoogleComHomePage(driver);
        googleComHomePage.screen();
        return googleComHomePage;
    }

    @Step("Ввести в строку поиска \"{text}\"")
    public void setSearchText(String text) throws Exception {
        queryTextField.sendKeys(text);
        screen();
    }

    @Step("Запустить поиск")
    public GoogleComSearchResultPage clickSearchButton() {
        queryTextField.submit();
        return new GoogleComSearchResultPage(driver);
    }
}