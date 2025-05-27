package jabberpoint.command;
import jabberpoint.model.PresentationReceiver;

/**
 * Command to save the current presentation
 *
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 * @version 1.1 2025/05/27
 */
public class SavePresentationCommand extends Command {
    public SavePresentationCommand(PresentationReceiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        this.receiver.savePresentation();
    }
}