package polyakova.test.demo.clipboardhealth.page.amazon.item;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import polyakova.test.selenium.page.AbstractPage;

/**
 * Item page of amazon.in
 *
 * @author Iuliia Poliakova
 */
public class AmazonItemPage extends AbstractPage {

    @FindBy(xpath = "//h1[normalize-space(text())='About this item']")
    public WebElement aboutHeader;

    public AmazonItemPage(WebDriver driver) {
        super(driver);
    }

    @Step("Assert that “About this item” section is present.")
    public void checkAboutHeader() {
        aboutHeader.isDisplayed();
    }

    @Step("Log “About this item” section text to report.")
    public void saveAbout() throws Exception {
        final WebElement about = waitElement(aboutHeader, By.xpath("./following-sibling::ul"));
        screen(about);
        final String text = about.getText();
        Allure.addAttachment("About this item", "text/plain", text, ".txt");
    }
}