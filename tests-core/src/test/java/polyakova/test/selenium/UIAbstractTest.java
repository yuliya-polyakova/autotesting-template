package polyakova.test.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import polyakova.test.selenium.crossbrowser.BrowserName;
import polyakova.test.selenium.crossbrowser.DisplaySize;
import polyakova.test.selenium.page.AbstractPage;
import polyakova.test.utils.SystemPropertyConst;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Parent class for all UI tests
 * Before the test, a browser is launched, accessed through the driver variable
 *
 * @author Iuliia Poliakova
 */
public abstract class UIAbstractTest {
    /**
     * Browser Access
     */
    protected WebDriver driver;

    /**
     * Will be executed only once before all test functions in the class
     *
     * @param testInfo Information about the test being run
     * @throws IOException
     */
    @BeforeAll
    public static final void beforeAll(TestInfo testInfo) throws IOException {
        Boolean deleteScreen=Boolean.valueOf(System.getProperty(SystemPropertyConst.TEST_BEFORE_ALL_DELETE_SCREEN, Boolean.TRUE.toString()));
        if (deleteScreen) {
            // deleting test screens before the test, which were received on the previous run
            final File dir = new File("report" + File.separatorChar + "screen" + File.separatorChar + testInfo.getTestClass().get().getCanonicalName().replace('.', '_'));
            FileUtils.deleteDirectory(dir);
        }
    }

    /**
     * Will be executed before each test function in the class
     */
    @BeforeEach
    public void beforeTest() {
        BrowserName browserName = BrowserName.valueOf(System.getProperty(SystemPropertyConst.TEST_BROWSER, BrowserName.CHROME.name()));
        String testDisplaySize = System.getProperty(SystemPropertyConst.TEST_DISPLAY_SIZE);
        DisplaySize displaySize = (testDisplaySize != null) ? DisplaySize.valueOf(testDisplaySize) : null;

        switch (browserName) {
            case CHROME:
                // Download driver
                WebDriverManager.chromedriver().setup();
                // Open browser
                driver = new ChromeDriver();
                break;
            case EDGE:
                //download driver
                WebDriverManager.edgedriver().setup();
                //open browser
                driver = new EdgeDriver();
                break;
            case OPERA:
                //download driver
                WebDriverManager.operadriver().setup();
                //open browser
                driver = new OperaDriver();
                break;
            case IE:
                //download driver
                WebDriverManager.iedriver().setup();
                //open browser
                driver = new InternetExplorerDriver();
                break;
        }

        // set size of the browser window
        if (displaySize != null) {
            driver.manage().window().setSize(displaySize.getDimension());
        } else {
            // set size of the browser window in full screen
            driver.manage().window().maximize();
        }
        // setting the default timeout
        driver.manage().timeouts().implicitlyWait(AbstractPage.DEFAULT_IMPLICITLY_TIMEOUT);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (driver != null) {
                    driver.quit();
                }
            }
        });
    }

    /**
     * Will be executed after each test function in the class
     *
     * @param testInfo Information about the running test case
     */
    @AfterEach
    public void afterTest(TestInfo testInfo) {
        if (driver != null) {
            // save screen
            if (driver instanceof TakesScreenshot) {
                try {
                    final byte[] image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    StringBuilder fileName = new StringBuilder("report").append(File.separatorChar);
                    fileName.append("screen").append(File.separatorChar);
                    fileName.append(testInfo.getTestClass().get().getCanonicalName().replace('.', '_')).append(File.separatorChar);
                    fileName.append(testInfo.getTestMethod().get().getName()).append(File.separatorChar);
                    fileName.append("9999_afterTest.png");
                    File screen=new File(fileName.toString());
                    screen.getParentFile().mkdirs();
                    try (final FileOutputStream out = new FileOutputStream(screen)) {
                        out.write(image);
                    }
                    Allure.addAttachment(fileName.toString(), "image/png", new ByteArrayInputStream(image), ".png");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // close window
            driver.quit();
        }
    }
}
