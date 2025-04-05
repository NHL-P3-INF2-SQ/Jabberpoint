package jabberpoint.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageObserver;

public class SlideItemTest {
    private TextItem textItem;
    private BitmapItem bitmapItem;
    private static final Style DEFAULT_STYLE = new Style(0, Color.BLACK, 0, 0);

    @BeforeEach
    public void setUp() {
        textItem = new TextItem(1, "Test Text");
        bitmapItem = new BitmapItem(1, "test.jpg");
    }

    @Test
    public void testTextItemInitialization() {
        assertEquals(1, textItem.getLevel());
        assertEquals("Test Text", textItem.getText());
    }

    @Test
    public void testBitmapItemInitialization() {
        assertEquals(1, bitmapItem.getLevel());
        assertEquals("test.jpg", bitmapItem.getImagePath());
    }

    @Test
    public void testTextItemLevel() {
        TextItem item = new TextItem(3, "Test");
        assertEquals(3, item.getLevel());
    }

    @Test
    public void testBitmapItemLevel() {
        BitmapItem item = new BitmapItem(4, "test.png");
        assertEquals(4, item.getLevel());
    }

    @Test
    public void testTextItemGetBoundingBox() {
        Rectangle area = new Rectangle(0, 0, 800, 600);
        Graphics mockGraphics = new java.awt.image.BufferedImage(
                800, 600, java.awt.image.BufferedImage.TYPE_INT_RGB).getGraphics();

        Rectangle boundingBox = textItem.getBoundingBox(mockGraphics, null, 1.0f, DEFAULT_STYLE);
        assertNotNull(boundingBox);
    }

    @Test
    public void testTextItemAttributes() {
        TextItem item = new TextItem(0, "Title");
        assertEquals(0, item.getLevel());
        assertEquals("Title", item.getText());

        item = new TextItem(1, "Content");
        assertEquals(1, item.getLevel());
        assertEquals("Content", item.getText());
    }

    @Test
    public void testBitmapItemAttributes() {
        BitmapItem item = new BitmapItem(1, "image.png");
        assertEquals(1, item.getLevel());
        assertEquals("image.png", item.getImagePath());
    }

    @Test
    public void testDrawWithNullGraphics() {
        Rectangle area = new Rectangle(0, 0, 800, 600);
        int x = 100, y = 100;

        // Should not throw exception when graphics is null
        assertDoesNotThrow(() -> {
            textItem.draw(x, y, 1.0f, null, DEFAULT_STYLE, null);
        });

        assertDoesNotThrow(() -> {
            bitmapItem.draw(x, y, 1.0f, null, DEFAULT_STYLE, null);
        });
    }

    @Test
    public void testDrawWithValidGraphics() {
        // Create a mock Graphics object
        Graphics mockGraphics = new java.awt.image.BufferedImage(
                800, 600, java.awt.image.BufferedImage.TYPE_INT_RGB).getGraphics();
        int x = 100, y = 100;

        // Should not throw exception
        assertDoesNotThrow(() -> {
            textItem.draw(x, y, 1.0f, mockGraphics, DEFAULT_STYLE, null);
        });

        assertDoesNotThrow(() -> {
            bitmapItem.draw(x, y, 1.0f, mockGraphics, DEFAULT_STYLE, null);
        });
    }

    @Test
    public void testInvalidLevel() {
        // Test with negative level
        TextItem negativeItem = new TextItem(-1, "Test");
        assertEquals(-1, negativeItem.getLevel());

        // Test with very high level
        TextItem highLevelItem = new TextItem(1000, "Test");
        assertEquals(1000, highLevelItem.getLevel());
    }

    @Test
    public void testNullText() {
        TextItem nullItem = new TextItem(1, null);
        assertNull(nullItem.getText());
    }

    @Test
    public void testEmptyText() {
        TextItem emptyItem = new TextItem(1, "");
        assertEquals("", emptyItem.getText());
    }
}