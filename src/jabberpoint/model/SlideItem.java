package jabberpoint.model;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * Abstract base class for items that can be displayed on a slide.
 * All slide items have drawing functionality and can calculate their bounding box.
 * 
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public abstract class SlideItem {
	/**
	 * The indentation level of this item.
	 */
	private int level = 0;

	/**
	 * Creates a new SlideItem with the specified level.
	 *
	 * @param level The indentation level
	 */
	public SlideItem(int level) {
		this.level = level;
	}

	/**
	 * Creates a new SlideItem with default level 0.
	 */
	public SlideItem() {
		this(0);
	}

	/**
	 * Gets the indentation level of this item.
	 *
	 * @return The level value
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
	 * @param style The style attributes to use
	 * @return A Rectangle representing the bounding box
	 */
	public abstract Rectangle getBoundingBox(Graphics graphics, 
			ImageObserver observer, float scale, StyleAttributes style);

	/**
	 * Draws this item on the specified graphics context.
	 *
	 * @param x The x-coordinate for drawing
	 * @param y The y-coordinate for drawing
	 * @param scale The scale factor to apply
	 * @param graphics The graphics context to draw on
	 * @param style The style attributes to use
	 * @param observer The image observer
	 */
	public abstract void draw(int x, int y, float scale, 
			Graphics graphics, StyleAttributes style, ImageObserver observer);
}
