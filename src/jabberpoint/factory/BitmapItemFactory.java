package jabberpoint.factory;

import jabberpoint.model.SlideItem;
import jabberpoint.model.BitmapItem;
import jabberpoint.util.ErrorHandler;

/**
 * Concrete factory for creating bitmap items.
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 */
public class BitmapItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(int level, String content) {
        if (content == null) {
            ErrorHandler.handleValidationError("Image path cannot be null", null);
            throw new IllegalArgumentException("Image path cannot be null");
        }
        return new BitmapItem(level, content);
    }
}