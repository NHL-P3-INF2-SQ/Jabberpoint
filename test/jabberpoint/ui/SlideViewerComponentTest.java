package jabberpoint.ui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import javax.swing.JFrame;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.ui.renderer.PresentationRenderer;

public class SlideViewerComponentTest {

    private SlideViewerComponent viewer;
    private Presentation presentation;
    private JFrame frame;
    private static final int TEST_WIDTH = 800;
    private static final int TEST_HEIGHT = 600;

    // Mock Graphics class for testing paint operations
    private static class MockGraphics extends Graphics {
        private boolean drawCalled = false;
        private int lastX, lastY;
        private String lastString;

        public boolean wasDrawCalled() {
            return drawCalled;
        }

        public int getLastX() {
            return lastX;
        }

        public int getLastY() {
            return lastY;
        }

        public String getLastString() {
            return lastString;
        }

        @Override
        public Graphics create() {
            return this;
        }

        @Override
        public void drawString(String str, int x, int y) {
            drawCalled = true;
            lastString = str;
            lastX = x;
            lastY = y;
        }

        @Override
        public void drawString(AttributedCharacterIterator iterator, int x, int y) {

        }

        // Implement other abstract methods with empty bodies
        @Override
        public void translate(int x, int y) {
        }

        @Override
        public void dispose() {
        }

        @Override
        public void setPaintMode() {
        }

        @Override
        public void setXORMode(java.awt.Color c1) {
        }

        @Override
        public void setFont(java.awt.Font font) {
        }

        @Override
        public void setColor(java.awt.Color color) {
        }

        @Override
        public void setClip(int x, int y, int width, int height) {
        }

        @Override
        public void setClip(java.awt.Shape clip) {
        }

        @Override
        public java.awt.FontMetrics getFontMetrics(java.awt.Font f) {
            return null;
        }

        @Override
        public java.awt.Font getFont() {
            return null;
        }

        @Override
        public java.awt.Color getColor() {
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
        public void fillRect(int x, int y, int width, int height) {
        }

        @Override
        public void drawRect(int x, int y, int width, int height) {
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
        public void clearRect(int x, int y, int width, int height) {
        }

        @Override
        public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

        }

        @Override
        public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

        }
    }

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        frame = new JFrame();
        viewer = new SlideViewerComponent(presentation, frame);
        viewer.setSize(TEST_WIDTH, TEST_HEIGHT);
    }

    @Test
    public void testConstructorNullPresentation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SlideViewerComponent(null, frame);
        }, "Constructor should throw exception for null presentation");
    }

    @Test
    public void testConstructorNullFrame() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SlideViewerComponent(presentation, null);
        }, "Constructor should throw exception for null frame");
    }

    @Test
    public void testGetPreferredSize() {
        Dimension size = viewer.getPreferredSize();
        assertEquals(Slide.WIDTH, size.width, "Preferred width should match slide width");
        assertEquals(Slide.HEIGHT, size.height, "Preferred height should match slide height");
    }

    @Test
    public void testOnPresentationUpdateWithNullPresentation() {
        // Should not throw exception but should handle error
        assertDoesNotThrow(() -> {
            viewer.onPresentationUpdate(null, new Slide());
        });
    }

    @Test
    public void testPaintComponentWithNullGraphics() {
        // Should not throw exception
        assertDoesNotThrow(() -> {
            viewer.paintComponent(null);
        });
    }

    @Test
    public void testPaintComponentWithNullSlide() {
        MockGraphics mockGraphics = new MockGraphics();

        // Should not throw exception when slide is null
        assertDoesNotThrow(() -> {
            viewer.paintComponent(mockGraphics);
        });

        // Should not attempt to draw when slide is null
        assertFalse(mockGraphics.wasDrawCalled(), "Should not draw when slide is null");
    }

    @Test
    public void testComponentSize() {
        assertEquals(TEST_WIDTH, viewer.getWidth(), "Component width should match set width");
        assertEquals(TEST_HEIGHT, viewer.getHeight(), "Component height should match set height");
    }
}