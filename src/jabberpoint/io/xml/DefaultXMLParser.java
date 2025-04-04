package jabberpoint.io.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 * Default implementation of XMLParser using DocumentBuilder.
 */
public class DefaultXMLParser implements XMLParser {
    @Override
    public Document parseXML(File file) throws XMLParserException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();    
            return builder.parse(file);
        } catch (Exception e) {
            throw new XMLParserException("Error parsing XML file", e);
        }
    }
} 