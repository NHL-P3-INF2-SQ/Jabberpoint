package jabberpoint.model;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a text item within a slide.
 * This class handles the rendering and layout of text content,
 * including word wrapping and styling.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.0 2025/04/01
 */
public class TextItem extends SlideItem {
    
    /**
     * The text content of this item.
     */
    private final String text;
    
    /**
     * Default text to use when no text is provided.
     */
    private static final String EMPTY_TEXT = "No Text Given";

    /**
     * Creates a new TextItem with the specified level and text content.
     *
     * @param level The indentation level of this item
     * @param text The text content to display
     */
    public TextItem(int level, String text) {
        super(level);
        this.text = text;
    }

    /**
     * Creates an empty TextItem with default values.
     */
    public TextItem() {
        this(0, EMPTY_TEXT);
    }

    /**
     * Gets the text content of this item.
     *
     * @return The text content, or an empty string if text is null
     */
    public String getText() {
        return this.text == null ? "" : this.text;
    }

    /**
     * Creates an AttributedString with the appropriate style and scale.
     *
     * @param style The style to apply to the text
     * @param scale The scale factor to apply
     * @return An AttributedString with the applied style
     */
    private AttributedString getAttributedString(StyleAttributes style, float scale) {
        AttributedString attrStr = new AttributedString(this.getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, this.getText().length());
        return attrStr;
    }

    /**
     * Calculates the bounding box for this text item.
     *
     * @param graphics The graphics context
     * @param observer The image observer
     * @param scale The scale factor to apply
     * @param style The style to use for rendering
     * @return A Rectangle representing the bounding box
     */
    @Override
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, StyleAttributes style) {
        List<TextLayout> layouts = this.getLayouts(graphics, style, scale);
        int xSize = 0;
        int ySize = (int) (style.getLeading() * scale);
        
        for (TextLayout layout : layouts) {
            Rectangle2D bounds = layout.getBounds();
            xSize = Math.max(xSize, (int) bounds.getWidth());
            if (bounds.getHeight() > 0) {
                ySize += bounds.getHeight();
            }
            ySize += layout.getLeading() + layout.getDescent();
        }
        
        return new Rectangle((int) (style.getIndent() * scale), 0, xSize, ySize);
    }

    /**
     * Draws the text item on the specified graphics context.
     *
     * @param x The x-coordinate for drawing
     * @param y The y-coordinate for drawing
     * @param scale The scale factor to apply
     * @param graphics The graphics context to draw on
     * @param style The style to use for rendering
     * @param observer The image observer
     */
    @Override
    public void draw(int x, int y, float scale, Graphics graphics, StyleAttributes style, ImageObserver observer) {
        if (this.getText().isEmpty()) {
            return;
        }
        
        List<TextLayout> layouts = this.getLayouts(graphics, style, scale);
        Point pen = new Point(
            x + (int)(style.getIndent() * scale),
            y + (int)(style.getLeading() * scale)
        );
        
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(style.getColor());
        
        for (TextLayout layout : layouts) {
            pen.y += layout.getAscent();
            layout.draw(graphics2D, pen.x, pen.y);
            pen.y += layout.getDescent();
        }
    }

    /**
     * Creates a list of TextLayout objects for the text content.
     *
     * @param graphics The graphics context
     * @param style The style to use for rendering
     * @param scale The scale factor to apply
     * @return A list of TextLayout objects
     */
    private List<TextLayout> getLayouts(Graphics graphics, StyleAttributes style, float scale) {
        List<TextLayout> layouts = new ArrayList<>();
        AttributedString attrStr = this.getAttributedString(style, scale);
        Graphics2D graphics2D = (Graphics2D) graphics;
        FontRenderContext frc = graphics2D.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        float wrappingWidth = (Slide.WIDTH - style.getIndent()) * scale;
        
        while (measurer.getPosition() < this.getText().length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }
        
        return layouts;
    }

    /**
     * Returns a string representation of this TextItem.
     *
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        return String.format("TextItem[%d,%s]", this.getLevel(), this.getText());
    }
}
