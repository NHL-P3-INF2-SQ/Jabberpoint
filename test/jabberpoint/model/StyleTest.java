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
        Style defaultStyle = new Style(0, Color.BLACK, 0,0);

        // Verify default values
        assertEquals(0, defaultStyle.getIndent());
        assertEquals(0, defaultStyle.getLeading());
        assertNotNull(defaultStyle.getColor());
    }

    @Test
    public void testNegativeValues() {
        Style negativeStyle = new Style(-5,Color.BLACK, -1, -1);
        assertEquals(-5, negativeStyle.getIndent());
        assertEquals(-1, negativeStyle.getLeading());
        assertEquals(-1, negativeStyle.getFontSize());
    }

    @Test
    public void testNullColor() {
        Style nullColorStyle = new Style(0, null, 0,0);
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
        Style colorStyle = new Style(0, customColor, 0,0);

        Color retrievedColor = colorStyle.getColor();
        assertEquals(100, retrievedColor.getRed());
        assertEquals(150, retrievedColor.getGreen());
        assertEquals(200, retrievedColor.getBlue());
    }
}