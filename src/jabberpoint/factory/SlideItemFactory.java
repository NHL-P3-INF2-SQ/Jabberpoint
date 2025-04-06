package jabberpoint.factory;

import jabberpoint.model.SlideItem;

/**
 * Abstract factory interface for creating slide items.
 * This interface defines the factory method pattern for creating different
 * types of slide items.
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.1 2025/04/01
 */
public interface SlideItemFactory {
    /**
     * Factory method to create a slide item.
     * 
     * @param level   The indentation level of the item
     * @param content The content of the item (text content or image path)
     * @return A SlideItem instance of the appropriate type
     * @throws IllegalArgumentException If the content is invalid
     */
    SlideItem createSlideItem(int level, String content);
}
