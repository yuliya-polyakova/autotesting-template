package polyakova.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 * Variables for tests
 *
 * @author Iuliia Poliakova
 */
public class EnvironmentVariables {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentVariables.class);

    private static ResourceBundle config;

    /**
     * Read value from configuration file
     *
     * @param name property name
     * @return value
     */
    public static String getString(String name) {
        try {
            if (config == null) {
                final String configName = System.getProperty(SystemPropertyConst.ENVIRONMENT_NAME, "environment_dev");
                log.info("Environment read from {}", configName);
                config = ResourceBundle.getBundle(configName);
            }
            return config.getString(name);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Read boolean value from configuration file
     *
     * @param name property name
     * @return value
     */
    public static boolean getBoolean(String name) {
        return Boolean.parseBoolean(getString(name));
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
