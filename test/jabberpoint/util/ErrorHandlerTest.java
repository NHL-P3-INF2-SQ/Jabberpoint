package jabberpoint.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;

public class ErrorHandlerTest {

    private static class TestException extends Exception {
        public TestException(String message) {
            super(message);
        }
    }

    @Test
    public void testPrivateConstructor() {
        assertThrows(IllegalAccessException.class, () -> {
            ErrorHandler.class.getDeclaredConstructor().newInstance();
        });
    }

    @Test
    public void testHandleIOError() {
        if (GraphicsEnvironment.isHeadless()) {
            // Skip test in headless environment
            return;
        }

        Exception ex = new IOException("Test IO Error");
        assertDoesNotThrow(() -> {
            ErrorHandler.handleIOError(ex, null);
        });
    }

    @Test
    public void testHandleParserError() {
        if (GraphicsEnvironment.isHeadless()) {
            // Skip test in headless environment
            return;
        }

        Exception ex = new TestException("Test Parser Error");
        assertDoesNotThrow(() -> {
            ErrorHandler.handleParserError(ex, null);
        });
    }

    @Test
    public void testHandleValidationError() {
        if (GraphicsEnvironment.isHeadless()) {
            // Skip test in headless environment
            return;
        }

        assertDoesNotThrow(() -> {
            ErrorHandler.handleValidationError("Test Validation Error", null);
        });
    }

    @Test
    public void testHandleGeneralError() {
        if (GraphicsEnvironment.isHeadless()) {
            // Skip test in headless environment
            return;
        }

        Exception ex = new TestException("Test General Error");
        assertDoesNotThrow(() -> {
            ErrorHandler.handleGeneralError(ex, null);
        });
    }

    @Test
    public void testErrorMessageFormat() throws Exception {
        if (GraphicsEnvironment.isHeadless()) {
            // Skip test in headless environment
            return;
        }

        // Use reflection to access private constants
        Field titleField = ErrorHandler.class.getDeclaredField("ERROR_TITLE");
        titleField.setAccessible(true);
        String errorTitle = (String) titleField.get(null);

        Field ioErrorPrefixField = ErrorHandler.class.getDeclaredField("IO_ERROR_PREFIX");
        ioErrorPrefixField.setAccessible(true);
        String ioErrorPrefix = (String) ioErrorPrefixField.get(null);

        // Test IO error message format
        Exception ex = new IOException("Test Message");
        assertEquals("JabberPoint Error", errorTitle);
        assertTrue(ioErrorPrefix.startsWith("IO Error:"));
    }

    @Test
    public void testNullExceptionHandling() {
        if (GraphicsEnvironment.isHeadless()) {
            // Skip test in headless environment
            return;
        }

        assertDoesNotThrow(() -> {
            ErrorHandler.handleIOError(new Exception(), null);
            ErrorHandler.handleParserError(new Exception(), null);
            ErrorHandler.handleGeneralError(new Exception(), null);
        });
    }

    @Test
    public void testNullMessageHandling() {
        if (GraphicsEnvironment.isHeadless()) {
            // Skip test in headless environment
            return;
        }

        assertDoesNotThrow(() -> {
            ErrorHandler.handleValidationError(null, null);
        });
    }
}