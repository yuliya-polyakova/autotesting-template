package polyakova.test.demo.clipboardhealth.page.amazon;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import polyakova.test.demo.clipboardhealth.page.amazon.television.AmazonTelevisionPage;
import polyakova.test.selenium.page.AbstractPanel;

/**
 * Open TV, Appliances and Electronics menu in page of amazon.in
 *
 * @author Iuliia Poliakova
 */

public class AmazonTvAppliancesElectronicsMenu extends AbstractPanel {

    @FindBy(xpath = ".//ul/li/a[text()='Televisions']")
    private WebElement televisionsMenuItem;

    public AmazonTvAppliancesElectronicsMenu(WebDriver driver) {
        super(driver, By.id("hmenu-content"));
        waitElement(By.xpath("//div[text()=\"tv, audio & cameras\"]"));
    }

    @Step("Click on Televisions under Tv, Audio & Cameras sub section.")
    public AmazonTelevisionPage clickTelevisions() {
        televisionsMenuItem.click();
        return new AmazonTelevisionPage(driver);
    }
}
