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
 * Родительский класс для всех UI тестов
 * Перед тестом запускается браузер, доступ к которому осуществляется через переменную driver
 *
 * @author Iuliia Poliakova
 */
public abstract class UIAbstractTest {
    /**
     * Доступ к браузеру
     */
    protected WebDriver driver;

    /**
     * Будет выполняться только один раз перед всеми тестовыми функциями в классе
     *
     * @param testInfo Информация о запускаемом тесте
     * @throws IOException
     */
    @BeforeAll
    public static final void beforeAll(TestInfo testInfo) throws IOException {
        Boolean deleteScreen=Boolean.valueOf(System.getProperty(SystemPropertyConst.TEST_BEFORE_ALL_DELETE_SCREEN, Boolean.TRUE.toString()));
        if (deleteScreen) {
            // удаление скринов теста перед тестом, которые были получены на предыдущем запуске
            final File dir = new File("report" + File.separatorChar + "screen" + File.separatorChar + testInfo.getTestClass().get().getCanonicalName().replace('.', '_'));
            FileUtils.deleteDirectory(dir);
        }
    }

    /**
     * Будет выполняться перед каждой тестовой функцией в классе
     */
    @BeforeEach
    public void beforeTest() {
        BrowserName browserName = BrowserName.valueOf(System.getProperty(SystemPropertyConst.TEST_BROWSER, BrowserName.CHROME.name()));
        String testDisplaySize = System.getProperty(SystemPropertyConst.TEST_DISPLAY_SIZE);
        DisplaySize displaySize = (testDisplaySize != null) ? DisplaySize.valueOf(testDisplaySize) : null;

        switch (browserName) {
            case CHROME:
                // Загрузить драйвер
                WebDriverManager.chromedriver().setup();
                // открытие окна браузера
                driver = new ChromeDriver();
                break;
            case EDGE:
                // Загрузить драйвер
                WebDriverManager.edgedriver().setup();
                // открытие окна браузера
                driver = new EdgeDriver();
                break;
            case OPERA:
                // Загрузить драйвер
                WebDriverManager.operadriver().setup();
                // открытие окна браузера
                driver = new OperaDriver();
                break;
            case IE:
                // Загрузить драйвер
                WebDriverManager.iedriver().setup();
                // открытие окна браузера
                driver = new InternetExplorerDriver();
                break;
        }

        // задать размер окна
        if (displaySize != null) {
            driver.manage().window().setSize(displaySize.getDimension());
        } else {
            // задать размер окна на весь экран
            driver.manage().window().maximize();
        }
        // Установка таймаута по умолчанию
        driver.manage().timeouts().implicitlyWait(AbstractPage.DEFAULT_IMPLICITLY_TIMEOUT);
    }

    /**
     * Будет выполняться после каждой тестовой функции в классе
     *
     * @param testInfo Информация о запускаемом тесте
     */
    @AfterEach
    public void afterTest(TestInfo testInfo) {
        if (driver != null) {
            // сохранить скрин
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
        // закрытие окна
            driver.quit();
        }
    }
}
