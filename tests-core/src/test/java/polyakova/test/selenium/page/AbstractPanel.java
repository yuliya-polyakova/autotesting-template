package polyakova.test.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 * General methods for working with the panel
 *
 * @author Iuliia Poliakova
 */
public abstract class AbstractPanel extends AbstractPage {

    /**
     * Panel - parent element, relative to which all other elements are found
     */
    protected final WebElement panel;

    public AbstractPanel(WebDriver driver, By panelSelector) {
        this(driver, waitElement(driver, panelSelector, DEFAULT_EXPLICIT_TIMEOUT));
    }

    public AbstractPanel(WebDriver driver, WebElement panel) {
        super(driver, panel);
        this.panel = panel;
    }

    /**
     * Take a screenshot of an element
     * The screenshot is folded into folders to correspond of the stack trace
     *
     * @return screenshot file
     * @throws Exception
     */
    @Override
    public File screen() throws Exception {
        return screen(panel);
    }
}
