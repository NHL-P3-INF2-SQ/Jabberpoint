package jabberpoint.ui.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JFrame;
import jabberpoint.model.Slide;
import jabberpoint.model.Presentation;

/**
 * Swing-based implementation of the PresentationRenderer interface.
 * This class handles rendering presentations using Java Swing components.
 *
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 */
public class SwingPresentationRenderer implements PresentationRenderer {
    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    private final Font labelFont;
    private final JFrame frame;

    /**
     * Creates a new SwingPresentationRenderer.
     *
     * @param frame The JFrame to render in
     */
    public SwingPresentationRenderer(JFrame frame) {
        this.frame = frame;
        this.labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
    }

    @Override
    public void renderSlide(Graphics graphics, Slide slide, Rectangle area, 
            int slideNumber, int totalSlides) {
        // Fill background
        graphics.setColor(BGCOLOR);
        graphics.fillRect(0, 0, area.width, area.height);
        
        if (slideNumber < 0 || slide == null) {
            return;
        }
        
        // Draw slide number indicator
        graphics.setFont(this.labelFont);
        graphics.setColor(COLOR);
        graphics.drawString(
            String.format("Slide %d of %d", slideNumber + 1, totalSlides),
            XPOS,
            YPOS
        );
        
        // Draw slide content
        Rectangle contentArea = new Rectangle(0, YPOS, area.width, area.height - YPOS);
        slide.draw(graphics, contentArea, null);
    }

    @Override
    public void updateTitle(Presentation presentation) {
        if (this.frame != null && presentation != null) {
            this.frame.setTitle(presentation.getTitle());
        }
    }
}