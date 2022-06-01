package polyakova.test.demo.selenium.page;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import polyakova.test.selenium.page.AbstractPage;

/**
 * Main page of the website gazprombank.ru
 *
 * @author Iuliia Poliakova
 */
public class QametaIoHomePage extends AbstractPage {

    @FindBy(xpath = "//section[@id='pricing']")
    private WebElement pricingPanel;

    public QametaIoHomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Save block \"Pricing\"")
    public void screenPricing() throws Exception {
        screen(pricingPanel);
    }

    @Step("Get cloud price")
    public int getCloudPrice() {
        final WebElement priceElement = pricingPanel.findElement(By.xpath(".//span[text()=\"Cloud\"]/../..//div[contains(text(),\"$\")]"));
        return getPrice(priceElement);
    }

    @Step("Get server price")
    public int getServerPrice() {
        final WebElement priceElement = pricingPanel.findElement(By.xpath(".//span[text()=\"Server\"]/../..//div[contains(text(),\"$\")]"));
        return getPrice(priceElement);
    }

    private int getPrice(final WebElement priceElement) {
        final String priceFullStr = priceElement.getText();
        final String priceStr = priceFullStr.substring(1, priceFullStr.length()-4);
        int price = Integer.parseInt(priceStr);
        return price;
    }
}
