package polyakova.test.demo.clipboardhealth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polyakova.test.demo.clipboardhealth.page.amazon.AmazonLeftMenu;
import polyakova.test.demo.clipboardhealth.page.amazon.AmazonPage;
import polyakova.test.demo.clipboardhealth.page.amazon.AmazonTvAppliancesElectronicsMenu;
import polyakova.test.demo.clipboardhealth.page.amazon.SortEnum;
import polyakova.test.demo.clipboardhealth.page.amazon.item.AmazonItemPage;
import polyakova.test.demo.clipboardhealth.page.amazon.television.AmazonTelevisionPage;
import polyakova.test.selenium.UIAbstractTest;

/**
 * ClipboardHealth test
 *
 * @author Iuliia Poliakova
 */
@DisplayName("Example selenium test")
public class ClipboardHealthTest extends UIAbstractTest {


    /**
     * Automation Coding Challenge <a href="https://github.com/ClipboardHealth/vanilla/blob/main/docs/assignment.md">link</a>
     */
    @Test
    @DisplayName("ClipboardHealthTest")
    public void test() throws Exception {
        final AmazonPage amazonPage = AmazonPage.openAmazonIn(driver);
        final AmazonLeftMenu amazonLeftMenu = amazonPage.clickHamburgerMenu();
        final AmazonTvAppliancesElectronicsMenu amazonTvAppliancesElectronicsMenu = amazonLeftMenu.clickTvAppliancesElectronics();
        final AmazonTelevisionPage amazonTelevisionPage = amazonTvAppliancesElectronicsMenu.clickTelevisions();
        amazonTelevisionPage.clickBrandSamsung();
        amazonTelevisionPage.clickSortAndSelect(SortEnum.PriceHighToLow);
        final AmazonItemPage amazonItemPage = amazonTelevisionPage.clickItemByNumber(2);
        amazonItemPage.checkAboutHeader();
        amazonItemPage.saveAbout();
    }
}
