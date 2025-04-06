package jabberpoint.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JFrame;

public class PresentationReceiverListenerTest {
    private Presentation presentation;
    private PresentationReceiver receiver;
    private JFrame dummyFrame;
    private TestUpdateListener listener;
    private TestUpdateListener secondListener;

    // Test implementation of PresentationUpdateListener
    private static class TestUpdateListener implements PresentationReceiver.PresentationUpdateListener {
        private int updateCount = 0;
        private Presentation lastPresentation = null;

        @Override
        public void onPresentationChanged(Presentation newPresentation) {
            updateCount++;
            lastPresentation = newPresentation;
        }

        public int getUpdateCount() {
            return updateCount;
        }

        public Presentation getLastPresentation() {
            return lastPresentation;
        }

        public void reset() {
            updateCount = 0;
            lastPresentation = null;
        }
    }

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        dummyFrame = new JFrame();
        receiver = new PresentationReceiver(presentation, dummyFrame);
        listener = new TestUpdateListener();
        secondListener = new TestUpdateListener();
    }

    @Test
    public void testListenerNotification() {
        // Add listener
        receiver.addPresentationUpdateListener(listener);

        // Create new presentation
        receiver.newPresentation();

        // Verify listener was notified
        assertEquals(1, listener.getUpdateCount());
        assertNotNull(listener.getLastPresentation());
        assertNotSame(presentation, listener.getLastPresentation());
    }

    @Test
    public void testMultipleListeners() {
        // Add multiple listeners
        receiver.addPresentationUpdateListener(listener);
        receiver.addPresentationUpdateListener(secondListener);

        // Create new presentation
        receiver.newPresentation();

        // Verify both listeners were notified
        assertEquals(1, listener.getUpdateCount());
        assertEquals(1, secondListener.getUpdateCount());
        assertSame(listener.getLastPresentation(), secondListener.getLastPresentation());
    }

    @Test
    public void testListenerNotificationOnSlideNavigation() {
        receiver.addPresentationUpdateListener(listener);

        // Add some slides
        presentation.append(new Slide());
        presentation.append(new Slide());

        // Navigate slides
        receiver.nextSlide();
        receiver.prevSlide();

        // Verify listener was not notified for slide navigation
        assertEquals(0, listener.getUpdateCount());
    }

    @Test
    public void testListenerNotificationOnNewPresentation() {
        receiver.addPresentationUpdateListener(listener);

        // Create multiple new presentations
        receiver.newPresentation();
        receiver.newPresentation();

        // Verify listener was notified for each new presentation
        assertEquals(2, listener.getUpdateCount());
    }

    @Test
    public void testListenerReceivesCorrectPresentation() {
        receiver.addPresentationUpdateListener(listener);

        // Create new presentation
        receiver.newPresentation();
        Presentation newPresentation = receiver.getPresentation();

        // Verify listener received the correct presentation
        assertSame(newPresentation, listener.getLastPresentation());
    }

    @Test
    public void testListenerWithEmptyPresentation() {
        receiver.addPresentationUpdateListener(listener);

        // Create new empty presentation
        receiver.newPresentation();
        Presentation newPresentation = listener.getLastPresentation();

        // Verify the new presentation is empty
        assertEquals(0, newPresentation.getSize());
        assertEquals(-1, newPresentation.getSlideNumber());
    }
}