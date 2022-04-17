package polyakova.test.demo.selenium.page;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import polyakova.test.selenium.page.AbstractPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Главная страница сайта gazprombank.ru
 *
 * @author Iuliia Poliakova
 */
public class GazprombankRuHomePage extends AbstractPage {

    @FindBy(xpath = "//h3[text()='В интернет-банке']/../div")
    private WebElement exchangeInOnlineBank;

    public GazprombankRuHomePage(WebDriver driver) {
        super(driver);
    }

    private List<WebElement> exchangeInOnlineBank(String currency) {
        return exchangeInOnlineBank.findElements(By.xpath(".//div[contains(text(),'" + currency + "')]/following::span"));
    }

    public double getCurrencyRateSale(String currency) {
        double sale = Double.parseDouble(exchangeInOnlineBank(currency).get(1).getText());
        return sale;
    }

    public double getCurrencyRatePurchase(String currency) {
        double purchase = Double.parseDouble(exchangeInOnlineBank(currency).get(3).getText());
        return purchase;
    }

    @Step("Сохранить блок \"Курсы валют\"")
    public void screenExchangeInOnlineBank() throws Exception {
        screen(exchangeInOnlineBank);
    }

    @Step("Проверить в блоке «Курс обмена в интернет-банке», что курс продажи больше курса покупки, для USD и для EUR.")
    public void checkCurrencyExchange() {
        final double usdSale = getCurrencyRateSale("USD");
        final double usdPurchase = getCurrencyRatePurchase("USD");
        assertTrue(usdSale > usdPurchase, usdSale + "(usdSale) > " + usdPurchase + "(usdPurchase)");
        final double eurSale = getCurrencyRateSale("EUR");
        final double eurPurchase = getCurrencyRatePurchase("EUR");
        assertTrue(eurSale > eurPurchase, eurSale + "(eurSale) > " + eurPurchase + "(eurPurchase)");
    }
}
