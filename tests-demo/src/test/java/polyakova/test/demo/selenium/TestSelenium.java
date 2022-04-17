package polyakova.test.demo.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import polyakova.test.demo.selenium.page.GazprombankRuHomePage;
import polyakova.test.demo.selenium.page.GoogleComHomePage;
import polyakova.test.demo.selenium.page.GoogleComSearchResultPage;
import polyakova.test.selenium.UIAbstractTest;

/**
 * UI тестирование
 *
 * @author Iuliia Poliakova
 */
@DisplayName("Пример selenium теста")
public class TestSelenium extends UIAbstractTest {

    /**
     * Пример:
     * <ol>
     *     <li>Запустить Chrome</li>
     *     <li>Открыть https://www.google.com/</li>
     *     <li>Написать в строке поиска «ufpghjv,fyr» ("газпромбанк" в английской раскладке)</li>
     *     <li>Запустить поиск</li>
     *     <li>Проверить, что результатах поиска есть https://www.gazprombank.ru</li>
     *     <li>Перейти на сайт https://www.gazprombank.ru</li>
     *     <li>Проверить в блоке «Курс обмена в интернет-банке», что курс продажи больше курса покупки, для USD и для EUR.</li>
     * </ol>
     */
    @Test
    @DisplayName("Проверка курса валют")
    @Tag("cross_browser")
    public void test() throws Exception {
        final GoogleComHomePage googleComHomePage = GoogleComHomePage.openGoogleCom(driver);
        googleComHomePage.setSearchText("ufpghjv,fyr"); // «ufpghjv,fyr» ("газпромбанк" в английской раскладке)
        final GoogleComSearchResultPage googleComSearchResultPage = googleComHomePage.clickSearchButton();
        final GazprombankRuHomePage gazprombankRuHomePage = googleComSearchResultPage.clickGazprombankRu();
        gazprombankRuHomePage.screenExchangeInOnlineBank();
        gazprombankRuHomePage.checkCurrencyExchange();
    }
}
