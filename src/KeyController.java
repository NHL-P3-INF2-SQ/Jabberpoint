import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * Handles keyboard input for controlling the presentation.
 * This class processes key events to navigate through slides
 * and exit the application.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class KeyController extends KeyAdapter {
    
    /**
     * The presentation being controlled.
     */
    private final Presentation presentation;

    /**
     * Creates a new KeyController for the specified presentation.
     *
     * @param presentation The presentation to control
     */
    public KeyController(Presentation presentation) {
        this.presentation = presentation;
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
            case '+':
                this.presentation.nextSlide();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case '-':
                this.presentation.prevSlide();
                break;
            case 'q':
            case 'Q':
                System.exit(0);
                break;
            default:
                break;
        }
    }
}
