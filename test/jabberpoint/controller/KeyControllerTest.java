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
}