package jabberpoint.util;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * Centralized error handling system for JabberPoint.
 * This class provides standardized error handling and reporting methods
 * to ensure consistent error management across the application.
 *
 * @author Bram Suurd
 * @version 1.0 2025/04/01
 */
public final class ErrorHandler {

    /**
     * Standard error messages
     */
    private static final String ERROR_TITLE = "JabberPoint Error";
    private static final String IO_ERROR_PREFIX = "IO Error: ";
    private static final String PARSER_ERROR_PREFIX = "Parser Error: ";
    private static final String VALIDATION_ERROR_PREFIX = "Validation Error: ";
    private static final String GENERAL_ERROR_PREFIX = "Error: ";

    /**
     * Private constructor to prevent instantiation
     */
    private ErrorHandler() {
        // Utility class should not be instantiated
    }

    /**
     * Checks if the application is running in a test environment.
     * 
     * @return true if running in a test environment, false otherwise
     */
    private static boolean isTestEnvironment() {
        // Check for common test environment indicators
        String testEnv = System.getProperty("test.environment");
        if (testEnv != null && testEnv.equals("true")) {
            return true;
        }
        
        // Check if running from a test runner
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().contains("org.junit")) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Handles IO-related errors with a standardized error dialog.
     *
     * @param ex     The exception that occurred
     * @param parent The parent component for the error dialog
     */
    public static void handleIOError(Exception ex, Component parent) {
        showErrorDialog(IO_ERROR_PREFIX + ex.getMessage(), parent);
    }

    /**
     * Handles XML parsing errors with a standardized error dialog.
     *
     * @param ex     The exception that occurred
     * @param parent The parent component for the error dialog
     */
    public static void handleParserError(Exception ex, Component parent) {
        showErrorDialog(PARSER_ERROR_PREFIX + ex.getMessage(), parent);
    }

    /**
     * Handles validation errors with a standardized error dialog.
     *
     * @param message The validation error message
     * @param parent  The parent component for the error dialog
     */
    public static void handleValidationError(String message, Component parent) {
        showErrorDialog(VALIDATION_ERROR_PREFIX + message, parent);
    }

    /**
     * Handles general application errors with a standardized error dialog.
     *
     * @param ex     The exception that occurred
     * @param parent The parent component for the error dialog
     */
    public static void handleGeneralError(Exception ex, Component parent) {
        showErrorDialog(GENERAL_ERROR_PREFIX + ex.getMessage(), parent);
    }

    /**
     * Shows a standardized error dialog with the specified message.
     * Suppresses dialog display when running in a test environment.
     *
     * @param message The error message to display
     * @param parent  The parent component for the error dialog
     */
    private static void showErrorDialog(String message, Component parent) {
        // Skip showing dialog if in test environment
        if (isTestEnvironment()) {
            System.err.println(message);
            return;
        }
        
        JOptionPane.showMessageDialog(
                parent,
                message,
                ERROR_TITLE,
                JOptionPane.ERROR_MESSAGE);
    }
}