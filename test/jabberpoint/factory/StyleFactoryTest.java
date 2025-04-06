package jabberpoint.factory;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jabberpoint.model.StyleAttributes;
import java.awt.Color;

public class StyleFactoryTest {
    private StyleFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new DefaultStyleFactory();
    }

    @Test
    public void testDefaultFactoryCreateStyle() {
        StyleAttributes style = factory.createStyle();

        assertEquals(40, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(32, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testDefaultFactoryCreateTitleStyle() {
        StyleAttributes style = factory.createTitleStyle();

        assertEquals(0, style.getIndent());
        assertEquals(Color.red, style.getColor());
        assertEquals(48, style.getFontSize());
        assertEquals(20, style.getLeading());
    }

    @Test
    public void testDefaultFactoryCreateHeadingStyle() {
        StyleAttributes style = factory.createHeadingStyle();

        assertEquals(20, style.getIndent());
        assertEquals(Color.blue, style.getColor());
        assertEquals(40, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testDefaultFactoryCreateSubheadingStyle() {
        StyleAttributes style = factory.createSubheadingStyle();

        assertEquals(50, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(36, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testDefaultFactoryCreateBodyStyle() {
        StyleAttributes style = factory.createBodyStyle();

        assertEquals(70, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(30, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testDefaultFactoryCreateDetailStyle() {
        StyleAttributes style = factory.createDetailStyle();

        assertEquals(90, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(24, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testDefaultFactoryCreateCustomStyle() {
        int indent = 25;
        Color color = Color.green;
        int fontSize = 16;
        int leading = 15;

        StyleAttributes style = factory.createCustomStyle(indent, color, fontSize, leading);

        assertEquals(indent, style.getIndent());
        assertEquals(color, style.getColor());
        assertEquals(fontSize, style.getFontSize());
        assertEquals(leading, style.getLeading());
    }

    @Test
    public void testProviderGetFactory() {
        StyleFactory providerFactory = StyleFactoryProvider.getFactory();
        assertNotNull(providerFactory);
        assertTrue(providerFactory instanceof DefaultStyleFactory);
    }

    @Test
    public void testProviderCreateTitleStyle() {
        StyleAttributes style = StyleFactoryProvider.createTitleStyle();

        assertEquals(0, style.getIndent());
        assertEquals(Color.red, style.getColor());
        assertEquals(48, style.getFontSize());
        assertEquals(20, style.getLeading());
    }

    @Test
    public void testProviderCreateHeadingStyle() {
        StyleAttributes style = StyleFactoryProvider.createHeadingStyle();

        assertEquals(20, style.getIndent());
        assertEquals(Color.blue, style.getColor());
        assertEquals(40, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testProviderCreateSubheadingStyle() {
        StyleAttributes style = StyleFactoryProvider.createSubheadingStyle();

        assertEquals(50, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(36, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testProviderCreateBodyStyle() {
        StyleAttributes style = StyleFactoryProvider.createBodyStyle();

        assertEquals(70, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(30, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testProviderCreateDetailStyle() {
        StyleAttributes style = StyleFactoryProvider.createDetailStyle();

        assertEquals(90, style.getIndent());
        assertEquals(Color.black, style.getColor());
        assertEquals(24, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testProviderCreateCustomStyle() {
        int indent = 25;
        Color color = Color.green;
        int fontSize = 16;
        int leading = 15;

        StyleAttributes style = StyleFactoryProvider.createCustomStyle(indent, color, fontSize, leading);

        assertEquals(indent, style.getIndent());
        assertEquals(color, style.getColor());
        assertEquals(fontSize, style.getFontSize());
        assertEquals(leading, style.getLeading());
    }

    @Test
    public void testFontScaling() {
        StyleAttributes style = factory.createTitleStyle();
        float scale = 1.5f;
        int expectedSize = Math.round(48 * scale); // 48 is the base size for title style
        assertEquals(expectedSize, style.getFont(scale).getSize());
    }

    @Test
    public void testStyleHierarchy() {
        // Test that indentation increases with each style level
        StyleAttributes titleStyle = factory.createTitleStyle();
        StyleAttributes headingStyle = factory.createHeadingStyle();
        StyleAttributes subheadingStyle = factory.createSubheadingStyle();
        StyleAttributes bodyStyle = factory.createBodyStyle();
        StyleAttributes detailStyle = factory.createDetailStyle();

        assertTrue(titleStyle.getIndent() < headingStyle.getIndent());
        assertTrue(headingStyle.getIndent() < subheadingStyle.getIndent());
        assertTrue(subheadingStyle.getIndent() < bodyStyle.getIndent());
        assertTrue(bodyStyle.getIndent() < detailStyle.getIndent());
    }

    @Test
    public void testFontSizeHierarchy() {
        // Test that font size decreases with each style level
        StyleAttributes titleStyle = factory.createTitleStyle();
        StyleAttributes headingStyle = factory.createHeadingStyle();
        StyleAttributes subheadingStyle = factory.createSubheadingStyle();
        StyleAttributes bodyStyle = factory.createBodyStyle();
        StyleAttributes detailStyle = factory.createDetailStyle();

        assertTrue(titleStyle.getFontSize() > headingStyle.getFontSize());
        assertTrue(headingStyle.getFontSize() > subheadingStyle.getFontSize());
        assertTrue(subheadingStyle.getFontSize() > bodyStyle.getFontSize());
        assertTrue(bodyStyle.getFontSize() > detailStyle.getFontSize());
    }
}