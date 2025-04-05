package jabberpoint.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;
import jabberpoint.model.Presentation;
import jabberpoint.model.PresentationReceiver;
import jabberpoint.model.Slide;
import javax.swing.JFrame;

public class KeyControllerTest {
    private KeyController keyController;
    private Presentation presentation;
    private PresentationReceiver receiver;
    private JFrame dummyFrame;

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        dummyFrame = new JFrame();
        receiver = new PresentationReceiver(presentation, dummyFrame);
        keyController = new KeyController(receiver);

        // Add some slides to the presentation
        presentation.append(new Slide());
        presentation.append(new Slide());
        presentation.append(new Slide());

        // Set initial slide
        presentation.setSlideNumber(0);
    }

    @Test
    public void testNextSlideWithPageDown() {
        // Create KeyEvent for PAGE_DOWN
        KeyEvent event = new KeyEvent(dummyFrame, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_PAGE_DOWN, '\0');

        // Process the key event
        keyController.keyPressed(event);

        // Verify slide changed
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void testNextSlideWithDownArrow() {
        KeyEvent event = new KeyEvent(dummyFrame, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, '\0');

        keyController.keyPressed(event);

        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void testNextSlideWithEnter() {
        KeyEvent event = new KeyEvent(dummyFrame, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');

        keyController.keyPressed(event);

        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void testPreviousSlideWithPageUp() {
        // Start from slide 1
        presentation.setSlideNumber(1);

        KeyEvent event = new KeyEvent(dummyFrame, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_PAGE_UP, '\0');

        keyController.keyPressed(event);

        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testPreviousSlideWithUpArrow() {
        // Start from slide 1
        presentation.setSlideNumber(1);

        KeyEvent event = new KeyEvent(dummyFrame, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_UP, '\0');

        keyController.keyPressed(event);

        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testNextSlideAtLastSlide() {
        // Go to last slide
        presentation.setSlideNumber(2);

        KeyEvent event = new KeyEvent(dummyFrame, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_PAGE_DOWN, '\0');

        keyController.keyPressed(event);

        // Verify we stay at last slide
        assertEquals(2, presentation.getSlideNumber());
    }

    @Test
    public void testPreviousSlideAtFirstSlide() {
        // Already at first slide (0)
        KeyEvent event = new KeyEvent(dummyFrame, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_PAGE_UP, '\0');

        keyController.keyPressed(event);

        // Verify we stay at first slide
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testExitWithQKey() {
        // Create a SecurityManager to prevent actual System.exit()
        SecurityManager originalManager = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExit(int status) {
                throw new SecurityException("Exit attempted");
            }

            @Override
            public void checkPermission(java.security.Permission perm) {
                // Allow other operations
            }
        });

        try {
            KeyEvent event = new KeyEvent(dummyFrame, KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(), 0, KeyEvent.VK_Q, 'q');

            // This should attempt to exit
            keyController.keyPressed(event);
            fail("Expected SecurityException was not thrown");
        } catch (SecurityException e) {
            // Expected behavior
            assertTrue(true);
        } finally {
            // Restore original SecurityManager
            System.setSecurityManager(originalManager);
        }
    }

    @Test
    public void testPresentationChange() {
        // Create a new presentation
        Presentation newPresentation = new Presentation();
        newPresentation.append(new Slide());

        // Notify controller about presentation change
        keyController.onPresentationChanged(newPresentation);

        // Try navigation in new presentation
        KeyEvent event = new KeyEvent(dummyFrame, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_PAGE_DOWN, '\0');

        keyController.keyPressed(event);

        // Verify navigation worked in new presentation
        assertEquals(0, newPresentation.getSlideNumber());
    }
}