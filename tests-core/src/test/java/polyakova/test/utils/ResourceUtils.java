package polyakova.test.utils;

import java.io.File;
import java.net.URL;

/**
 * Helper methods for resource
 *
 * @author Iuliia Poliakova
 */
public class ResourceUtils {
    public static String getResourceAbsolutePath(String resourceRelativePath) {
        URL resource = ResourceUtils.class.getResource(resourceRelativePath);
        return new File(resource.getFile()).getAbsolutePath();
    }
}
