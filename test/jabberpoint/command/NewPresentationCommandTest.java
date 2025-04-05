package jabberpoint.command;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jabberpoint.model.Presentation;
import jabberpoint.model.PresentationReceiver;
import jabberpoint.observer.PresentationObserver;
import javax.swing.JFrame;

public class NewPresentationCommandTest {
    private Presentation presentation;
    private PresentationReceiver receiver;
    private NewPresentationCommand command;
    private JFrame dummyFrame;
    private TestObserver observer;

    private class TestObserver implements PresentationObserver {
        private int updateCount = 0;
        private Presentation lastPresentation = null;

        @Override
        public void onPresentationUpdate(Presentation presentation, jabberpoint.model.Slide currentSlide) {
            updateCount++;
            lastPresentation = presentation;
        }

        public int getUpdateCount() {
            return updateCount;
        }

        public Presentation getLastPresentation() {
            return lastPresentation;
        }
    }

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        dummyFrame = new JFrame();
        receiver = new PresentationReceiver(presentation, dummyFrame);
        command = new NewPresentationCommand(receiver);
        observer = new TestObserver();

        // Add an observer to the presentation
        presentation.addObserver(observer);

        // Add some slides to the presentation
        presentation.append(new jabberpoint.model.Slide());
        presentation.append(new jabberpoint.model.Slide());
    }

    @Test
    public void testExecuteCreatesNewEmptyPresentation() {
        // Execute the command
        command.execute();

        // Assert the presentation is empty
        assertEquals(0, receiver.getPresentation().getSize());
    }

    @Test
    public void testExecuteResetsSlideNumber() {
        // Set initial slide number
        presentation.setSlideNumber(1);

        // Execute the command
        command.execute();

        // Assert the slide number is reset
        assertEquals(-1, receiver.getPresentation().getSlideNumber());
    }
}