package polyakova.test.demo.selenium.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import polyakova.test.selenium.page.AbstractPage;
import polyakova.test.utils.EnvironmentVariables;

/**
 * Страница с результатами поиска
 *
 * @author Iuliia Poliakova
 */
public class GoogleComSearchResultPage extends AbstractPage {

    public final String GAZPROMBANK_RU = EnvironmentVariables.getString("ui.gazprombank.url");

    public GoogleComSearchResultPage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверить, что результатах поиска есть ссылка {url}, перейти на сайт")
    public void getLinkByUrlAndClick(String url) {
        WebElement link = driver.findElement(By.xpath("//a[@href='" + url + "']"));
        link.click();
    }

    @Step("Перейти на сайт gazprombank.ru")
    public GazprombankRuHomePage clickGazprombankRu() throws Exception {
        getLinkByUrlAndClick(GAZPROMBANK_RU);
        GazprombankRuHomePage gazprombankRuHomePage = new GazprombankRuHomePage(driver);
        gazprombankRuHomePage.screen();
        return gazprombankRuHomePage;
    }
}
