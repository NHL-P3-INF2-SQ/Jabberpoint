package jabberpoint.model;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import java.io.IOException;

/**
 * Represents a bitmap image item within a slide.
 * This class is responsible for loading, drawing, and managing bitmap images
 * as slide items. Each BitmapItem contains an image and handles its own rendering
 * based on the provided style and scale parameters.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class BitmapItem extends SlideItem {
    
    /**
     * The loaded image data.
     */
    private BufferedImage bufferedImage;
    
    /**
     * The file path of the image.
     */
    private final String imagePath;
    
    /**
     * Error message constants for file handling.
     */
    private static final String ERROR_FILE_PREFIX = "File ";
    private static final String ERROR_NOT_FOUND = " not found";

    /**
     * Creates a new BitmapItem with the specified level and image path.
     *
     * @param level The indentation level of this item
     * @param imagePath The file path to the image
     */
    public BitmapItem(int level, String imagePath) {
        super(level);
        this.imagePath = imagePath;
        this.loadImage();
    }

    /**
     * Creates an empty BitmapItem with default values.
     */
    public BitmapItem() {
        this(0, null);
    }

    /**
     * Loads the image from the specified file path.
     */
// Load the image from various sources
	private void loadImage() {
		if (imagePath == null) {
			return;
		}
		
		bufferedImage = null;
		
		// Try to load from file system
		try {
			File file = new File(imagePath);
			if (file.exists()) {
				bufferedImage = ImageIO.read(file);
				if (bufferedImage != null) {
					return;
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading from file: " + e.getMessage());
		}
		
		// Try to load from resources
		try {
			// Try to load directly as resource
			URL url = getClass().getResource("/" + imagePath);
			if (url != null) {
				bufferedImage = ImageIO.read(url);
				if (bufferedImage != null) {
					return;
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading from resource: " + e.getMessage());
		}
		
		// Try to load from classpath root 
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(imagePath);
			if (is != null) {
				bufferedImage = ImageIO.read(is);
				if (bufferedImage != null) {
					return;
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading from classpath: " + e.getMessage());
		}
		
		// Try to load from project root (for working dir)
		try {
			File rootFile = new File(System.getProperty("user.dir"), imagePath);
			if (rootFile.exists()) {
				bufferedImage = ImageIO.read(rootFile);
				if (bufferedImage != null) {
					return;
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading from project root: " + e.getMessage());
		}
		
		// If all loading attempts failed
		System.err.println(ERROR_FILE_PREFIX + imagePath + ERROR_NOT_FOUND);
	}

    /**
     * Gets the file path of the image.
     *
     * @return The file path of the image
     */
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * Calculates the bounding box for this bitmap item.
     *
     * @param graphics The graphics context
     * @param observer The image observer
     * @param scale The scale factor to apply
     * @param style The style to use for rendering
     * @return A Rectangle representing the bounding box
     */
    @Override
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style style) {
        if (this.bufferedImage == null) {
            return new Rectangle();
        }
        
        return new Rectangle(
            (int) (style.getIndent() * scale),
            0,
            (int) (this.bufferedImage.getWidth(observer) * scale),
            (int) (style.getLeading() * scale) + (int) (this.bufferedImage.getHeight(observer) * scale)
        );
    }

    /**
     * Draws the bitmap image on the specified graphics context.
     *
     * @param x The x-coordinate for drawing
     * @param y The y-coordinate for drawing
     * @param scale The scale factor to apply
     * @param graphics The graphics context to draw on
     * @param style The style to use for rendering
     * @param observer The image observer
     */
    @Override
    public void draw(int x, int y, float scale, Graphics graphics, Style style, ImageObserver observer) {
        if (this.bufferedImage == null) {
            return;
        }
        
        int drawX = x + (int) (style.getIndent() * scale);
        int drawY = y + (int) (style.getLeading() * scale);
        int scaledWidth = (int) (this.bufferedImage.getWidth(observer) * scale);
        int scaledHeight = (int) (this.bufferedImage.getHeight(observer) * scale);
        
        graphics.drawImage(this.bufferedImage, drawX, drawY, scaledWidth, scaledHeight, observer);
    }

    /**
     * Returns a string representation of this BitmapItem.
     *
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        return String.format("BitmapItem[level=%d,path=%s]", this.getLevel(), this.imagePath);
    }
}