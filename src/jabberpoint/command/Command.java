package jabberpoint.command;

/**
 * Command interface defining the contract for all presentation commands
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 */
public interface Command {
    /**
     * Execute the command
     */
    void execute();
}