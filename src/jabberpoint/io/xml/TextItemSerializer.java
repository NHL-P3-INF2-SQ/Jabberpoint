package jabberpoint.io.xml;

import org.w3c.dom.Element;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;

/**
 * Serializer for TextItem slide items.
 */
public class TextItemSerializer implements SlideItemSerializer {
    private static final String KIND = "text";
    
    @Override
    public boolean supports(SlideItem item) {
        return item instanceof TextItem;
    }
    
    @Override
    public String serialize(SlideItem item) {
        if (!supports(item)) {
            throw new IllegalArgumentException("Item must be a TextItem");
        }
        TextItem textItem = (TextItem) item;
        return String.format("kind=\"%s\" level=\"%d\">%s", 
            KIND, item.getLevel(), textItem.getText());
    }
    
    @Override
    public SlideItem deserialize(Element element) throws XMLParserException {
        try {
            int level = Integer.parseInt(element.getAttribute("level"));
            String text = element.getTextContent();
            return new TextItem(level, text);
        } catch (NumberFormatException e) {
            throw new XMLParserException("Invalid level number for text item", e);
        }
    }
} 