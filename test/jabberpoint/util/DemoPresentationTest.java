package jabberpoint.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.TextItem;
import jabberpoint.model.BitmapItem;

public class DemoPresentationTest {
    private DemoPresentation demoPresentation;
    private Presentation presentation;

    @BeforeEach
    public void setUp() {
        demoPresentation = new DemoPresentation();
        presentation = new Presentation();
    }

    @Test
    public void testLoadFile() {
        demoPresentation.loadFile(presentation, null);

        // Test presentation title
        assertEquals("Demo Presentation", presentation.getTitle());

        // Test number of slides
        assertEquals(3, presentation.getSize());

        // Test first slide
        Slide firstSlide = presentation.getSlide(0);
        assertEquals("JabberPoint", firstSlide.getTitle());
        assertEquals(4, firstSlide.getSize());
        assertTrue(firstSlide.getSlideItem(0) instanceof TextItem);
        assertEquals("The Java presentation tool", ((TextItem) firstSlide.getSlideItem(0)).getText());
        assertEquals(1, firstSlide.getSlideItem(0).getLevel());

        // Test second slide
        Slide secondSlide = presentation.getSlide(1);
        assertEquals("What is JabberPoint?", secondSlide.getTitle());
        assertEquals(5, secondSlide.getSize());
        assertTrue(secondSlide.getSlideItem(0) instanceof TextItem);
        assertEquals("JabberPoint is a primitive slide-show program in Java",
                ((TextItem) secondSlide.getSlideItem(0)).getText());

        // Test third slide (with image)
        Slide thirdSlide = presentation.getSlide(2);
        assertEquals("JabberPoint® features", thirdSlide.getTitle());
        assertEquals(7, thirdSlide.getSize());
        assertTrue(thirdSlide.getSlideItem(6) instanceof BitmapItem);
        assertEquals("JabberPoint.jpg", ((BitmapItem) thirdSlide.getSlideItem(6)).getImagePath());
    }

    @Test
    public void testLoadFileIgnoresFilename() {
        // The demo presentation should ignore the filename parameter
        demoPresentation.loadFile(presentation, "some_file.xml");
        assertEquals("Demo Presentation", presentation.getTitle());
        assertEquals(3, presentation.getSize());
    }

    @Test
    public void testSaveFileThrowsException() {
        // Saving should not be supported for demo presentation
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            demoPresentation.saveFile(presentation, "test.xml");
        });
        assertEquals("Cannot save demo presentation", exception.getMessage());
    }

    @Test
    public void testTextItemLevels() {
        demoPresentation.loadFile(presentation, null);

        // Test various text levels in first slide
        Slide firstSlide = presentation.getSlide(0);
        assertEquals(1, firstSlide.getSlideItem(0).getLevel()); // Level 1
        assertEquals(2, firstSlide.getSlideItem(1).getLevel()); // Level 2
        assertEquals(4, firstSlide.getSlideItem(3).getLevel()); // Level 4
    }

    @Test
    public void testLoadIntoExistingPresentation() {
        // First load some initial content
        presentation.setTitle("Initial Title");
        Slide initialSlide = new Slide();
        initialSlide.setTitle("Initial Slide");
        presentation.append(initialSlide);

        // Now load the demo presentation
        demoPresentation.loadFile(presentation, null);

        // Verify the content was completely replaced
        assertEquals("Demo Presentation", presentation.getTitle());
        assertEquals(3, presentation.getSize());
        assertNotEquals("Initial Slide", presentation.getSlide(0).getTitle());
    }

    @Test
    public void testBitmapItemProperties() {
        demoPresentation.loadFile(presentation, null);

        // Get the bitmap item from the third slide
        Slide thirdSlide = presentation.getSlide(2);
        BitmapItem bitmapItem = (BitmapItem) thirdSlide.getSlideItem(6);

        // Test bitmap properties
        assertEquals(1, bitmapItem.getLevel());
        assertEquals("JabberPoint.jpg", bitmapItem.getImagePath());
    }
}