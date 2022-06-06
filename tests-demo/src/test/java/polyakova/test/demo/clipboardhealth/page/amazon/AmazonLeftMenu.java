package polyakova.test.demo.clipboardhealth.page.amazon;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import polyakova.test.selenium.page.AbstractPanel;

/**
 * Open left menu in page of amazon.in
 *
 * @author Iuliia Poliakova
 */
public class AmazonLeftMenu extends AbstractPanel {

    @FindBy(xpath = "./ul/li/a/div[text()='TV, Appliances, Electronics']")
    private WebElement tvAppliancesElectronicsMenuItem;

    public AmazonLeftMenu(WebDriver driver) {
        super(driver, By.id("hmenu-content"));
        waitElement(By.id("hmenu-customer-name"));
    }

    @Step("Scroll own and then Click on the TV, Appliances and Electronics link under Shop by Department section.")
    public AmazonTvAppliancesElectronicsMenu clickTvAppliancesElectronics() {
        tvAppliancesElectronicsMenuItem.click();
        return new AmazonTvAppliancesElectronicsMenu(driver);
    }
}