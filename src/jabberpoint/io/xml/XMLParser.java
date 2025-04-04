package jabberpoint.io.xml;

import java.io.File;
import org.w3c.dom.Document;

/**
 * Interface for XML parsing operations.
 */
public interface XMLParser {
    Document parseXML(File file) throws XMLParserException;
} 