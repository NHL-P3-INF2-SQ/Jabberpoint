import java.awt.Color;
import java.awt.Font;

/**
 * Represents a visual style for slide items, including indentation,
 * color, font properties, and line spacing (leading).
 * The style number corresponds directly to the item level in slides.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class Style {
    
    /**
     * Array of predefined styles for different item levels.
     */
    private static Style[] styles;
    
    /**
     * The default font family name.
     */
    private static final String FONT_NAME = "Helvetica";
    
    /**
     * The indentation from the left margin in pixels.
     */
    private final int indent;
    
    /**
     * The color of the text.
     */
    private final Color color;
    
    /**
     * The font object for rendering text.
     */
    private final Font font;
    
    /**
     * The base font size in points.
     */
    private final int fontSize;
    
    /**
     * The line spacing (leading) in pixels.
     */
    private final int leading;

    /**
     * Creates the predefined styles for different item levels.
     * Each style has specific indentation, color, font size, and leading values.
     */
    public static void createStyles() {
        styles = new Style[5];
        styles[0] = new Style(0, Color.red, 48, 20);    // Level 0: Title style
        styles[1] = new Style(20, Color.blue, 40, 10);  // Level 1: Main heading
        styles[2] = new Style(50, Color.black, 36, 10); // Level 2: Subheading
        styles[3] = new Style(70, Color.black, 30, 10); // Level 3: Body text
        styles[4] = new Style(90, Color.black, 24, 10); // Level 4: Detail text
    }

    /**
     * Gets the style for the specified level.
     *
     * @param level The item level (0-4)
     * @return The style corresponding to the level
     */
    public static Style getStyle(int level) {
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
        this.indent = indent;
        this.color = color;
        this.fontSize = points;
        this.leading = leading;
        this.font = new Font(FONT_NAME, Font.BOLD, points);
    }

    /**
     * Returns a string representation of this Style.
     *
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        return String.format("[%d,%s; %d on %d]", 
            this.indent, this.color, this.fontSize, this.leading);
    }

    /**
     * Gets a scaled version of the font.
     *
     * @param scale The scale factor to apply
     * @return A new Font object with the scaled size
     */
    public Font getFont(float scale) {
        return this.font.deriveFont(this.fontSize * scale);
    }
    
    /**
     * Gets the indentation value.
     *
     * @return The indentation in pixels
     */
    public int getIndent() {
        return this.indent;
    }
    
    /**
     * Gets the text color.
     *
     * @return The Color object
     */
    public Color getColor() {
        return this.color;
    }
    
    /**
     * Gets the line spacing (leading).
     *
     * @return The leading in pixels
     */
    public int getLeading() {
        return this.leading;
    }
    
    /**
     * Gets the base font size.
     *
     * @return The font size in points
     */
    public int getFontSize() {
        return this.fontSize;
    }
}
