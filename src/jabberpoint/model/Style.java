package jabberpoint.model;
import java.awt.Color;
import jabberpoint.factory.StyleFactory;

/**
 * Represents a visual style for slide items, including indentation,
 * color, font properties, and line spacing (leading).
 * The style number corresponds directly to the item level in slides.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class Style extends AbstractStyle {
    
    /**
     * Array of predefined styles for different item levels.
     */
    private static StyleAttributes[] styles;

    /**
     * Creates the predefined styles for different item levels.
     * Each style has specific indentation, color, font size, and leading values.
     */
    public static void createStyles() {
        styles = new StyleAttributes[5];
        styles[0] = StyleFactory.createTitleStyle();      // Level 0: Title style
        styles[1] = StyleFactory.createHeadingStyle();    // Level 1: Main heading
        styles[2] = StyleFactory.createSubheadingStyle(); // Level 2: Subheading
        styles[3] = StyleFactory.createBodyStyle();       // Level 3: Body text
        styles[4] = StyleFactory.createDetailStyle();     // Level 4: Detail text
    }

    /**
     * Gets the style for the specified level.
     *
     * @param level The item level (0-4)
     * @return The style corresponding to the level
     */
    public static StyleAttributes getStyle(int level) {
        if (level >= styles.length) {
            level = styles.length - 1;
        }
        return styles[level];
    }

    /**
     * Creates a new Style with the specified properties.
     *
     * @param indent The indentation from the left margin in pixels
     * @param color The color of the text
     * @param points The font size in points
     * @param leading The line spacing in pixels
     */
    public Style(int indent, Color color, int points, int leading) {
        super(indent, color, points, leading, DEFAULT_FONT_NAME);
    }
}
