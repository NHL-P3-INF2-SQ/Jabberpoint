package jabberpoint.io.xml;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.nio.charset.StandardCharsets;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.TextItem;

public class XMLWriterTest {
    private XMLWriter writer;
    private Presentation presentation;

    private String readFile(Path path) throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, StandardCharsets.UTF_8);
    }

    @BeforeEach
    public void setUp() {
        List<SlideItemSerializer> serializers = Arrays.asList(
                new TextItemSerializer(),
                new BitmapItemSerializer());
        writer = new XMLWriter(serializers);
        presentation = new Presentation();
    }

    @Test
    public void testWriteEmptyPresentation(@TempDir Path tempDir) throws IOException {
        // Save empty presentation
        Path xmlFile = tempDir.resolve("empty.xml");
        writer.saveFile(presentation, xmlFile.toString());

        // Read the file content
        String content = readFile(xmlFile);

        // Verify minimal XML structure
        assertTrue(content.contains("<?xml version=\"1.0\""));
        assertTrue(content.contains("<presentation"));
        assertTrue(content.contains("</presentation>"));
        assertFalse(content.contains("<slide"));
    }

    @Test
    public void testWriteToInvalidLocation(@TempDir Path tempDir) {
        String invalidPath = tempDir.resolve("nonexistent/test.xml").toString();
        assertThrows(IOException.class, () -> {
            writer.saveFile(presentation, invalidPath);
        });
    }

    @Test
    public void testWriteWithNullTitle(@TempDir Path tempDir) throws IOException {
        // Create a presentation with null title
        Slide slide = new Slide();
        slide.append(new TextItem(1, "Content"));
        presentation.append(slide);

        // Save the presentation
        Path xmlFile = tempDir.resolve("nullTitle.xml");
        writer.saveFile(presentation, xmlFile.toString());

        // Read the file content
        String content = readFile(xmlFile);

        // Verify XML structure
        assertTrue(content.contains("<?xml version=\"1.0\""));
        assertTrue(content.contains("<presentation"));
        assertFalse(content.contains("title=\"null\""));
    }
}