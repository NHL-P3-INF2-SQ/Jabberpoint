package jabberpoint.command;
import jabberpoint.model.PresentationReceiver;

/**
 * Command to move to the previous slide in the presentation    
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 */
public class PreviousSlideCommand implements Command {
    private final PresentationReceiver receiver;

    /**
     * Constructor that takes a receiver
     * @param receiver The presentation receiver that will handle the operation
     */
    public PreviousSlideCommand(PresentationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.prevSlide();
    }
}