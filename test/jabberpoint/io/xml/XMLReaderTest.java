package jabberpoint.io.xml;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.TextItem;
import jabberpoint.model.BitmapItem;

public class XMLReaderTest {
    private XMLReader reader;
    private Presentation presentation;

    private void writeFile(Path path, String content) throws IOException {
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }

    @BeforeEach
    public void setUp() {
        reader = new XMLReader(new DefaultXMLParser(), Arrays.asList(
                new TextItemSerializer(),
                new BitmapItemSerializer()));
        presentation = new Presentation();
    }

    @Test
    public void testLoadValidXMLFile() throws IOException {
        // Load the test.xml file from project root
        reader.loadFile(presentation, "test.xml");

        // Verify the loaded content
        assertEquals("XML-Based Presentation for Jabberpoint", presentation.getTitle());
        assertEquals(5, presentation.getSize());

        // Test first slide
        Slide firstSlide = presentation.getSlide(0);
        assertEquals("JabberPoint XML-Demo", firstSlide.getTitle());
        assertEquals(9, firstSlide.getSize());
        assertTrue(firstSlide.getSlideItem(0) instanceof TextItem);
        assertEquals("Welkom bij Jabberpoint", ((TextItem) firstSlide.getSlideItem(0)).getText());
        assertEquals(1, ((TextItem) firstSlide.getSlideItem(0)).getLevel());

        // Test second slide
        Slide secondSlide = presentation.getSlide(1);
        assertEquals("Een bijna lege slide", secondSlide.getTitle());
        assertEquals(1, secondSlide.getSize());
        assertTrue(secondSlide.getSlideItem(0) instanceof TextItem);

        // Test third slide (with images)
        Slide thirdSlide = presentation.getSlide(2);
        assertEquals("Achtergrond", thirdSlide.getTitle());
        assertEquals(9, thirdSlide.getSize());
        assertTrue(thirdSlide.getSlideItem(3) instanceof BitmapItem,
                "Item at index 3 should be a BitmapItem but was "
                        + thirdSlide.getSlideItem(3).getClass().getSimpleName());
        assertEquals("logo-woordmerk_ou.gif", ((BitmapItem) thirdSlide.getSlideItem(3)).getImagePath());

        // Test fourth slide (levels)
        Slide fourthSlide = presentation.getSlide(3);
        assertEquals("JabberPoint: Levels en stijlen", fourthSlide.getTitle());
        assertEquals(6, fourthSlide.getSize());
        assertTrue(fourthSlide.getSlideItem(5) instanceof TextItem);
        assertEquals(4, ((TextItem) fourthSlide.getSlideItem(5)).getLevel());

        // Test last slide
        Slide lastSlide = presentation.getSlide(4);
        assertEquals("Laatste slide", lastSlide.getTitle());
        assertEquals(6, lastSlide.getSize());
        assertTrue(lastSlide.getSlideItem(4) instanceof BitmapItem,
                "Item at index 4 should be a BitmapItem but was "
                        + lastSlide.getSlideItem(4).getClass().getSimpleName());
        assertEquals("JabberPoint.jpg", ((BitmapItem) lastSlide.getSlideItem(4)).getImagePath());
    }

    @Test
    public void testLoadInvalidXMLFile(@TempDir Path tempDir) throws IOException {
        String invalidXml = "This is not XML content";
        Path xmlFile = tempDir.resolve("invalid.xml");
        writeFile(xmlFile, invalidXml);

        assertThrows(IOException.class, () -> {
            reader.loadFile(presentation, xmlFile.toString());
        });
    }

    @Test
    public void testLoadEmptyXMLFile(@TempDir Path tempDir) throws IOException {
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<presentation>\n" +
                "</presentation>";

        Path xmlFile = tempDir.resolve("empty.xml");
        writeFile(xmlFile, xmlContent);

        reader.loadFile(presentation, xmlFile.toString());

        assertNull(presentation.getTitle());
        assertEquals(0, presentation.getSize());
    }

    @Test
    public void testLoadNonExistentFile() {
        assertThrows(IOException.class, () -> {
            reader.loadFile(presentation, "nonexistent.xml");
        });
    }
}