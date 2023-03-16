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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import polyakova.test.selenium.crossbrowser.BrowserName;
import polyakova.test.selenium.crossbrowser.DisplaySize;
import polyakova.test.selenium.page.AbstractPage;
import polyakova.test.utils.SystemPropertyConst;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public static void beforeAll(TestInfo testInfo) throws IOException {
        boolean deleteScreen=Boolean.valueOf(System.getProperty(SystemPropertyConst.TEST_BEFORE_ALL_DELETE_SCREEN, Boolean.TRUE.toString()));
        if (deleteScreen) {
            // deleting test screens before the test, which were received on the previous run
            final File dir = new File("report" + File.separatorChar + "screen" + File.separatorChar + testInfo.getTestClass().get().getCanonicalName().replace('.', '_'));
            FileUtils.deleteDirectory(dir);
        }
    }

    /**
     * Get a folder for downloading files
     *
     * @return directory
     */
    public static File getDownloadDirectory() {
        File tmpdir = new File(System.getProperty("java.io.tmpdir"));
        return tmpdir;
    }

    /**
     * Will be executed before each test function in the class
     */
    @BeforeEach
    public void beforeTest() {
        BrowserName browserName = BrowserName.valueOf(System.getProperty(SystemPropertyConst.TEST_BROWSER, BrowserName.CHROME.name()));
        String testDisplaySize = System.getProperty(SystemPropertyConst.TEST_DISPLAY_SIZE);
        DisplaySize displaySize = (testDisplaySize != null) ? DisplaySize.valueOf(testDisplaySize) : null;
        Boolean displayVisible = "true".equalsIgnoreCase(System.getProperty(SystemPropertyConst.TEST_DISPLAY_VISIBLE, "true"));

        switch (browserName) {
            case CHROME:
                // Download driver
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (!displayVisible) {
                    options.addArguments("--headless");
                    if (displaySize == null) {
                        displaySize = DisplaySize.XGA;
                    }
                    options.addArguments("--window-size=" + displaySize.getWidth() + "," + displaySize.getHeight());
                }
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("download.default_directory", getDownloadDirectory().getAbsolutePath());
                options.setExperimentalOption("prefs", chromePrefs);
                options.addArguments("--remote-allow-origins=*");
                // Open browser
                driver = new ChromeDriver(options);
                break;
            case SAFARI:
                //download driver
                WebDriverManager.safaridriver().setup();
                //open browser
                driver = new SafariDriver();
                break;
            case FIREFOX:
                //download driver
                WebDriverManager.firefoxdriver().setup();
                //open browser
                driver = new FirefoxDriver();
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
                driver = new ChromeDriver();
                break;
            case IE:
                //download driver
                WebDriverManager.iedriver().setup();
                //open browser
                driver = new InternetExplorerDriver();
                break;
        }

        if (displayVisible) {
        // set size of the browser window
        if (displaySize != null) {
            driver.manage().window().setSize(displaySize.getDimension());
        } else {
            // set size of the browser window in full screen
            driver.manage().window().maximize();
        }
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
