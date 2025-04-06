package jabberpoint.io.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.io.PresentationReader;
import jabberpoint.util.ErrorHandler;

/**
 * Implementation of PresentationReader for XML files.
 */
public class XMLReader implements PresentationReader {
    private static final String SHOW_TITLE = "showtitle";
    private static final String SLIDE = "slide";
    private static final String SLIDE_TITLE = "title";
    private static final String ITEM = "item";
    
    private final XMLParser parser;
    private final List<SlideItemSerializer> serializers;
    
    public XMLReader(XMLParser parser, List<SlideItemSerializer> serializers) {
        this.parser = parser;
        this.serializers = serializers;
    }
    
    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
        try {
            Document document = parser.parseXML(new File(filename));
            Element root = document.getDocumentElement();
            
            // Set presentation title
            presentation.setTitle(getTitle(root, SHOW_TITLE));
            
            // Load slides
            NodeList slides = root.getElementsByTagName(SLIDE);
            for (int i = 0; i < slides.getLength(); i++) {
                loadSlide(presentation, (Element) slides.item(i));
            }
        } catch (XMLParserException e) {
            ErrorHandler.handleParserError(e, null);
            throw new IOException("Failed to parse XML file", e);
        }
    }
    
    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }
    
    private void loadSlide(Presentation presentation, Element slideElement) throws XMLParserException {
        Slide slide = new Slide();
        slide.setTitle(getTitle(slideElement, SLIDE_TITLE));
        presentation.append(slide);
        
        NodeList items = slideElement.getElementsByTagName(ITEM);
        for (int i = 0; i < items.getLength(); i++) {
            Element itemElement = (Element) items.item(i);
            String kind = itemElement.getAttribute("kind");
            
            SlideItem item = deserializeItem(itemElement, kind);
            if (item != null) {
                slide.append(item);
            }
        }
    }

    private SlideItem deserializeItem(Element element, String kind) throws XMLParserException {
        SlideItemSerializer appropriateSerializer = null;
        if ("text".equals(kind)) {
            appropriateSerializer = serializers.stream()
                    .filter(s -> s instanceof TextItemSerializer)
                    .findFirst()
                    .orElse(null);
        } else if ("image".equals(kind)) {
            appropriateSerializer = serializers.stream()
                    .filter(s -> s instanceof BitmapItemSerializer)
                    .findFirst()
                    .orElse(null);
        }

        if (appropriateSerializer == null) {
            throw new XMLParserException("No suitable serializer found for item kind: " + kind);
        }

        try {
            return appropriateSerializer.deserialize(element);
        } catch (XMLParserException e) {
            throw new XMLParserException("Failed to deserialize item of kind: " + kind, e);
        }
    }
} 