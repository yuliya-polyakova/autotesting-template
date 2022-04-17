package polyakova.test.selenium.crossbrowser;

import org.openqa.selenium.Dimension;

/**
 * Список стандартных разрешений
 *
 * @author Iuliia Poliakova
 * @see <a href="https://en.wikipedia.org/wiki/Display_resolution">Display_resolution</a>
 */
public enum DisplaySize {
    VGA(640, 480),
    SVGA(800, 600),
    XGA(1024, 768),
    WXGA_H(1280, 720),
    WXGA(1280, 800),
    WSXGA(1440, 900),
    FHD(1920, 1080),
    WUXGA(1920, 1200);

    private final int width;
    private final int height;

    DisplaySize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Dimension getDimension(){
        return new Dimension(width, height);
    }
}
