package polyakova.test.demo.selenium.crossbrowser;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import polyakova.test.selenium.crossbrowser.BrowserName;
import polyakova.test.selenium.crossbrowser.DisplaySize;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Тестирование кроссбраузерности
 * <p>
 * Запуск тестов с заданной анотацией
 * <blockquote><pre>
 *     \u0040Tag("cross_browser")
 * </pre></blockquote>
 *
 * @author Iuliia Poliakova
 */
public class CrossBrowserTest {

    @BeforeAll
    public static void beforeAll() throws IOException {
        // удаление скринов теста перед тестом, которые были полученные на предыдущем запуске
        final File dir = new File("report" + File.separatorChar + "screen" + File.separatorChar + CrossBrowserConfigTest.class.getCanonicalName().replace('.', '_'));
        FileUtils.deleteDirectory(dir);
    }

    @TestFactory
    @Disabled
    public Collection<DynamicNode> crossBrowserTest() {

        final List<DynamicNode> result = new ArrayList<>();

        LauncherDiscoveryRequestBuilder request = LauncherDiscoveryRequestBuilder.request();
        // поиск тестов в пакете
        request.selectors(DiscoverySelectors.selectPackage("polyakova.test.demo.selenium"));
        // тесты должня быть помеченых тегом
        request.filters(TagFilter.includeTags("cross_browser"));

        final Launcher launcher = LauncherFactory.create();

        final TestPlan testPlan = launcher.discover(request.build());

        final Set<TestIdentifier> roots = testPlan.getRoots();
        for (TestIdentifier root : roots) {
            // классы содержащие тесты
            final Set<TestIdentifier> children = testPlan.getChildren(root);
            for (TestIdentifier child : children) {
                // методы - тест
                final Set<TestIdentifier> subChildren = testPlan.getChildren(child);
                final List<DynamicNode> testList = new ArrayList<>();
                for (TestIdentifier subChild : subChildren) {
                    final List<DynamicNode> subTestList = new ArrayList<>();
                    // для каждого теста создаем необходимое количество копий настроенных на определенный браузер и разрешение
                    addTest(subTestList, launcher, child.getDisplayName(), subChild, BrowserName.CHROME, DisplaySize.VGA);
                    addTest(subTestList, launcher, child.getDisplayName(), subChild, BrowserName.CHROME, DisplaySize.XGA);
                    addTest(subTestList, launcher, child.getDisplayName(), subChild, BrowserName.CHROME, DisplaySize.FHD);
                    addTest(subTestList, launcher, child.getDisplayName(), subChild, BrowserName.EDGE, DisplaySize.FHD);
                    addTest(subTestList, launcher, child.getDisplayName(), subChild, BrowserName.OPERA, DisplaySize.FHD);
                    addTest(subTestList, launcher, child.getDisplayName(), subChild, BrowserName.IE, DisplaySize.FHD);

                    final DynamicNode dynamicNode = DynamicContainer.dynamicContainer(subChild.getDisplayName(), subTestList);
                    testList.add(dynamicNode);
                }
                final DynamicNode dynamicNode = DynamicContainer.dynamicContainer(child.getDisplayName(), testList);
                result.add(dynamicNode);
            }
        }
        return result;
    }

    private static void addTest(List<DynamicNode> tests, Launcher launcher, String className, TestIdentifier testIdentifier, BrowserName browserName, DisplaySize displaySize) {
        // к имени теста добавляем имя браузера и размер
        String displayName = browserName.name() + displaySize.getWidth() + "x" + displaySize.getHeight();
        CrossBrowserConfigTest test = new CrossBrowserConfigTest(launcher, className, testIdentifier, browserName, displaySize);
        DynamicTest dynamicTest = DynamicTest.dynamicTest(displayName, test);
        tests.add(dynamicTest);
    }
}


