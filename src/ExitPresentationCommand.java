/**
 * Command to exit the presentation
 */
public class ExitPresentationCommand implements Command {
    private final PresentationReceiver receiver;

    /**
     * Constructor that takes a receiver
     * @param receiver The presentation receiver that will handle the operation
     */
    public ExitPresentationCommand(PresentationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.exitPresentation();
    }
} 