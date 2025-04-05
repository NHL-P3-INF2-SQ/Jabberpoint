package jabberpoint.ui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.awt.MenuBar;
import javax.swing.JFrame;
import jabberpoint.model.Presentation;
import jabberpoint.controller.KeyController;
import jabberpoint.controller.MenuController;

public class SlideViewerFrameTest {

    private SlideViewerFrame frame;
    private Presentation presentation;
    private static final String TEST_TITLE = "Test Presentation";

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        frame = new SlideViewerFrame(TEST_TITLE, presentation);
    }

    @AfterEach
    public void tearDown() {
        frame.dispose();
    }

    @Test
    public void testFrameInitialization() {
        assertNotNull(frame, "Frame should be created");
        assertTrue(frame.isVisible(), "Frame should be visible");
        assertEquals("Jabberpoint 1.7 - OU", frame.getTitle(),
                "Frame should have correct title");
    }

    @Test
    public void testFrameDimensions() {
        assertEquals(SlideViewerFrame.WIDTH, frame.getSize().width,
                "Frame width should match constant");
        assertEquals(SlideViewerFrame.HEIGHT, frame.getSize().height,
                "Frame height should match constant");
    }

    @Test
    public void testSlideViewerComponentPresence() {
        Component[] components = frame.getContentPane().getComponents();
        assertEquals(1, components.length,
                "Frame should have exactly one component");
        assertTrue(components[0] instanceof SlideViewerComponent,
                "Component should be SlideViewerComponent");
    }

    @Test
    public void testKeyListenerPresence() {
        KeyListener[] listeners = frame.getKeyListeners();
        boolean hasKeyController = false;
        for (KeyListener listener : listeners) {
            if (listener instanceof KeyController) {
                hasKeyController = true;
                break;
            }
        }
        assertTrue(hasKeyController, "Frame should have KeyController listener");
    }

    @Test
    public void testMenuBarPresence() {
        MenuBar menuBar = frame.getMenuBar();
        assertNotNull(menuBar, "Frame should have a menu bar");
        assertTrue(menuBar instanceof MenuController,
                "Menu bar should be MenuController instance");
    }

    @Test
    public void testConstructorWithNullPresentation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SlideViewerFrame(TEST_TITLE, null);
        }, "Constructor should throw exception for null presentation");
    }

    @Test
    public void testConstructorWithNullTitle() {
        // Should not throw exception with null title
        assertDoesNotThrow(() -> {
            SlideViewerFrame testFrame = new SlideViewerFrame(null, presentation);
            testFrame.dispose();
        });
    }

    @Test
    public void testComponentHierarchy() {
        // Test that all required components are in place
        assertNotNull(frame.getContentPane(), "Content pane should exist");
        assertNotNull(frame.getMenuBar(), "Menu bar should exist");
        assertTrue(frame.getContentPane().getComponent(0) instanceof SlideViewerComponent,
                "First component should be SlideViewerComponent");
    }

    @Test
    public void testFrameResizing() {
        // Test that frame can be resized
        Dimension newSize = new Dimension(800, 600);
        frame.setSize(newSize);
        assertEquals(newSize.width, frame.getSize().width,
                "Frame width should match new size");
        assertEquals(newSize.height, frame.getSize().height,
                "Frame height should match new size");
    }

    @Test
    public void testFrameLocationSetting() {
        // Test that frame location can be set
        int x = 100, y = 100;
        frame.setLocation(x, y);
        assertEquals(x, frame.getLocation().x,
                "Frame x location should match set value");
        assertEquals(y, frame.getLocation().y,
                "Frame y location should match set value");
    }
}