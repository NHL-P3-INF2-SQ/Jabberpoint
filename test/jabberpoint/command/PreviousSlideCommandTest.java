package jabberpoint.command;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jabberpoint.model.Presentation;
import jabberpoint.model.PresentationReceiver;
import javax.swing.JFrame;

public class PreviousSlideCommandTest {
    private Presentation presentation;
    private PresentationReceiver receiver;
    private PreviousSlideCommand command;
    private JFrame dummyFrame;

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        dummyFrame = new JFrame();
        receiver = new PresentationReceiver(presentation, dummyFrame);
        command = new PreviousSlideCommand(receiver);

        // Add some slides to the presentation
        presentation.append(new jabberpoint.model.Slide());
        presentation.append(new jabberpoint.model.Slide());
        presentation.append(new jabberpoint.model.Slide());
    }

    @Test
    public void testExecuteMovesToPreviousSlide() {
        // Set current slide to 1 (second slide)
        presentation.setSlideNumber(1);

        // Execute the command
        command.execute();

        // Assert we moved to the previous slide
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testExecuteAtFirstSlideDoesNothing() {
        // Set current slide to 0 (first slide)
        presentation.setSlideNumber(0);

        // Execute the command
        command.execute();

        // Assert we stayed at the first slide
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testExecuteWithMultipleCallsStopsAtFirstSlide() {
        // Set current slide to 2 (last slide)
        presentation.setSlideNumber(2);

        // Execute the command multiple times
        command.execute(); // Should move to slide 1
        command.execute(); // Should move to slide 0
        command.execute(); // Should stay at slide 0

        // Assert we're at the first slide
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testExecuteFromInitialState() {
        // Test from initial state (-1)
        presentation.setSlideNumber(-1);

        // Execute the command
        command.execute();

        // Assert we stayed at -1
        assertEquals(-1, presentation.getSlideNumber());
    }

    @Test
    public void testExecuteWithSingleSlide() {
        // Create new presentation with single slide
        presentation = new Presentation();
        presentation.append(new jabberpoint.model.Slide());
        receiver = new PresentationReceiver(presentation, dummyFrame);
        command = new PreviousSlideCommand(receiver);

        // Set to first and only slide
        presentation.setSlideNumber(0);

        // Execute the command
        command.execute();

        // Assert we stayed at the only slide
        assertEquals(0, presentation.getSlideNumber());
    }
}