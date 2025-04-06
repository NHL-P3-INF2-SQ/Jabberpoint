package jabberpoint.factory;

import java.awt.Color;
import jabberpoint.model.StyleAttributes;

/**
 * Provider class for style factories.
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 */
public class StyleFactoryProvider {
    private static final StyleFactory defaultFactory = new DefaultStyleFactory();

    /**
     * Gets the default style factory instance.
     * 
     * @return The default StyleFactory
     */
    public static StyleFactory getFactory() {
        return defaultFactory;
    }

    /**
     * Creates a title style using the default factory.
     *
     * @return A style suitable for titles
     */
    public static StyleAttributes createTitleStyle() {
        return defaultFactory.createTitleStyle();
    }

    /**
     * Creates a heading style using the default factory.
     *
     * @return A style suitable for main headings
     */
    public static StyleAttributes createHeadingStyle() {
        return defaultFactory.createHeadingStyle();
    }

    /**
     * Creates a subheading style using the default factory.
     *
     * @return A style suitable for subheadings
     */
    public static StyleAttributes createSubheadingStyle() {
        return defaultFactory.createSubheadingStyle();
    }

    /**
     * Creates a body text style using the default factory.
     *
     * @return A style suitable for body text
     */
    public static StyleAttributes createBodyStyle() {
        return defaultFactory.createBodyStyle();
    }

    /**
     * Creates a detail text style using the default factory.
     *
     * @return A style suitable for detail text
     */
    public static StyleAttributes createDetailStyle() {
        return defaultFactory.createDetailStyle();
    }

    /**
     * Creates a custom style with the specified properties using the default
     * factory.
     *
     * @param indent   The indentation from the left margin in pixels
     * @param color    The color of the text
     * @param fontSize The font size in points
     * @param leading  The line spacing in pixels
     * @return A custom style with the specified properties
     */
    public static StyleAttributes createCustomStyle(int indent, Color color, int fontSize, int leading) {
        return defaultFactory.createCustomStyle(indent, color, fontSize, leading);
    }
}