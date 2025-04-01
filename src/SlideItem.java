import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * Abstract base class for items that can be displayed on a slide.
 * All slide items have drawing functionality and maintain their
 * indentation level within the slide.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public abstract class SlideItem {
    
    /**
     * The indentation level of this item within the slide.
     */
    private final int level;

    /**
     * Creates a new SlideItem with the specified indentation level.
     *
     * @param level The indentation level of this item
     */
    public SlideItem(int level) {
        this.level = level;
    }

    /**
     * Creates a new SlideItem with default indentation level (0).
     */
    public SlideItem() {
        this(0);
    }

    /**
     * Gets the indentation level of this item.
     *
     * @return The indentation level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Calculates the bounding box for this item.
     *
     * @param graphics The graphics context
     * @param observer The image observer
     * @param scale The scale factor to apply
     * @param style The style to use for rendering
     * @return A Rectangle representing the bounding box
     */
    public abstract Rectangle getBoundingBox(
        Graphics graphics,
        ImageObserver observer,
        float scale,
        Style style
    );

    /**
     * Draws this item on the specified graphics context.
     *
     * @param x The x-coordinate for drawing
     * @param y The y-coordinate for drawing
     * @param scale The scale factor to apply
     * @param graphics The graphics context to draw on
     * @param style The style to use for rendering
     * @param observer The image observer
     */
    public abstract void draw(
        int x,
        int y,
        float scale,
        Graphics graphics,
        Style style,
        ImageObserver observer
    );
}
