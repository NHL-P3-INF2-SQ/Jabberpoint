package jabberpoint.factory;

import jabberpoint.model.SlideItem;
import jabberpoint.util.ErrorHandler;

/**
 * Provider class for slide item factories.
 * This class maintains backward compatibility with the existing codebase
 * while implementing the Factory Method pattern.
 */
public class SlideItemFactoryProvider {
    private static final TextItemFactory textFactory = new TextItemFactory();
    private static final BitmapItemFactory bitmapFactory = new BitmapItemFactory();

    /**
     * Creates a slide item based on the specified type, level, and content.
     * 
     * @param kind    The type of item to create ("text" or "image")
     * @param level   The indentation level of the item
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
                return textFactory.createSlideItem(level, content);
            case "image":
                return bitmapFactory.createSlideItem(level, content);
            default:
                ErrorHandler.handleValidationError("Unknown item kind: " + kind, null);
                throw new IllegalArgumentException("Unknown item kind: " + kind);
        }
    }

    /**
     * Gets the appropriate factory for the specified kind.
     * 
     * @param kind The type of item factory to get ("text" or "image")
     * @return The appropriate SlideItemFactory
     * @throws IllegalArgumentException If the item type is not recognized
     */
    public static SlideItemFactory getFactory(String kind) {
        if (kind == null) {
            ErrorHandler.handleValidationError("Item kind cannot be null", null);
            throw new IllegalArgumentException("Item kind cannot be null");
        }

        switch (kind.toLowerCase()) {
            case "text":
                return textFactory;
            case "image":
                return bitmapFactory;
            default:
                ErrorHandler.handleValidationError("Unknown item kind: " + kind, null);
                throw new IllegalArgumentException("Unknown item kind: " + kind);
        }
    }
}