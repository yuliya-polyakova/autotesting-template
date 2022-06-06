package polyakova.test.demo.clipboardhealth.page.amazon;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import polyakova.test.selenium.page.AbstractPage;
import polyakova.test.utils.EnvironmentVariables;

/**
 * Main page of amazon.in
 *
 * @author Iuliia Poliakova
 */
public class AmazonPage extends AbstractPage {

    public final static String AMAZON_URL = EnvironmentVariables.getString("ui.amazon.url");

    @FindBy(id = "nav-hamburger-menu")
    public WebElement hamburgerMenu;

    public AmazonPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open https://www.amazon.in/")
    public static AmazonPage openAmazonIn(WebDriver driver) throws Exception {
        driver.get(AMAZON_URL);
        final AmazonPage amazonPage = new AmazonPage(driver);
        amazonPage.screen();
        return amazonPage;
    }

    @Step("Click on the 'hamburger menu' in the top left corner.")
    public AmazonLeftMenu clickHamburgerMenu() {
        hamburgerMenu.click();
        return new AmazonLeftMenu(driver);
    }
}