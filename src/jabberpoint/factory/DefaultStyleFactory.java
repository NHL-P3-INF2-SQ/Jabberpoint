package jabberpoint.factory;

import java.awt.Color;
import jabberpoint.model.Style;
import jabberpoint.model.StyleAttributes;

/**
 * Default concrete implementation of StyleFactory.
 * Creates styles with predefined settings for different presentation elements.
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 */
public class DefaultStyleFactory implements StyleFactory {
    @Override
    public StyleAttributes createStyle() {
        // Default style with moderate settings
        return new Style(40, Color.black, 32, 10);
    }

    @Override
    public StyleAttributes createTitleStyle() {
        return new Style(0, Color.red, 48, 20);
    }

    @Override
    public StyleAttributes createHeadingStyle() {
        return new Style(20, Color.blue, 40, 10);
    }

    @Override
    public StyleAttributes createSubheadingStyle() {
        return new Style(50, Color.black, 36, 10);
    }

    @Override
    public StyleAttributes createBodyStyle() {
        return new Style(70, Color.black, 30, 10);
    }

    @Override
    public StyleAttributes createDetailStyle() {
        return new Style(90, Color.black, 24, 10);
    }

    @Override
    public StyleAttributes createCustomStyle(int indent, Color color, int fontSize, int leading) {
        return new Style(indent, color, fontSize, leading);
    }
}