package jabberpoint.factory;

import java.awt.Color;
import jabberpoint.model.StyleAttributes;

/**
 * Abstract factory interface for creating style implementations.
 * 
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.1 2025/04/01
 */
public interface StyleFactory {
    /**
     * Factory method to create a style.
     * 
     * @return A StyleAttributes instance
     */
    StyleAttributes createStyle();

    /**
     * Creates a title style.
     *
     * @return A style suitable for titles
     */
    StyleAttributes createTitleStyle();

    /**
     * Creates a heading style.
     *
     * @return A style suitable for main headings
     */
    StyleAttributes createHeadingStyle();

    /**
     * Creates a subheading style.
     *
     * @return A style suitable for subheadings
     */
    StyleAttributes createSubheadingStyle();

    /**
     * Creates a body text style.
     *
     * @return A style suitable for body text
     */
    StyleAttributes createBodyStyle();

    /**
     * Creates a detail text style.
     *
     * @return A style suitable for detail text
     */
    StyleAttributes createDetailStyle();

    /**
     * Creates a custom style with the specified properties.
     *
     * @param indent   The indentation from the left margin in pixels
     * @param color    The color of the text
     * @param fontSize The font size in points
     * @param leading  The line spacing in pixels
     * @return A custom style with the specified properties
     */
    StyleAttributes createCustomStyle(int indent, Color color, int fontSize, int leading);
}