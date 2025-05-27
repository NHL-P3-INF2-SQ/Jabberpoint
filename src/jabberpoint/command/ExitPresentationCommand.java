package jabberpoint.command;

import jabberpoint.model.PresentationReceiver;

/**
 * Command to exit the presentation
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 * @version 1.1 2025/05/27
 */
public class ExitPresentationCommand extends Command {
    public ExitPresentationCommand(PresentationReceiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        this.receiver.exitPresentation();
    }
}