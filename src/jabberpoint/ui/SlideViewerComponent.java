package jabberpoint.ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import jabberpoint.model.Slide;
import jabberpoint.model.Presentation;
import jabberpoint.ui.renderer.PresentationRenderer;
import jabberpoint.ui.renderer.SwingPresentationRenderer;
import jabberpoint.observer.PresentationObserver;
import jabberpoint.util.ErrorHandler;

/**
 * A graphical component that displays slides in a presentation.
 * This component uses the Bridge pattern to delegate rendering to a PresentationRenderer,
 * allowing for different rendering implementations.
 * Implements the Observer pattern to receive updates from the presentation model.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class SlideViewerComponent extends JComponent implements PresentationObserver {
    
    private static final long serialVersionUID = 227L;
    
    /**
     * The current slide being displayed.
     */
    private Slide slide;
    
    /**
     * The font used for labels.
     */
    private final Font labelFont;
    
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
     * The renderer responsible for drawing the presentation.
     */
    private final PresentationRenderer renderer;

    /**
     * Creates a new SlideViewerComponent for the specified presentation.
     *
     * @param presentation The presentation to display
     * @param frame The parent frame containing this component
     * @throws IllegalArgumentException if presentation or frame is null
     */
    public SlideViewerComponent(Presentation presentation, JFrame frame) {
        if (presentation == null || frame == null) {
            throw new IllegalArgumentException("Presentation and frame must not be null");
        }
        
        this.setBackground(BGCOLOR);
        this.presentation = presentation;
        this.labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
        this.frame = frame;
        this.renderer = new SwingPresentationRenderer(frame);
        this.presentation.addObserver(this);
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
     * Called when the presentation state changes.
     * Implements the PresentationObserver interface.
     *
     * @param presentation The presentation that changed
     * @param currentSlide The current slide being displayed
     */
    @Override
    public void onPresentationUpdate(Presentation presentation, Slide currentSlide) {
        if (presentation == null) {
            ErrorHandler.handleValidationError("Presentation update received with null presentation", this);
            return;
        }
        this.presentation = presentation;
        this.slide = currentSlide;
        this.renderer.updateTitle(presentation);
        repaint();
    }

    /**
     * Paints the component using the configured renderer.
     *
     * @param graphics The graphics context to paint on
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        
        if (graphics == null || this.presentation == null || 
            this.presentation.getSlideNumber() < 0 || this.slide == null) {
            return;
        }
        
        Rectangle area = new Rectangle(0, 0, getWidth(), getHeight());
        this.renderer.renderSlide(graphics, this.slide, area, 
            this.presentation.getSlideNumber(), this.presentation.getSize());
    }
}
