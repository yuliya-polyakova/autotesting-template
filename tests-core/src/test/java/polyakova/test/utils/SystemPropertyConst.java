package polyakova.test.utils;

/**
 * Переменные для настройки тестов, передаваемые как переменные окружения
 *
 * @author Iuliia Poliakova
 */
public class SystemPropertyConst {
    /**
     * Определяет с какого файла определяются переменные, по умолчанию environment_dev
     */
    public static final String ENVIRONMENT_NAME ="environmentName";
    /**
     * Определяет метод beforeAll будет ли удалят скриншоты, по умолчанию true(удалять старые скриншоты)
     */
    public static final String TEST_BEFORE_ALL_DELETE_SCREEN ="test_before_all_delete_screen";
    /**
     * Определяет в каком браузере {@code ru.tkoinform.tests.selenium.crossbrowser.BrowserName} запускать тесты, по умолчанию CHROME
     */
    public static final String TEST_BROWSER ="test_browser";
    /**
     * Определяет размер окна {@code ru.tkoinform.tests.selenium.crossbrowser.DisplaySize}, по умолчанию не задано (на весь экран)
     */
    public static final String TEST_DISPLAY_SIZE ="test_display_size";
}
