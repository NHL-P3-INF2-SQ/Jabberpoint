package jabberpoint.model;

import jabberpoint.factory.SlideItemFactory;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

/**
 * Represents a slide in a presentation.
 * This class manages the content and rendering of a single slide,
 * including its title and collection of slide items.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class Slide {
	
	/**
	 * The standard width of a slide in pixels.
	 */
	public static final int WIDTH = 1200;
	
	/**
	 * The standard height of a slide in pixels.
	 */
	public static final int HEIGHT = 800;
	
	/**
	 * The title of the slide.
	 */
	private String title;
	
	/**
	 * The collection of items on the slide.
	 */
	private final Vector<SlideItem> items;

	/**
	 * Creates a new empty slide.
	 */
	public Slide() {
		this.items = new Vector<>();
	}

	/**
	 * Adds a slide item to the slide.
	 *
	 * @param item The slide item to add
	 */
	public void append(SlideItem item) {
		this.items.addElement(item);
	}

	/**
	 * Gets the title of the slide.
	 *
	 * @return The slide title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title of the slide.
	 *
	 * @param title The new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Creates and adds a text item to the slide.
	 *
	 * @param level The indentation level
	 * @param message The text content
	 */
	public void append(int level, String message) {
		this.append(SlideItemFactory.createTextItem(level, message));
	}

	/**
	 * Gets a specific slide item by its index.
	 *
	 * @param index The index of the item
	 * @return The slide item at the specified index
	 */
	public SlideItem getSlideItem(int index) {
		return this.items.elementAt(index);
	}

	/**
	 * Gets all slide items.
	 *
	 * @return A Vector containing all slide items
	 */
	public Vector<SlideItem> getSlideItems() {
		return this.items;
	}

	/**
	 * Gets the number of items in the slide.
	 *
	 * @return The number of items
	 */
	public int getSize() {
		return this.items.size();
	}

	/**
	 * Draws the slide on the specified graphics context.
	 *
	 * @param graphics The graphics context
	 * @param area The area to draw in
	 * @param observer The image observer
	 */
	public void draw(Graphics graphics, Rectangle area, ImageObserver observer) {
		float scale = this.getScale(area);
		int yPosition = area.y;

		// Draw the title
		SlideItem titleItem = new TextItem(0, this.getTitle());
		Style titleStyle = Style.getStyle(titleItem.getLevel());
		titleItem.draw(area.x, yPosition, scale, graphics, titleStyle, observer);
		yPosition += titleItem.getBoundingBox(graphics, observer, scale, titleStyle).height;

		// Draw each slide item
		for (SlideItem item : this.getSlideItems()) {
			Style itemStyle = Style.getStyle(item.getLevel());
			item.draw(area.x, yPosition, scale, graphics, itemStyle, observer);
			yPosition += item.getBoundingBox(graphics, observer, scale, itemStyle).height;
		}
	}

	/**
	 * Calculates the scale factor for drawing the slide.
	 *
	 * @param area The area to fit the slide into
	 * @return The scale factor
	 */
	private float getScale(Rectangle area) {
		return Math.min(
			(float) area.width / WIDTH,
			(float) area.height / HEIGHT
		);
	}
}
