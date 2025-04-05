package jabberpoint.ui.renderer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import javax.swing.JFrame;
import jabberpoint.model.Slide;
import jabberpoint.model.Presentation;
import jabberpoint.model.TextItem;

public class SwingPresentationRendererTest {

    private SwingPresentationRenderer renderer;
    private JFrame frame;
    private static final int TEST_WIDTH = 800;
    private static final int TEST_HEIGHT = 600;

    // Mock Graphics class for testing rendering operations
    private static class MockGraphics extends Graphics {
        private Color currentColor;
        private Font currentFont;
        private String lastDrawnString;
        private int lastX, lastY;
        private boolean fillRectCalled = false;
        private Rectangle lastFillRect;

        @Override
        public Graphics create() {
            return this;
        }

        @Override
        public void setColor(Color c) {
            currentColor = c;
        }

        @Override
        public Color getColor() {
            return currentColor;
        }

        @Override
        public void setFont(Font font) {
            currentFont = font;
        }

        @Override
        public Font getFont() {
            return currentFont;
        }

        @Override
        public void drawString(String str, int x, int y) {
            lastDrawnString = str;
            lastX = x;
            lastY = y;
        }

        @Override
        public void drawString(AttributedCharacterIterator iterator, int x, int y) {

        }

        @Override
        public void fillRect(int x, int y, int width, int height) {
            fillRectCalled = true;
            lastFillRect = new Rectangle(x, y, width, height);
        }

        public String getLastDrawnString() {
            return lastDrawnString;
        }

        public int getLastX() {
            return lastX;
        }

        public int getLastY() {
            return lastY;
        }

        public boolean wasFillRectCalled() {
            return fillRectCalled;
        }

        public Rectangle getLastFillRect() {
            return lastFillRect;
        }

        // Implement other abstract methods with empty bodies
        @Override
        public void dispose() {
        }

        @Override
        public void setPaintMode() {
        }

        @Override
        public void setXORMode(Color c1) {
        }

        @Override
        public void setClip(int x, int y, int width, int height) {
        }

        @Override
        public void setClip(java.awt.Shape clip) {
        }

        @Override
        public java.awt.FontMetrics getFontMetrics(Font f) {
            return null;
        }

        @Override
        public java.awt.Rectangle getClipBounds() {
            return null;
        }

        @Override
        public void clipRect(int x, int y, int width, int height) {

        }

        @Override
        public java.awt.Shape getClip() {
            return null;
        }

        @Override
        public void drawRect(int x, int y, int width, int height) {
        }

        @Override
        public void clearRect(int x, int y, int width, int height) {
        }

        @Override
        public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

        }

        @Override
        public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

        }

        @Override
        public void drawOval(int x, int y, int width, int height) {
        }

        @Override
        public void fillOval(int x, int y, int width, int height) {
        }

        @Override
        public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        }

        @Override
        public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        }

        @Override
        public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        }

        @Override
        public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        }

        @Override
        public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        }

        @Override
        public void drawLine(int x1, int y1, int x2, int y2) {
        }

        @Override
        public boolean drawImage(java.awt.Image img, int x, int y, java.awt.image.ImageObserver observer) {
            return false;
        }

        @Override
        public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
            return false;
        }

        @Override
        public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
            return false;
        }

        @Override
        public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
            return false;
        }

        @Override
        public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
            return false;
        }

        @Override
        public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
            return false;
        }

        @Override
        public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        }

        @Override
        public void translate(int x, int y) {
        }
    }

    @BeforeEach
    public void setUp() {
        frame = new JFrame();
        renderer = new SwingPresentationRenderer(frame);
    }

    @Test
    public void testConstructorWithNullFrame() {
        // Should not throw exception
        assertDoesNotThrow(() -> {
            new SwingPresentationRenderer(null);
        });
    }

    @Test
    public void testRenderSlideWithNullGraphics() {
        Slide slide = new Slide();
        Rectangle area = new Rectangle(0, 0, TEST_WIDTH, TEST_HEIGHT);

        // Should not throw exception
        assertThrows(NullPointerException.class, () -> {
            renderer.renderSlide(null, slide, area, 0, 1);
        });
    }

    @Test
    public void testRenderSlideWithNullSlide() {
        MockGraphics graphics = new MockGraphics();
        Rectangle area = new Rectangle(0, 0, TEST_WIDTH, TEST_HEIGHT);

        renderer.renderSlide(graphics, null, area, 0, 1);

        // Background should still be filled
        assertTrue(graphics.wasFillRectCalled(),
                "Background should be filled even with null slide");
    }

    @Test
    public void testRenderSlideWithInvalidSlideNumber() {
        MockGraphics graphics = new MockGraphics();
        Rectangle area = new Rectangle(0, 0, TEST_WIDTH, TEST_HEIGHT);
        Slide slide = new Slide();

        renderer.renderSlide(graphics, slide, area, -1, 1);

        // Should only fill background, not render slide number
        assertTrue(graphics.wasFillRectCalled(), "Background should be filled");
        assertNull(graphics.getLastDrawnString(),
                "Nothing should be drawn for invalid slide number");
    }

    @Test
    public void testUpdateTitleWithValidPresentation() {
        Presentation presentation = new Presentation();
        String testTitle = "Test Title";
        presentation.setTitle(testTitle);

        renderer.updateTitle(presentation);

        assertEquals(testTitle, frame.getTitle(),
                "Frame title should be updated to match presentation title");
    }

    @Test
    public void testUpdateTitleWithNullPresentation() {
        String originalTitle = "Original Title";
        frame.setTitle(originalTitle);

        renderer.updateTitle(null);

        assertEquals(originalTitle, frame.getTitle(),
                "Frame title should not change with null presentation");
    }
}