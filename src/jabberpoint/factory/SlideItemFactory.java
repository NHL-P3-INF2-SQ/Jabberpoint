package jabberpoint.factory;

import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;
import jabberpoint.model.BitmapItem;
import jabberpoint.util.ErrorHandler;

/**
 * Factory class for creating different types of slide items.
 * This class encapsulates the creation logic for text and bitmap items,
 * following the Factory pattern.
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.7 2024/04/01
 */
public class SlideItemFactory {
    
    /**
     * Creates a slide item based on the specified type, level, and content.
     * 
     * @param kind The type of item to create ("text" or "image")
     * @param level The indentation level of the item
     * @param content The content of the item (text content or image path)
     * @return A SlideItem instance of the appropriate type
     * @throws IllegalArgumentException If the item type is not recognized
     */
    public static SlideItem createSlideItem(String kind, int level, String content) {
        if (kind == null) {
            ErrorHandler.handleValidationError("Item kind cannot be null", null);
            throw new IllegalArgumentException("Item kind cannot be null");
        }
        
        switch (kind.toLowerCase()) {
            case "text":
                return createTextItem(level, content);
            case "image":
                return createBitmapItem(level, content);
            default:
                ErrorHandler.handleValidationError("Unknown item kind: " + kind, null);
                throw new IllegalArgumentException("Unknown item kind: " + kind);
        }
    }
    
    /**
     * Creates a text item with the specified level and content.
     * 
     * @param level The indentation level of the text
     * @param text The text content
     * @return A TextItem instance
     */
    public static TextItem createTextItem(int level, String text) {
        return new TextItem(level, text);
    }
    
    /**
     * Creates a bitmap item with the specified level and image path.
     * 
     * @param level The indentation level of the image
     * @param imagePath The path to the image file
     * @return A BitmapItem instance
     */
    public static BitmapItem createBitmapItem(int level, String imagePath) {
        return new BitmapItem(level, imagePath);
    }
}
