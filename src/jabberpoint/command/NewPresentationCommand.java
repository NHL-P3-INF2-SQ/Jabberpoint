package jabberpoint.command;

import jabberpoint.model.PresentationReceiver;

/**
 * Command to create a new presentation
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.7 2024/04/01
 */
public class NewPresentationCommand implements Command {
    private final PresentationReceiver receiver;

    /**
     * Constructor that takes a receiver
     * @param receiver The presentation receiver that will handle the operation
     */
    public NewPresentationCommand(PresentationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.newPresentation();
    }
} 