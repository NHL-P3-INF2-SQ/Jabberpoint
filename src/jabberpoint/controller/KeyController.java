package jabberpoint.controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import jabberpoint.command.Command;
import jabberpoint.command.NextSlideCommand;
import jabberpoint.command.PreviousSlideCommand;
import jabberpoint.command.ExitPresentationCommand;
import jabberpoint.model.Presentation;
import jabberpoint.model.PresentationReceiver;

/**
 * Handles keyboard input for controlling the presentation.
 * This class processes key events to navigate through slides
 * and exit the application.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.0 2025/04/01
 */
public class KeyController extends KeyAdapter implements PresentationReceiver.PresentationUpdateListener {
    
    /**
     * Command for navigating to the next slide
     */
    private final Command nextSlideCommand;

    /**
     * Command for navigating to the previous slide
     */
    private final Command prevSlideCommand;

    /**
     * Command for exiting the application
     */
    private final Command exitCommand;

    /**
     * Creates a new KeyController with the specified presentation receiver.
     *
     * @param receiver The presentation receiver to use for commands
     */
    public KeyController(PresentationReceiver receiver) {
        receiver.addPresentationUpdateListener(this);  // Register for presentation updates
        
        this.nextSlideCommand = new NextSlideCommand(receiver);
        this.prevSlideCommand = new PreviousSlideCommand(receiver);
        this.exitCommand = new ExitPresentationCommand(receiver);
    }

    @Override
    public void onPresentationChanged(Presentation newPresentation) {
        // No need to store the presentation reference as we use commands
    }

    /**
     * Handles key press events for presentation control.
     * - PageDown/Down/Enter/'+': Next slide
     * - PageUp/Up/'-': Previous slide
     * - 'q'/'Q': Exit application
     *
     * @param event The key event to process
     */
    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_PLUS:
                this.nextSlideCommand.execute();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_MINUS:
                this.prevSlideCommand.execute();
                break;
            case KeyEvent.VK_Q:
                this.exitCommand.execute();
                break;
            default:
                break;
        }
    }
}
