package polyakova.test.demo.clipboardhealth.page.amazon.television;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Quotes;
import polyakova.test.demo.clipboardhealth.page.amazon.AmazonPage;
import polyakova.test.demo.clipboardhealth.page.amazon.SortEnum;
import polyakova.test.demo.clipboardhealth.page.amazon.item.AmazonItemPage;

/**
 * Television page of amazon.in
 *
 * @author Iuliia Poliakova
 */
public class AmazonTelevisionPage extends AmazonPage {

    // TODO move to AmazonInTelevisionLeftMenu
    @FindBy(xpath = "//a/span[text()='Samsung']")
    public WebElement brandSamsungCheckbox;

    @FindBy(xpath = "//select[@id='s-result-sort-select']/..")
    public WebElement sortSelect;

    public AmazonTelevisionPage(WebDriver driver) {
        super(driver);
    }

    @Step("Scroll down and filter the results by Brand ‘Samsung’.")
    public void clickBrandSamsung() {
        brandSamsungCheckbox.click();
    }

    @Step("Sort the Samsung results with {sort}.")
    public void clickSortAndSelect(SortEnum sort) {
        sortSelect.click();
        waitElementAndClick(By.xpath("//a[text()=" + Quotes.escape(sort.getLabel()) + "]"));
        waitInvisibilityElement(By.xpath("//div[@class=\"a-spinner-wrapper\"]"));
    }

    @Step("Click on {number} item")
    public AmazonItemPage clickItemByNumber(int number) {
        waitElementAndClick(By.xpath("//div[@data-cel-widget=\"search_result_"+number+"\"]//h2/a"));
        switchToLastTab();
        final AmazonItemPage amazonItemPage = new AmazonItemPage(driver);
        return amazonItemPage;
    }
}