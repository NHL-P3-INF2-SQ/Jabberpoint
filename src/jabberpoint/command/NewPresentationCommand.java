package jabberpoint.command;

import jabberpoint.model.PresentationReceiver;

/**
 * Command to create a new presentation
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.1 2025/04/01
 */
public class NewPresentationCommand extends Command {
    public NewPresentationCommand(PresentationReceiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        this.receiver.newPresentation();
    }
}