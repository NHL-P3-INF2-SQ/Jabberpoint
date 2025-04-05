package jabberpoint.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Vector;

public class SlideTest {
    private Slide slide;
    private static final String TEST_TITLE = "Test Slide";
    private static final String TEST_TEXT = "Test Text";
    private static final int TEST_LEVEL = 2;

    // Mock SlideItem for testing
    private static class MockSlideItem extends SlideItem {
        private final Rectangle boundingBox;
        private boolean drawCalled = false;
        private int lastDrawX;
        private int lastDrawY;
        private float lastScale;

        public MockSlideItem(int level) {
            super(level);
            this.boundingBox = new Rectangle(0, 0, 100, 50);
        }

        @Override
        public Rectangle getBoundingBox(Graphics graphics,
                ImageObserver observer, float scale, StyleAttributes style) {
            return new Rectangle(
                    (int) (boundingBox.x * scale),
                    (int) (boundingBox.y * scale),
                    (int) (boundingBox.width * scale),
                    (int) (boundingBox.height * scale));
        }

        @Override
        public void draw(int x, int y, float scale,
                Graphics graphics, StyleAttributes style, ImageObserver observer) {
            drawCalled = true;
            lastDrawX = x;
            lastDrawY = y;
            lastScale = scale;
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }

        public int getLastDrawX() {
            return lastDrawX;
        }

        public int getLastDrawY() {
            return lastDrawY;
        }

        public float getLastScale() {
            return lastScale;
        }
    }

    @BeforeEach
    public void setUp() {
        slide = new Slide();
        Style.createStyles(); // Ensure styles are initialized
    }

    @Test
    public void testInitialState() {
        assertEquals(0, slide.getSize(), "New slide should be empty");
        assertNull(slide.getTitle(), "New slide should have no title");
    }

    @Test
    public void testSetAndGetTitle() {
        slide.setTitle(TEST_TITLE);
        assertEquals(TEST_TITLE, slide.getTitle(), "Title should match set value");
    }

    @Test
    public void testAppendSlideItem() {
        MockSlideItem item = new MockSlideItem(TEST_LEVEL);
        slide.append(item);

        assertEquals(1, slide.getSize(), "Slide should have one item");
        assertSame(item, slide.getSlideItem(0), "Retrieved item should be the same as appended");
    }

    @Test
    public void testAppendTextItem() {
        slide.append(TEST_LEVEL, TEST_TEXT);

        assertEquals(1, slide.getSize(), "Slide should have one item");
        SlideItem item = slide.getSlideItem(0);
        assertTrue(item instanceof TextItem, "Appended item should be TextItem");
        assertEquals(TEST_LEVEL, item.getLevel(), "Item level should match");
        assertEquals(TEST_TEXT, ((TextItem) item).getText(), "Item text should match");
    }

    @Test
    public void testGetSlideItems() {
        MockSlideItem item1 = new MockSlideItem(1);
        MockSlideItem item2 = new MockSlideItem(2);

        slide.append(item1);
        slide.append(item2);

        Vector<SlideItem> items = slide.getSlideItems();
        assertEquals(2, items.size(), "Should return all items");
        assertSame(item1, items.elementAt(0), "First item should match");
        assertSame(item2, items.elementAt(1), "Second item should match");
    }

    @Test
    public void testGetSlideItemOutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            slide.getSlideItem(0);
        }, "Getting item from empty slide should throw exception");
    }
}