package jabberpoint.io.xml;

/**
 * Custom exception for XML parsing errors.
 */
public class XMLParserException extends Exception {
    public XMLParserException(String message) {
        super(message);
    }

    public XMLParserException(String message, Throwable cause) {
        super(message, cause);
    }
} 