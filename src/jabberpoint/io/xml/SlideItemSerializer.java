package jabberpoint.io.xml;

import org.w3c.dom.Element;
import jabberpoint.model.SlideItem;

/**
 * Interface for serializing and deserializing slide items to/from XML.
 */
public interface SlideItemSerializer {
    String serialize(SlideItem item);
    SlideItem deserialize(Element element) throws XMLParserException;
    boolean supports(SlideItem item);
} 