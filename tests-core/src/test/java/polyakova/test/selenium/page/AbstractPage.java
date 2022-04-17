package polyakova.test.selenium.page;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

/**
 * Общие методы для работы со страницей
 *
 * @author Iuliia Poliakova
 */
public abstract class AbstractPage {
    /**
     * Время ожидания (5 секунд), применяется ко всем операциям поиска элементов
     */
    public static final Duration DEFAULT_IMPLICITLY_TIMEOUT = Duration.ofSeconds(5);
    /**
     * Время ожидания (2 минуты), применяется по умолчанию в методах с явным указанием ожидания
     */
    public static final int DEFAULT_EXPLICIT_TIMEOUT = 30000;

    /**
     * Пакет который необходимо включить в путь к скриншоту
     */
    public static final String SCREEN_INCLUDE_PACKAGE = "polyakova.test";

    /**
     * Selenium web driver
     */
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        // TODO для модальных окон использовать SearchContextElementLocatorFactory, а в аннотации FindBy относительные пути
        PageFactory.initElements(driver, this);
    }

    /**
     * Метод ожидает появление элемента на странице
     *
     * @param parent  Родитель относительно которого необходимо выполнить поиск
     * @param by      селектор идентифицирующий элемент
     * @param timeOut максимальное время ожидания (ms)
     * @return элемент
     * @throws TimeoutException если элемент не был найден в течении timeOut
     */
    public static WebElement waitElement(SearchContext parent, By by, long timeOut) {
        WebElement result = null;
        long time = System.currentTimeMillis();
        while (result == null && System.currentTimeMillis() - time < timeOut) {
            try {
                result = parent.findElement(by);
                if (!result.isEnabled()) {
                    result = null;
                } else if (!result.isDisplayed()) {
                    result = null;
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) {
                }
            }
        }
        if (result == null) {
            throw new TimeoutException("Element not fount by:" + by);
        }
        return result;
    }

    /**
     * Метод ожидает появление элемента на странице
     *
     * @param parent Родитель относительно которого необходимо выполнить поиск
     * @param by     селектор идентифицирующий элемент
     * @return элемент
     * @throws TimeoutException если элемент не был найден в течении timeOut
     */
    protected WebElement waitElement(SearchContext parent, By by) {
        return waitElement(parent, by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * Метод ожидает появление элемента на странице
     *
     * @param by      селектор идентифицирующий элемент
     * @param timeOut максимальное время ожидания (ms)
     * @return элемент
     * @throws TimeoutException если элемент не был найден в течении timeOut
     */
    protected WebElement waitElement(By by, long timeOut) {
        return waitElement(driver, by, timeOut);
    }

    /**
     * Метод ожидает появление элемента на странице
     *
     * @param by селектор идентифицирующий элемент
     * @return элемент
     * @throws TimeoutException если элемент не был найден в течении timeOut
     */
    protected WebElement waitElement(By by) {
        return waitElement(driver, by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * Ожидание появления фрейма по frameBy и элемента в нем по elementBy.
     * Возвращает найденный элемент или кидает TimeoutException,
     * если элемент не был найден за {@code DEFAULT_EXPLICIT_TIMEOUT}.
     *
     * @param frameBy   селектор, идентифицирующий фрейм
     * @param elementBy селектор, идентифицирующий элемент
     * @return элемент
     */
    protected WebElement waitElementInFrame(By frameBy, By elementBy) {
        return waitElementInFrame(frameBy, elementBy, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * Ожидание появления фрейма по frameBy и элемента в нем по elementBy.
     * Возвращает найденный элемент или кидает TimeoutException,
     * если элемент не был найден за указанный timeout.
     *
     * @param frameBy   селектор, идентифицирующий фрейм
     * @param elementBy селектор, идентифицирующий элемент
     * @param timeout   максимальное время ожидания (ms)
     * @return элемент
     * @throws TimeoutException, если элемент не был найден в течении timeOut
     */
    protected WebElement waitElementInFrame(By frameBy, By elementBy, long timeout) {
        long time = System.currentTimeMillis();
        WebElement result = null;
        while (result == null && System.currentTimeMillis() - time < timeout) {
            try {
                driver.switchTo().parentFrame();
                final WebElement frame = driver.findElement(frameBy);
                driver.switchTo().frame(frame);
                result = driver.findElement(elementBy);
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) {
                }
            }
        }
        if (result == null) {
            throw new TimeoutException("Element not fount by frame:" + frameBy + " by:" + elementBy);
        }
        return result;
    }

    /**
     * Метод ожидает появление элемента на странице и выполняет нажатие на него.
     *
     * @param parent  Родитель, относительно которого необходимо выполнить поиск
     * @param by      селектор, идентифицирующий элемент
     * @param timeOut максимальное время ожидания (ms)
     * @return элемент
     * @throws TimeoutException, если элемент не был найден в течении timeOut
     */
    protected WebElement waitElementAndClick(SearchContext parent, By by, long timeOut) {
        return waitElementAndClick(driver, parent, by, timeOut);
    }
    /**
     * Метод ожидает появление элемента на странице и выполняет нажатие на него.
     *
     * @param driver  Selenium web driver
     * @param parent  Родитель, относительно которого необходимо выполнить поиск
     * @param by      селектор, идентифицирующий элемент
     * @param timeOut максимальное время ожидания (ms)
     * @return элемент
     * @throws TimeoutException, если элемент не был найден в течении timeOut
     */
    public static WebElement waitElementAndClick(WebDriver driver, SearchContext parent, By by, long timeOut) {
        long time = System.currentTimeMillis();
        WebElement result = null;
            boolean click = false;
        final WebDriver.Timeouts timeouts = driver.manage().timeouts();
        final Duration implicitWaitTimeout = timeouts.getImplicitWaitTimeout();
        timeouts.implicitlyWait(Duration.ofMillis(100));
            while (!click && System.currentTimeMillis() - time < timeOut) {
                try {
                result = parent.findElement(by);
                    result.click();
                    click = true;
                } catch (Exception e) {
                    }
                }
        timeouts.implicitlyWait(implicitWaitTimeout);
            if (!click) {
                throw new TimeoutException("Failed to click on element by:" + by);
            }
        return result;
    }

    /**
     * Выделить содержимое (Ctrl+A) и ввести текст.
     *
     * @param element поле ввода
     * @param text    текст
     */
    protected void selectAllAndSendKeys(WebElement element, String text) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), text);
    }

    /**
     * Метод ожидает появление элемента на странице и выполняет нажатие на него
     *
     * @param parent Родитель, относительно которого необходимо выполнить поиск
     * @param by     селектор, идентифицирующий элемент
     * @return элемент
     * @throws TimeoutException, если элемент не был найден в течении timeOut
     */
    protected WebElement waitElementAndClick(SearchContext parent, By by) {
        return waitElementAndClick(parent, by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * Метод ожидает появление элемента на странице и выполняет нажатие на него
     *
     * @param by селектор, идентифицирующий элемент
     * @return элемент
     * @throws TimeoutException, если элемент не был найден в течении timeOut
     */
    protected WebElement waitElementAndClick(By by) {
        return waitElementAndClick(driver, by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * Метод ожидает скрытия элемента со страницы
     *
     * @param by      селектор идентифицирующий элемент
     * @param timeOut максимальное время ожидания (ms)
     * @throws TimeoutException если элемент не был скрыт в течении указанного времени
     */
    public void waitInvisibilityElement(By by, long timeOut) {
        boolean hide;
        long time = System.currentTimeMillis();
        final WebDriver.Timeouts timeouts = driver.manage().timeouts();
        final Duration implicitWaitTimeout = timeouts.getImplicitWaitTimeout();
        timeouts.implicitlyWait(Duration.ofMillis(500));
        do {
            final List<WebElement> elements = driver.findElements(by);
            hide = elements.isEmpty();
            if (!hide) {
                hide = true;
                for (int i = 0; hide && i < elements.size(); i++) {
                    try {
                        hide = !elements.get(i).isDisplayed();
                    } catch (Exception ignore) {
                    }
                }
            }
        } while (!hide && System.currentTimeMillis() - time < timeOut);
        timeouts.implicitlyWait(implicitWaitTimeout);
        if (!hide) {
            throw new TimeoutException("Element not hide by:" + by+ " time:"+ (System.currentTimeMillis() - time)+"ms");
        }
    }

    /**
     * Метод ожидает скрытия элемента со страницы
     *
     * @param by селектор идентифицирующий элемент
     * @throws TimeoutException если элемент не был скрыт в течении 30 секунд
     */
    public void waitInvisibilityElement(By by) {
        waitInvisibilityElement(by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * Метод ожидает скрытия элемента со страницы
     *
     * @param element элемент
     * @throws TimeoutException если элемент не был скрыт в течении 2-х минут
     */
    public void waitInvisibilityElement(WebElement element) {
        long time = System.currentTimeMillis();
        boolean hide;
        do {
            try {
                hide = !element.isDisplayed();
                if (!hide){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignore) {
                    }
                }
            } catch (Exception ignore) {
                hide = true;
            }
        } while (!hide && System.currentTimeMillis() - time < DEFAULT_EXPLICIT_TIMEOUT);
        if (!hide) {
            throw new TimeoutException("Element not hide:" + element);
        }
    }

    /**
     * Сделать снимок элемента
     * Снимок складывается в папки соответствующие стектрейсу
     *
     * @param element элемент
     * @return файл со снимком
     * @throws Exception
     */
    public static File screen(TakesScreenshot element) throws Exception {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder fileName = new StringBuilder("report").append(File.separatorChar);
        fileName.append("screen").append(File.separatorChar);
        boolean findTest = false;
        for (int i = stackTrace.length - 1; i >= 3; i--) {
            final String testName = stackTrace[i].getClassName();
            if (!findTest) {
                findTest = testName.startsWith(SCREEN_INCLUDE_PACKAGE);
                if (findTest) {
                    fileName.append(testName.replace('.', '_')).append(File.separatorChar);
                    fileName.append(stackTrace[i].getMethodName()).append(File.separatorChar);
                    fileName.append(stackTrace[i].getLineNumber()).append('_');
                }
            } else {
                if (testName.startsWith(SCREEN_INCLUDE_PACKAGE)) {
                    fileName.append(stackTrace[i].getMethodName()).append('_');
                    fileName.append(stackTrace[i].getLineNumber()).append('_');
                }
            }
        }
        fileName.setLength(fileName.length() - 1);
        final byte[] bytes = element.getScreenshotAs(OutputType.BYTES);
        int i = 0;
        String countFileName = fileName + ".png";
        File screen;
        while ((screen = new File(countFileName)).exists()) {
            i++;
            countFileName = fileName + "_" + i + ".png";
        }
        screen.getParentFile().mkdirs();
        try (final FileOutputStream out = new FileOutputStream(screen)) {
            out.write(bytes);
        }
        try (final FileInputStream in = new FileInputStream(screen)) {
            Allure.addAttachment(countFileName, "image/png", in, ".png");
        }
        return screen;
    }

    /**
     * Снять снимок экрана
     * Снимок складывается в папки соответствующие стектрейсу
     *
     * @return файл со снимком
     * @throws Exception
     */
    public File screen() throws Exception {
        return screen((TakesScreenshot) driver);
    }

    /**
     * Переключиться на последнее открытое окно
     */
    protected void switchToLastTab() {
        final Iterator<String> iterator = driver.getWindowHandles().iterator();
        String last = null;
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        driver.switchTo().window(last);
    }

    /**
     * Переключиться на окно с указанным title
     */
    protected void waitTab(String title, boolean ignoreCase) {
        long time = System.currentTimeMillis();

        while (System.currentTimeMillis() - time < DEFAULT_IMPLICITLY_TIMEOUT.toMillis()) {
            for (String window : driver.getWindowHandles()) {
                if ((ignoreCase && title.equalsIgnoreCase(driver.switchTo().window(window).getTitle())) ||
                        title.equals(driver.switchTo().window(window).getTitle())) {
                    return;
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
        throw new TimeoutException("Failed to wait tab by title:" + title);
    }
}
