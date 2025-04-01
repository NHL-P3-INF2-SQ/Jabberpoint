import java.io.IOException;

/**
 * Receiver class that handles all presentation operations
 */
public class PresentationReceiver {
    private Presentation presentation;

    /**
     * Constructor that takes a presentation
     * @param presentation The presentation to operate on
     */
    public PresentationReceiver(Presentation presentation) {
        this.presentation = presentation;
    }

    /**
     * Move to the next slide in the presentation
     */
    public void nextSlide() {
        this.presentation.nextSlide();
    }

    /**
     * Move to the previous slide in the presentation
     */
    public void prevSlide() {
        this.presentation.prevSlide();
    }

    /**
     * Create a new presentation
     */
    public void newPresentation() {
        this.presentation = new Presentation();
    }

    /**
     * Open a presentation using the default accessor
     */
    public void openPresentation() {
        try {
            this.presentation.clear();  // Clear the current presentation
            Accessor accessor = new XMLAccessor();
            accessor.loadFile(this.presentation, "test.xml");
            this.presentation.setSlideNumber(0);  // Reset to first slide
        } catch (IOException e) {
            System.err.println("Error loading presentation: " + e.getMessage());
        }
    }

    /**
     * Save the current presentation using the default accessor
     */
    public void savePresentation() {
        try {
            Accessor accessor = new XMLAccessor();
            accessor.saveFile(this.presentation, "test.xml");
        } catch (IOException e) {
            System.err.println("Error saving presentation: " + e.getMessage());
        }
    }

    /**
     * Exit the presentation
     */
    public void exitPresentation() {
        System.exit(0);
    }
} 