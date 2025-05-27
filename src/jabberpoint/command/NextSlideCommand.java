package jabberpoint.command;

import jabberpoint.model.PresentationReceiver;

/**
 * Command to move to the next slide in the presentation
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.1 2025/04/01
 */
public class NextSlideCommand extends Command {
    public NextSlideCommand(PresentationReceiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        this.receiver.nextSlide();
    }
}