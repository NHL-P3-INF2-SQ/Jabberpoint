package jabberpoint.factory;

import java.awt.Color;
import jabberpoint.model.Style;
import jabberpoint.model.StyleAttributes;

/**
 * Factory class for creating different style implementations.
 * This class follows the Factory pattern to provide a centralized way
 * to create style objects with different configurations.
 * 
 * @author Bram Suurd
 * @version 1.7 2024/04/01
 */
public class StyleFactory {
    /**
     * Creates a title style.
     *
     * @return A style suitable for titles
     */
    public static StyleAttributes createTitleStyle() {
        return new Style(0, Color.red, 48, 20);
    }

    /**
     * Creates a heading style.
     *
     * @return A style suitable for main headings
     */
    public static StyleAttributes createHeadingStyle() {
        return new Style(20, Color.blue, 40, 10);
    }

    /**
     * Creates a subheading style.
     *
     * @return A style suitable for subheadings
     */
    public static StyleAttributes createSubheadingStyle() {
        return new Style(50, Color.black, 36, 10);
    }

    /**
     * Creates a body text style.
     *
     * @return A style suitable for body text
     */
    public static StyleAttributes createBodyStyle() {
        return new Style(70, Color.black, 30, 10);
    }

    /**
     * Creates a detail text style.
     *
     * @return A style suitable for detail text
     */
    public static StyleAttributes createDetailStyle() {
        return new Style(90, Color.black, 24, 10);
    }

    /**
     * Creates a custom style with the specified properties.
     *
     * @param indent The indentation from the left margin in pixels
     * @param color The color of the text
     * @param fontSize The font size in points
     * @param leading The line spacing in pixels
     * @return A custom style with the specified properties
     */
    public static StyleAttributes createCustomStyle(int indent, Color color, int fontSize, int leading) {
        return new Style(indent, color, fontSize, leading);
    }
} 