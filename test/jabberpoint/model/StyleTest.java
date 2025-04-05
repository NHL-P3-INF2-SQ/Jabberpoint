package jabberpoint.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.awt.Font;

public class StyleTest {
    private Style style;
    private static final int TEST_INDENT = 10;
    private static final int TEST_POINTS = 20;
    private static final Color TEST_COLOR = Color.RED;
    private static final int TEST_LEADING = 10;

    @BeforeEach
    public void setUp() {
        style = new Style(TEST_INDENT, TEST_COLOR, TEST_POINTS, TEST_LEADING);
        Style.createStyles();
    }

    @Test
    public void testStyleInitialization() {
        assertEquals(TEST_INDENT, style.getIndent());
        assertEquals(TEST_POINTS, style.getFontSize());
        assertEquals(TEST_COLOR, style.getColor());
        assertEquals(TEST_LEADING, style.getLeading());
    }

    @Test
    public void testDefaultStyle() {
        Style defaultStyle = new Style(0, Color.BLACK, 0, 0);

        // Verify default values
        assertEquals(0, defaultStyle.getIndent());
        assertEquals(0, defaultStyle.getLeading());
        assertNotNull(defaultStyle.getColor());
    }

    @Test
    public void testNegativeValues() {
        Style negativeStyle = new Style(-5, Color.BLACK, -1, -1);
        assertEquals(-5, negativeStyle.getIndent());
        assertEquals(-1, negativeStyle.getLeading());
        assertEquals(-1, negativeStyle.getFontSize());
    }

    @Test
    public void testNullColor() {
        Style nullColorStyle = new Style(0, null, 0, 0);
        assertNull(nullColorStyle.getColor());
    }

    @Test
    public void testLargeValues() {
        int largeIndent = Integer.MAX_VALUE;
        int largeLeading = Integer.MAX_VALUE;
        Style largeStyle = new Style(largeIndent, TEST_COLOR, 0, largeLeading);
        assertEquals(largeIndent, largeStyle.getIndent());
        assertEquals(largeLeading, largeStyle.getLeading());
    }

    @Test
    public void testColorComponents() {
        Color customColor = new Color(100, 150, 200);
        Style colorStyle = new Style(0, customColor, 0, 0);

        Color retrievedColor = colorStyle.getColor();
        assertEquals(100, retrievedColor.getRed());
        assertEquals(150, retrievedColor.getGreen());
        assertEquals(200, retrievedColor.getBlue());
    }

    @Test
    public void testCreateStyles() {
        // Test that styles are created for all levels
        for (int i = 0; i < 5; i++) {
            StyleAttributes style = Style.getStyle(i);
            assertNotNull(style, "Style should not be null for level " + i);
        }
    }

    @Test
    public void testGetStyleLevelBoundaries() {
        // Test getting style for level beyond array bounds
        StyleAttributes style = Style.getStyle(10);
        assertNotNull(style, "Style should not be null for out-of-bounds level");

        // Should return the last style (level 4)
        assertEquals(Style.getStyle(4).getIndent(), style.getIndent(),
                "Out-of-bounds level should return last style's indent");
        assertEquals(Style.getStyle(4).getColor(), style.getColor(),
                "Out-of-bounds level should return last style's color");
    }

    @Test
    public void testStyleHierarchy() {
        // Test that indentation increases with level
        int previousIndent = Style.getStyle(0).getIndent();
        for (int i = 1; i < 5; i++) {
            int currentIndent = Style.getStyle(i).getIndent();
            assertTrue(currentIndent > previousIndent,
                    "Indentation should increase with level");
            previousIndent = currentIndent;
        }
    }

    @Test
    public void testFontScaling() {
        StyleAttributes style = Style.getStyle(0);
        float scale = 2.0f;
        Font scaledFont = style.getFont(scale);

        assertEquals(Math.round(style.getFontSize() * scale),
                scaledFont.getSize(),
                "Font size should be scaled correctly");
    }

    @Test
    public void testStyleConstructor() {
        int indent = 20;
        Color color = Color.BLUE;
        int points = 24;
        int leading = 10;

        Style style = new Style(indent, color, points, leading);

        assertEquals(indent, style.getIndent(), "Indent should match constructor parameter");
        assertEquals(color, style.getColor(), "Color should match constructor parameter");
        assertEquals(points, style.getFontSize(), "Font size should match constructor parameter");
        assertEquals(leading, style.getLeading(), "Leading should match constructor parameter");
    }

    @Test
    public void testDefaultStyles() {
        // Test title style (level 0)
        StyleAttributes titleStyle = Style.getStyle(0);
        assertTrue(titleStyle.getFontSize() > Style.getStyle(1).getFontSize(),
                "Title style should have largest font size");

        // Test heading style (level 1)
        StyleAttributes headingStyle = Style.getStyle(1);
        assertTrue(headingStyle.getFontSize() > Style.getStyle(2).getFontSize(),
                "Heading style should have larger font size than subheading");

        // Test body style (level 3)
        StyleAttributes bodyStyle = Style.getStyle(3);
        assertEquals(bodyStyle.getFontSize(), Style.getStyle(3).getFontSize(),
                "Body and detail text should have same font size");
    }

    @Test
    public void testFontAttributes() {
        StyleAttributes style = Style.getStyle(0);
        Font font = style.getFont(1.0f);

        assertNotNull(font, "Font should not be null");
        assertTrue(font.getFamily().length() > 0, "Font should have a family name");
        assertEquals(style.getFontSize(), font.getSize(),
                "Font size should match when scale is 1.0");
    }
}