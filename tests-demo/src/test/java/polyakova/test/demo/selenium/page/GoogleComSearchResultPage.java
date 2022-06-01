package polyakova.test.demo.selenium.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import polyakova.test.selenium.page.AbstractPage;

/**
 * Search results page
 *
 * @author Iuliia Poliakova
 */
public class GoogleComSearchResultPage extends AbstractPage {

    public GoogleComSearchResultPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check that the search results have a link {url}, go to the site")
    public void getLinkByUrlAndClick(String url) {
        waitElementAndClick(By.xpath("//a[@href='" + url + "']"));
    }
}
