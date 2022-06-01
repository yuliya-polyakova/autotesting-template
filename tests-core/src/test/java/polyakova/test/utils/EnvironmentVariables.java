package polyakova.test.utils;

import java.util.ResourceBundle;

/**
 * Variables for tests
 *
 * @author Iuliia Poliakova
 */
public class EnvironmentVariables {

    private static ResourceBundle config;

    /**
     * Read value from configuration file
     *
     * @param name property name
     * @return value
     */
    public static String getString(String name) {
        if (config == null) {
            final String configName = System.getProperty(SystemPropertyConst.ENVIRONMENT_NAME, "environment_dev");
            config = ResourceBundle.getBundle(configName);
        }
        return config.getString(name);
    }

    /**
     * Read numeric value from configuration file
     *
     * @param name property name
     * @return value
     */
    public static int getInt(String name) {
        return Integer.parseInt(getString(name));
    }

    /**
     * Read numeric value from configuration file
     *
     * @param name property name
     * @return value
     */
    public static long getLong(String name) {
        return Long.parseLong(getString(name));
    }
}
