package jabberpoint.model;

import java.awt.Color;
import java.awt.Font;

/**
 * Interface defining the contract for style attributes in presentations.
 * This interface provides methods to access visual styling properties
 * used in rendering presentation elements.
 * 
 * @author Bram Suurd
 * @version 1.0 2025/04/01
 */
public interface StyleAttributes {
    /**
     * Gets the indentation value.
     *
     * @return The indentation in pixels
     */
    int getIndent();
    
    /**
     * Gets the text color.
     *
     * @return The Color object
     */
    Color getColor();
    
    /**
     * Gets a scaled version of the font.
     *
     * @param scale The scale factor to apply
     * @return A new Font object with the scaled size
     */
    Font getFont(float scale);
    
    /**
     * Gets the line spacing (leading).
     *
     * @return The leading in pixels
     */
    int getLeading();
    
    /**
     * Gets the base font size.
     *
     * @return The font size in points
     */
    int getFontSize();
}