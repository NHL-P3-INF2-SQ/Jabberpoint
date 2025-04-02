package jabberpoint.model;

import java.awt.Color;
import java.awt.Font;

/**
 * Abstract base class for style implementations.
 * Provides common functionality and default implementations for styles
 * while allowing specific style types to customize behavior.
 * 
 * @author Bram Suurd
 * @version 1.7 2024/04/02
 */
public abstract class AbstractStyle implements StyleAttributes {
    /**
     * The default font family name.
     */
    protected static final String DEFAULT_FONT_NAME = "Helvetica";
    
    /**
     * The indentation from the left margin in pixels.
     */
    protected final int indent;
    
    /**
     * The color of the text.
     */
    protected final Color color;
    
    /**
     * The font object for rendering text.
     */
    protected final Font font;
    
    /**
     * The base font size in points.
     */
    protected final int fontSize;
    
    /**
     * The line spacing (leading) in pixels.
     */
    protected final int leading;

    /**
     * Creates a new AbstractStyle with the specified properties.
     *
     * @param indent The indentation from the left margin in pixels
     * @param color The color of the text
     * @param fontSize The font size in points
     * @param leading The line spacing in pixels
     * @param fontName The font family name to use
     */
    protected AbstractStyle(int indent, Color color, int fontSize, int leading, String fontName) {
        this.indent = indent;
        this.color = color;
        this.fontSize = fontSize;
        this.leading = leading;
        this.font = new Font(fontName, Font.BOLD, fontSize);
    }

    @Override
    public int getIndent() {
        return this.indent;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public Font getFont(float scale) {
        return this.font.deriveFont(this.fontSize * scale);
    }

    @Override
    public int getLeading() {
        return this.leading;
    }

    @Override
    public int getFontSize() {
        return this.fontSize;
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
} 