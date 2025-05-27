package jabberpoint.command;
import jabberpoint.model.PresentationReceiver;

/**
 * Command to open an existing presentation
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 * @version 1.1 2025/05/27
 */
public class OpenPresentationCommand extends Command {
    public OpenPresentationCommand(PresentationReceiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        this.receiver.openPresentation();
    }
}