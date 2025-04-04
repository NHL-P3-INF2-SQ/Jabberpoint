package jabberpoint.io.xml;

import org.w3c.dom.Element;
import jabberpoint.model.SlideItem;
import jabberpoint.model.BitmapItem;

/**
 * Serializer for BitmapItem slide items.
 */
public class BitmapItemSerializer implements SlideItemSerializer {
    private static final String KIND = "image";
    
    @Override
    public boolean supports(SlideItem item) {
        return item instanceof BitmapItem;
    }
    
    @Override
    public String serialize(SlideItem item) {
        if (!supports(item)) {
            throw new IllegalArgumentException("Item must be a BitmapItem");
        }
        BitmapItem bitmapItem = (BitmapItem) item;
        return String.format("kind=\"%s\" level=\"%d\">%s", 
            KIND, item.getLevel(), bitmapItem.getImagePath());
    }
    
    @Override
    public SlideItem deserialize(Element element) throws XMLParserException {
        try {
            int level = Integer.parseInt(element.getAttribute("level"));
            String imagePath = element.getTextContent();
            return new BitmapItem(level, imagePath);
        } catch (NumberFormatException e) {
            throw new XMLParserException("Invalid level number for bitmap item", e);
        }
    }
} 