package jabberpoint.factory;

import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;
import jabberpoint.util.ErrorHandler;

/**
 * Concrete factory for creating text items.
 */
public class TextItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(int level, String content) {
        if (content == null) {
            ErrorHandler.handleValidationError("Text content cannot be null", null);
            throw new IllegalArgumentException("Text content cannot be null");
        }
        return new TextItem(level, content);
    }
}