package jabberpoint.observer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;

public class PresentationObserverTest {
    private Presentation presentation;
    private TestObserver observer;
    private TestObserver secondObserver;

    // Test implementation of PresentationObserver
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

        public void reset() {
            updateCount = 0;
            lastPresentation = null;
            lastSlide = null;
        }
    }

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        observer = new TestObserver();
        secondObserver = new TestObserver();

        // Add some slides to the presentation
        presentation.append(new Slide());
        presentation.append(new Slide());
        presentation.append(new Slide());
    }

    @Test
    public void testObserverNotification() {
        // Add observer
        presentation.addObserver(observer);

        // Change slide number
        presentation.setSlideNumber(1);

        // Verify observer was notified
        assertEquals(1, observer.getUpdateCount());
        assertEquals(presentation, observer.getLastPresentation());
        assertEquals(presentation.getCurrentSlide(), observer.getLastSlide());
    }

    @Test
    public void testMultipleObservers() {
        // Add multiple observers
        presentation.addObserver(observer);
        presentation.addObserver(secondObserver);

        // Change slide number
        presentation.setSlideNumber(1);

        // Verify both observers were notified
        assertEquals(1, observer.getUpdateCount());
        assertEquals(1, secondObserver.getUpdateCount());
        assertEquals(presentation, observer.getLastPresentation());
        assertEquals(presentation, secondObserver.getLastPresentation());
    }

    @Test
    public void testRemoveObserver() {
        // Add and then remove an observer
        presentation.addObserver(observer);
        presentation.removeObserver(observer);

        // Change slide number
        presentation.setSlideNumber(1);

        // Verify observer was not notified
        assertEquals(0, observer.getUpdateCount());
        assertNull(observer.getLastPresentation());
    }

    @Test
    public void testMultipleNotifications() {
        presentation.addObserver(observer);

        // Change slide number multiple times
        presentation.setSlideNumber(0);
        presentation.setSlideNumber(1);
        presentation.setSlideNumber(2);

        // Verify observer was notified multiple times
        assertEquals(3, observer.getUpdateCount());
        assertEquals(2, presentation.getSlideNumber());
    }

    @Test
    public void testObserverNotificationWithInitialState() {
        presentation.addObserver(observer);

        // Set initial slide number to -1 (initial state)
        presentation.setSlideNumber(-1);

        // Verify observer was notified with correct state
        assertEquals(1, observer.getUpdateCount());
        assertNull(observer.getLastSlide()); // No current slide at -1
    }

    @Test
    public void testObserverNotificationAfterClear() {
        presentation.addObserver(observer);
        presentation.setSlideNumber(1);
        observer.reset();

        // Clear the presentation
        presentation.clear();

        // Verify observer was notified of the change
        assertEquals(1, observer.getUpdateCount());
        assertEquals(-1, presentation.getSlideNumber());
        assertNull(observer.getLastSlide());
    }
}