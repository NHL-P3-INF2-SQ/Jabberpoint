package jabberpoint.factory;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;
import jabberpoint.model.BitmapItem;

public class SlideItemFactoryTest {

    @Test
    public void testCreateTextItem() {
        // Test creating a text item
        SlideItem item = SlideItemFactory.createSlideItem("text", 1, "Test Text");

        // Assert it's the right type
        assertTrue(item instanceof TextItem);

        // Assert properties are set correctly
        TextItem textItem = (TextItem) item;
        assertEquals(1, textItem.getLevel());
        assertEquals("Test Text", textItem.getText());
    }

    @Test
    public void testCreateBitmapItem() {
        // Test creating a bitmap item
        String imagePath = "JabberPoint.gif";
        SlideItem item = SlideItemFactory.createSlideItem("image", 2, imagePath);

        // Assert it's the right type
        assertTrue(item instanceof BitmapItem);

        // Assert properties are set correctly
        BitmapItem bitmapItem = (BitmapItem) item;
        assertEquals(2, bitmapItem.getLevel());
    }

    @Test
    public void testCreateTextItemDirectly() {
        TextItem item = SlideItemFactory.createTextItem(3, "Text");

        assertEquals(3, item.getLevel());
        assertEquals("Text", item.getText());
    }

    @Test
    public void testCreateBitmapItemDirectly() {
        String imagePath = "JabberPoint.gif";
        BitmapItem item = SlideItemFactory.createBitmapItem(4, imagePath);

        assertEquals(4, item.getLevel());
    }

    @Test
    public void testCreateSlideItemWithNullKind() {
        assertThrows(IllegalArgumentException.class, () -> {
            SlideItemFactory.createSlideItem(null, 1, "content");
        });
    }

    @Test
    public void testCreateSlideItemWithInvalidKind() {
        assertThrows(IllegalArgumentException.class, () -> {
            SlideItemFactory.createSlideItem("invalid", 1, "content");
        });
    }

    @Test
    public void testCreateSlideItemCaseInsensitive() {
        SlideItem textItem = SlideItemFactory.createSlideItem("TEXT", 1, "Upper Case");
        SlideItem imageItem = SlideItemFactory.createSlideItem("IMAGE", 1, "test.png");

        assertTrue(textItem instanceof TextItem);
        assertTrue(imageItem instanceof BitmapItem);
    }
}