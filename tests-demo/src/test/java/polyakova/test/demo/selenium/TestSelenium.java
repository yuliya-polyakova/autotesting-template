package polyakova.test.demo.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import polyakova.test.demo.selenium.page.GoogleComHomePage;
import polyakova.test.demo.selenium.page.GoogleComSearchResultPage;
import polyakova.test.demo.selenium.page.QametaIoHomePage;
import polyakova.test.selenium.UIAbstractTest;
import polyakova.test.utils.EnvironmentVariables;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * UI Testing
 *
 * @author Iuliia Poliakova
 */
@DisplayName("Example selenium test")
public class TestSelenium extends UIAbstractTest {

    public final String QAMETA_URL = EnvironmentVariables.getString("ui.qameta.url");

    /**
     * Example:
     * <ol>
     *     <li>run Chrome</li>
     *     <li>open https://www.google.com/</li>
     *     <li>Enter in search input «alluretestops»</li>
     *     <li>run searching</li>
     *     <li>Check that result od searching has "https://qameta.io/"</li>
     *     <li>open site https://qameta.io/</li>
     *     <li>Check in the "Pricing" block that the price of an account in the cloud is sold  higher than in the server.</li>
     * </ol>
     */
    @Test
    @DisplayName("Check account price")
    @Tags(@Tag("cross_browser"))
    public void test() throws Exception {
        final GoogleComHomePage googleComHomePage = GoogleComHomePage.openGoogleCom(driver);
        googleComHomePage.setSearchText("alluretestops");
        final GoogleComSearchResultPage googleComSearchResultPage = googleComHomePage.clickSearchButton();
        googleComSearchResultPage.getLinkByUrlAndClick(QAMETA_URL);
        final QametaIoHomePage qametaIoHomePage = new QametaIoHomePage(driver);
        qametaIoHomePage.screenPricing();
        final int cloudPrice = qametaIoHomePage.getCloudPrice();
        final int serverPrice = qametaIoHomePage.getServerPrice();
        assertTrue(cloudPrice > serverPrice, cloudPrice + "(cloud price) > " + serverPrice + "(server price)");
    }
}
