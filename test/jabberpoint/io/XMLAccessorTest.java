package jabberpoint.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.TextItem;

public class XMLAccessorTest {
    private XMLAccessor accessor;
    private Presentation presentation;

    @BeforeEach
    public void setUp() {
        accessor = new XMLAccessor();
        presentation = new Presentation();
    }

    @Test
    public void testSaveAndLoadPresentation(@TempDir Path tempDir) throws IOException {
        // Create a test presentation
        presentation.setTitle("Test Presentation");
        Slide slide1 = new Slide();
        slide1.setTitle("Test Slide 1");
        slide1.append(new TextItem(1, "Test Text 1"));
        presentation.append(slide1);

        Slide slide2 = new Slide();
        slide2.setTitle("Test Slide 2");
        slide2.append(new TextItem(1, "Test Text 2"));
        presentation.append(slide2);

        // Save the presentation
        String filename = tempDir.resolve("test.xml").toString();
        accessor.saveFile(presentation, filename);

        // Create a new presentation to load into
        Presentation loadedPresentation = new Presentation();
        accessor.loadFile(loadedPresentation, filename);

        // Verify the loaded presentation
        assertEquals("Test Presentation", loadedPresentation.getTitle());
        assertEquals(2, loadedPresentation.getSize());

        // Verify first slide
        assertEquals("Test Slide 1", loadedPresentation.getSlide(0).getTitle());
        assertEquals(1, loadedPresentation.getSlide(0).getSize());
        assertTrue(loadedPresentation.getSlide(0).getSlideItem(0) instanceof TextItem);
        assertEquals("Test Text 1", ((TextItem) loadedPresentation.getSlide(0).getSlideItem(0)).getText());

        // Verify second slide
        assertEquals("Test Slide 2", loadedPresentation.getSlide(1).getTitle());
        assertEquals(1, loadedPresentation.getSlide(1).getSize());
        assertTrue(loadedPresentation.getSlide(1).getSlideItem(0) instanceof TextItem);
        assertEquals("Test Text 2", ((TextItem) loadedPresentation.getSlide(1).getSlideItem(0)).getText());
    }

    @Test
    public void testLoadNonExistentFile() {
        assertThrows(IOException.class, () -> {
            accessor.loadFile(presentation, "nonexistent.xml");
        });
    }

    @Test
    public void testSaveToInvalidLocation(@TempDir Path tempDir) {
        String invalidPath = tempDir.resolve("nonexistent/test.xml").toString();
        assertThrows(IOException.class, () -> {
            accessor.saveFile(presentation, invalidPath);
        });
    }

    @Test
    public void testLoadEmptyPresentation(@TempDir Path tempDir) throws IOException {
        // Save an empty presentation
        String filename = tempDir.resolve("empty.xml").toString();
        accessor.saveFile(presentation, filename);

        // Load it back
        Presentation loadedPresentation = new Presentation();
        accessor.loadFile(loadedPresentation, filename);

        // Verify it's empty
        assertNull(loadedPresentation.getTitle());
        assertEquals(0, loadedPresentation.getSize());
    }
}