package jabberpoint.factory;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import jabberpoint.model.StyleAttributes;
import java.awt.Color;

public class StyleFactoryTest {

    @Test
    public void testCreateTitleStyle() {
        StyleAttributes style = StyleFactory.createTitleStyle();

        // Assert title style properties
        assertEquals(0, style.getIndent());
        assertEquals(Color.red, style.getColor());
        assertEquals(20, style.getLeading());

        // Test font size with default scale
        assertEquals(48, style.getFont(1.0f).getSize());
    }

    @Test
    public void testCreateHeadingStyle() {
        StyleAttributes style = StyleFactory.createHeadingStyle();

        // Assert heading style properties
        assertEquals(20, style.getIndent());
        assertEquals(Color.blue, style.getColor());
        assertEquals(10, style.getLeading());

        // Test font size with default scale
        assertEquals(40, style.getFont(1.0f).getSize());
    }

    @Test
    public void testCreateSubheadingStyle() {
        StyleAttributes style = StyleFactory.createSubheadingStyle();

        // Assert subheading style properties
        assertEquals(50, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(10, style.getLeading());

        // Test font size with default scale
        assertEquals(36, style.getFont(1.0f).getSize());
    }

    @Test
    public void testCreateBodyStyle() {
        StyleAttributes style = StyleFactory.createBodyStyle();

        // Assert body style properties
        assertEquals(70, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(10, style.getLeading());

        // Test font size with default scale
        assertEquals(30, style.getFont(1.0f).getSize());
    }

    @Test
    public void testCreateDetailStyle() {
        StyleAttributes style = StyleFactory.createDetailStyle();

        // Assert detail style properties
        assertEquals(90, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(10, style.getLeading());

        // Test font size with default scale
        assertEquals(24, style.getFont(1.0f).getSize());
    }

    @Test
    public void testCreateCustomStyle() {
        // Create a custom style with specific properties
        int indent = 25;
        Color color = Color.green;
        int fontSize = 16;
        int leading = 15;

        StyleAttributes style = StyleFactory.createCustomStyle(indent, color, fontSize, leading);

        // Assert custom style properties
        assertEquals(indent, style.getIndent());
        assertEquals(color, style.getColor());
        assertEquals(leading, style.getLeading());
        assertEquals(fontSize, style.getFont(1.0f).getSize());
    }

    @Test
    public void testFontScaling() {
        StyleAttributes style = StyleFactory.createTitleStyle();

        // Test font scaling
        float scale = 1.5f;
        int expectedSize = Math.round(48 * scale); // 48 is the base size for title style
        assertEquals(expectedSize, style.getFont(scale).getSize());
    }
}