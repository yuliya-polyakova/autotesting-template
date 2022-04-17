package polyakova.test.demo.selenium.crossbrowser;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import polyakova.test.selenium.crossbrowser.BrowserName;
import polyakova.test.selenium.crossbrowser.DisplaySize;
import polyakova.test.utils.SystemPropertyConst;

import java.io.File;
import java.lang.invoke.MethodHandles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Часть кроссбраузерного тестирования
 * Перенастройка UI тестирование на конкретный browser и необходимое разрешение
 *
 * @author Iuliia Poliakova
 */
public class CrossBrowserConfigTest implements Executable {

    private final Launcher launcher;
    private final String className;
    private final TestIdentifier testIdentifier;
    private BrowserName browserName;
    private DisplaySize displaySize;

    public CrossBrowserConfigTest(Launcher launcher, String className, TestIdentifier testIdentifier, BrowserName browserName, DisplaySize displaySize) {
        this.launcher = launcher;
        this.className = className;
        this.testIdentifier = testIdentifier;
        this.browserName = browserName;
        this.displaySize = displaySize;
    }

    @Override
    public void execute() throws Throwable {
        System.setProperty(SystemPropertyConst.TEST_BROWSER, browserName.name());
        System.setProperty(SystemPropertyConst.TEST_DISPLAY_SIZE, displaySize.name());
        System.setProperty(SystemPropertyConst.TEST_BEFORE_ALL_DELETE_SCREEN, Boolean.FALSE.toString());

        final LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().selectors(DiscoverySelectors.selectUniqueId(testIdentifier.getUniqueId())).build();

        final SummaryGeneratingListener listener = new SummaryGeneratingListener();

        launcher.execute(request, listener);

        // сохранение скринов в директорию
        final File dirForClass = new File("report" + File.separatorChar + "screen" + File.separatorChar + MethodHandles.lookup().lookupClass().getCanonicalName().replace('.', '_'));
        final File srcDir = new File(dirForClass, "execute");
        if (srcDir.exists()) {
            final File destDir = new File(dirForClass, className + File.separatorChar + testIdentifier.getDisplayName().substring(0, testIdentifier.getDisplayName().length() - 2) + File.separatorChar + browserName + displaySize.getWidth() + "x" + displaySize.getHeight());
            FileUtils.moveDirectory(srcDir, destDir);
        }

        // получить отчет
        final TestExecutionSummary summary = listener.getSummary();
        assertEquals(1, summary.getTestsStartedCount());

        if (summary.getTestsFailedCount() > 0) {
            final Throwable exception = summary.getFailures().get(0).getException();
            fail(exception);
        }
    }
}
