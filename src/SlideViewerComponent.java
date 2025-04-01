import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * A graphical component that displays slides in a presentation.
 * This component handles the rendering of slides and their content,
 * including the slide number indicator.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class SlideViewerComponent extends JComponent {
    
    private static final long serialVersionUID = 227L;
    
    /**
     * The current slide being displayed.
     */
    private Slide slide;
    
    /**
     * The font used for labels.
     */
    private Font labelFont;
    
    /**
     * The presentation being displayed.
     */
    private Presentation presentation;
    
    /**
     * The parent frame containing this component.
     */
    private final JFrame frame;
    
    /**
     * Display constants
     */
    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    /**
     * Creates a new SlideViewerComponent for the specified presentation.
     *
     * @param presentation The presentation to display
     * @param frame The parent frame containing this component
     */
    public SlideViewerComponent(Presentation presentation, JFrame frame) {
        this.setBackground(BGCOLOR);
        this.presentation = presentation;
        this.labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
        this.frame = frame;
    }

    /**
     * Gets the preferred size of this component.
     *
     * @return The preferred dimension for this component
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    /**
     * Updates the component with new presentation data.
     *
     * @param presentation The presentation containing the data
     * @param slide The slide to display
     */
    public void update(Presentation presentation, Slide slide) {
        if (slide == null) {
            repaint();
            return;
        }
        this.presentation = presentation;
        this.slide = slide;
        repaint();
        this.frame.setTitle(presentation.getTitle());
    }

    /**
     * Paints the component, including the slide content and slide number indicator.
     *
     * @param graphics The graphics context to paint on
     */
    @Override
    public void paintComponent(Graphics graphics) {
        // Fill background
        graphics.setColor(BGCOLOR);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        
        if (this.presentation.getSlideNumber() < 0 || this.slide == null) {
            return;
        }
        
        // Draw slide number indicator
        graphics.setFont(this.labelFont);
        graphics.setColor(COLOR);
        graphics.drawString(
            String.format("Slide %d of %d",
                this.presentation.getSlideNumber() + 1,
                this.presentation.getSize()),
            XPOS,
            YPOS
        );
        
        // Draw slide content
        Rectangle area = new Rectangle(0, YPOS, getWidth(), getHeight() - YPOS);
        this.slide.draw(graphics, area, this);
    }
}
