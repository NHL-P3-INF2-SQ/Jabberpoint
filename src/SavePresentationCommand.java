/**
 * Command to save the current presentation
 */
public class SavePresentationCommand implements Command {
    private final PresentationReceiver receiver;

    /**
     * Constructor that takes a receiver
     * @param receiver The presentation receiver that will handle the operation
     */
    public SavePresentationCommand(PresentationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.savePresentation();
    }
} 