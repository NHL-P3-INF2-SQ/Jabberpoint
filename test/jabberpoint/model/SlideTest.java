package jabberpoint.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class SlideTest {
    private Slide slide;

    @BeforeEach
    public void setUp() {
        slide = new Slide();
    }

    @Test
    public void testInitialState() {
        assertNull(slide.getTitle());
        assertEquals(0, slide.getSize());
    }

    @Test
    public void testSetTitle() {
        String title = "Test Slide";
        slide.setTitle(title);
        assertEquals(title, slide.getTitle());
    }

    @Test
    public void testAppendSlideItem() {
        // Create a text item
        TextItem textItem = new TextItem(1, "Test Text");

        // Add to slide
        slide.append(textItem);

        // Verify item was added
        assertEquals(1, slide.getSize());
        assertSame(textItem, slide.getSlideItem(0));
    }

    @Test
    public void testAppendTextDirectly() {
        // Test the convenience method for adding text
        slide.append(1, "Test Text");

        // Verify a TextItem was created and added
        assertEquals(1, slide.getSize());
        SlideItem item = slide.getSlideItem(0);
        assertTrue(item instanceof TextItem);
        assertEquals("Test Text", ((TextItem) item).getText());
        assertEquals(1, item.getLevel());
    }

    @Test
    public void testGetSlideItemWithInvalidIndex() {
        // Test with empty slide
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            slide.getSlideItem(0);
        });

        // Add an item and test beyond bounds
        slide.append(1, "Test");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            slide.getSlideItem(1);
        });
    }

    @Test
    public void testGetSize() {
        assertEquals(0, slide.getSize());

        // Add items
        slide.append(1, "First");
        slide.append(2, "Second");

        assertEquals(2, slide.getSize());
    }

    @Test
    public void testSlideConstants() {
        // Test that slide dimensions are properly defined
        assertEquals(1200, Slide.WIDTH);
        assertEquals(800, Slide.HEIGHT);
    }

    @Test
    public void testMultipleItems() {
        // Add multiple items of different types
        TextItem text1 = new TextItem(1, "Text 1");
        TextItem text2 = new TextItem(2, "Text 2");
        BitmapItem image = new BitmapItem(1, "test.jpg");

        slide.append(text1);
        slide.append(text2);
        slide.append(image);

        // Verify all items were added in order
        assertEquals(3, slide.getSize());
        assertSame(text1, slide.getSlideItem(0));
        assertSame(text2, slide.getSlideItem(1));
        assertSame(image, slide.getSlideItem(2));
    }

    @Test
    public void testItemLevels() {
        // Add items with different levels
        slide.append(0, "Title"); // Level 0
        slide.append(1, "Heading"); // Level 1
        slide.append(2, "Subheading"); // Level 2

        // Verify levels were set correctly
        assertEquals(0, slide.getSlideItem(0).getLevel());
        assertEquals(1, slide.getSlideItem(1).getLevel());
        assertEquals(2, slide.getSlideItem(2).getLevel());
    }

    @Test
    public void testDrawWithNoItems() {
        // Create a mock Graphics object
        Graphics mockGraphics = new java.awt.image.BufferedImage(
                Slide.WIDTH, Slide.HEIGHT, java.awt.image.BufferedImage.TYPE_INT_RGB).getGraphics();

        // Create a test rectangle
        Rectangle area = new Rectangle(0, 0, Slide.WIDTH, Slide.HEIGHT);

        // This should not throw any exceptions
        assertDoesNotThrow(() -> {
            slide.draw(mockGraphics, area, null);
        });
    }
}