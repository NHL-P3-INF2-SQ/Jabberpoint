package jabberpoint.factory;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;
import jabberpoint.model.BitmapItem;

public class SlideItemFactoryTest {

    @Test
    public void testCreateTextItem() {
        // Test creating a text item using the provider
        SlideItem item = SlideItemFactoryProvider.createSlideItem("text", 1, "Test Text");

        // Assert it's the right type
        assertTrue(item instanceof TextItem);

        // Assert properties are set correctly
        TextItem textItem = (TextItem) item;
        assertEquals(1, textItem.getLevel());
        assertEquals("Test Text", textItem.getText());
    }

    @Test
    public void testCreateBitmapItem() {
        // Test creating a bitmap item using the provider
        String imagePath = "JabberPoint.gif";
        SlideItem item = SlideItemFactoryProvider.createSlideItem("image", 2, imagePath);

        // Assert it's the right type
        assertTrue(item instanceof BitmapItem);

        // Assert properties are set correctly
        BitmapItem bitmapItem = (BitmapItem) item;
        assertEquals(2, bitmapItem.getLevel());
    }

    @Test
    public void testCreateTextItemWithFactory() {
        SlideItemFactory factory = SlideItemFactoryProvider.getFactory("text");
        SlideItem item = factory.createSlideItem(3, "Text");

        assertTrue(item instanceof TextItem);
        assertEquals(3, item.getLevel());
        assertEquals("Text", ((TextItem) item).getText());
    }

    @Test
    public void testCreateBitmapItemWithFactory() {
        String imagePath = "JabberPoint.gif";
        SlideItemFactory factory = SlideItemFactoryProvider.getFactory("image");
        SlideItem item = factory.createSlideItem(4, imagePath);

        assertTrue(item instanceof BitmapItem);
        assertEquals(4, item.getLevel());
        assertEquals(imagePath, ((BitmapItem) item).getImagePath());
    }

    @Test
    public void testCreateSlideItemWithNullKind() {
        assertThrows(IllegalArgumentException.class, () -> {
            SlideItemFactoryProvider.createSlideItem(null, 1, "content");
        });
    }

    @Test
    public void testCreateSlideItemWithInvalidKind() {
        assertThrows(IllegalArgumentException.class, () -> {
            SlideItemFactoryProvider.createSlideItem("invalid", 1, "content");
        });
    }

    @Test
    public void testGetFactoryWithNullKind() {
        assertThrows(IllegalArgumentException.class, () -> {
            SlideItemFactoryProvider.getFactory(null);
        });
    }

    @Test
    public void testGetFactoryWithInvalidKind() {
        assertThrows(IllegalArgumentException.class, () -> {
            SlideItemFactoryProvider.getFactory("invalid");
        });
    }

    @Test
    public void testCreateSlideItemCaseInsensitive() {
        SlideItem textItem = SlideItemFactoryProvider.createSlideItem("TEXT", 1, "Upper Case");
        SlideItem imageItem = SlideItemFactoryProvider.createSlideItem("IMAGE", 1, "test.png");

        assertTrue(textItem instanceof TextItem);
        assertTrue(imageItem instanceof BitmapItem);
    }

    @Test
    public void testNullContent() {
        assertThrows(IllegalArgumentException.class, () -> {
            SlideItemFactory factory = SlideItemFactoryProvider.getFactory("text");
            factory.createSlideItem(1, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            SlideItemFactory factory = SlideItemFactoryProvider.getFactory("image");
            factory.createSlideItem(1, null);
        });
    }
}