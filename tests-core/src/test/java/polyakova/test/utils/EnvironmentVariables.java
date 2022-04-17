package polyakova.test.utils;

import java.util.ResourceBundle;

/**
 * Переменные для тестов
 *
 * @author Iuliia Poliakova
 */
public class EnvironmentVariables {

    private static ResourceBundle config;

    /**
     * Считать значение из конфигурационного файла
     *
     * @param name наименование свойства
     * @return значение
     */
    public static String getString(String name) {
        if (config == null) {
            final String configName = System.getProperty(SystemPropertyConst.ENVIRONMENT_NAME, "environment_dev");
            config = ResourceBundle.getBundle(configName);
        }
        return config.getString(name);
    }

    /**
     * Считать числовое значение из конфигурационного файла
     *
     * @param name наименование свойства
     * @return значение
     */
    public static int getInt(String name) {
        return Integer.parseInt(getString(name));
    }

    /**
     * Считать числовое значение из конфигурационного файла
     *
     * @param name наименование свойства
     * @return значение
     */
    public static long getLong(String name) {
        return Long.parseLong(getString(name));
    }
}
