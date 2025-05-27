package jabberpoint.command;

import jabberpoint.model.PresentationReceiver;

/**
 * Abstract Command class defining the contract for all presentation commands and providing a shared receiver field.
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 * @version 1.1 2025/05/27
 */
public abstract class Command {
    protected final PresentationReceiver receiver;

    /**
     * Constructor that takes a receiver
     * @param receiver The presentation receiver that will handle the operation
     */
    public Command(PresentationReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Execute the command
     */
    public abstract void execute();
}