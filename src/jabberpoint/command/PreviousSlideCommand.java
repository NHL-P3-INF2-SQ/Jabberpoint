package jabberpoint.command;
import jabberpoint.model.PresentationReceiver;

/**
 * Command to move to the previous slide in the presentation    
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 * @version 1.1 2025/05/27
 */
public class PreviousSlideCommand extends Command {
    public PreviousSlideCommand(PresentationReceiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        this.receiver.prevSlide();
    }
}