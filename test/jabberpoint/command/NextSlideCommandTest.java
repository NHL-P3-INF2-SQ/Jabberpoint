package jabberpoint.command;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jabberpoint.model.Presentation;
import jabberpoint.model.PresentationReceiver;
import javax.swing.JFrame;

public class NextSlideCommandTest {
    private Presentation presentation;
    private PresentationReceiver receiver;
    private NextSlideCommand command;
    private JFrame dummyFrame;

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        dummyFrame = new JFrame();
        receiver = new PresentationReceiver(presentation, dummyFrame);
        command = new NextSlideCommand(receiver);

        // Add some slides to the presentation
        presentation.append(new jabberpoint.model.Slide());
        presentation.append(new jabberpoint.model.Slide());
        presentation.append(new jabberpoint.model.Slide());
    }

    @Test
    public void testExecuteMovesToNextSlide() {
        // Set current slide to 0 (first slide)
        presentation.setSlideNumber(0);

        // Execute the command
        command.execute();

        // Assert we moved to the next slide
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void testExecuteAtLastSlideDoesNothing() {
        // Set current slide to 2 (last slide)
        presentation.setSlideNumber(2);

        // Execute the command
        command.execute();

        // Assert we stayed at the last slide
        assertEquals(2, presentation.getSlideNumber());
    }

    @Test
    public void testExecuteWithMultipleCallsStopsAtLastSlide() {
        // Set current slide to 0 (first slide)
        presentation.setSlideNumber(0);

        // Execute the command multiple times
        command.execute(); // Should move to slide 1
        command.execute(); // Should move to slide 2
        command.execute(); // Should stay at slide 2

        // Assert we're at the last slide
        assertEquals(2, presentation.getSlideNumber());
    }
}