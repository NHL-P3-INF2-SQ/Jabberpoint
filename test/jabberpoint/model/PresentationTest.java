package jabberpoint.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jabberpoint.observer.PresentationObserver;

public class PresentationTest {
    private Presentation presentation;
    private TestObserver observer;

    private static class TestObserver implements PresentationObserver {
        private int updateCount = 0;
        private Presentation lastPresentation = null;
        private Slide lastSlide = null;

        @Override
        public void onPresentationUpdate(Presentation presentation, Slide currentSlide) {
            updateCount++;
            lastPresentation = presentation;
            lastSlide = currentSlide;
        }

        public int getUpdateCount() {
            return updateCount;
        }

        public Presentation getLastPresentation() {
            return lastPresentation;
        }

        public Slide getLastSlide() {
            return lastSlide;
        }
    }

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        observer = new TestObserver();
        presentation.addObserver(observer);
    }

    @Test
    public void testInitialState() {
        assertEquals(-1, presentation.getSlideNumber());
        assertEquals(0, presentation.getSize());
        assertNull(presentation.getCurrentSlide());
        assertNull(presentation.getTitle());
    }

    @Test
    public void testSetTitle() {
        String title = "Test Presentation";
        presentation.setTitle(title);
        assertEquals(title, presentation.getTitle());
    }

    @Test
    public void testAppendSlide() {
        Slide slide = new Slide();
        presentation.append(slide);

        assertEquals(1, presentation.getSize());
        assertSame(slide, presentation.getSlide(0));
    }

    @Test
    public void testGetSlideWithInvalidIndex() {
        assertNull(presentation.getSlide(-1));
        assertNull(presentation.getSlide(0));

        // Add a slide and test beyond bounds
        presentation.append(new Slide());
        assertNull(presentation.getSlide(1));
    }

    @Test
    public void testSetSlideNumber() {
        // Add some slides
        presentation.append(new Slide());
        presentation.append(new Slide());

        // Test valid slide number
        presentation.setSlideNumber(1);
        assertEquals(1, presentation.getSlideNumber());
        assertNotNull(presentation.getCurrentSlide());

        // Verify observer was notified
        assertEquals(1, observer.getUpdateCount());
        assertSame(presentation, observer.getLastPresentation());
        assertSame(presentation.getCurrentSlide(), observer.getLastSlide());
    }

    @Test
    public void testNextSlide() {
        // Add slides
        presentation.append(new Slide());
        presentation.append(new Slide());

        // Start from first slide
        presentation.setSlideNumber(0);
        observer.updateCount = 0; // Reset counter

        // Move to next slide
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
        assertEquals(1, observer.getUpdateCount());
    }

    @Test
    public void testPrevSlide() {
        // Add slides
        presentation.append(new Slide());
        presentation.append(new Slide());

        // Start from second slide
        presentation.setSlideNumber(1);
        observer.updateCount = 0; // Reset counter

        // Move to previous slide
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
        assertEquals(1, observer.getUpdateCount());
    }

    @Test
    public void testClear() {
        // Add some content
        presentation.append(new Slide());
        presentation.setTitle("Test");
        presentation.setSlideNumber(0);
        observer.updateCount = 0; // Reset counter

        // Clear presentation
        presentation.clear();

        // Verify state
        assertEquals(0, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());
        assertNull(presentation.getCurrentSlide());
        assertEquals(1, observer.getUpdateCount());
    }

    @Test
    public void testNavigationBoundaries() {
        // Add slides
        presentation.append(new Slide());
        presentation.append(new Slide());

        // Test at first slide
        presentation.setSlideNumber(0);
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber()); // Should stay at first

        // Test at last slide
        presentation.setSlideNumber(1);
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber()); // Should stay at last
    }
}