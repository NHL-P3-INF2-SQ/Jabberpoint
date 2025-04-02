package jabberpoint.ui.renderer;

import java.awt.Graphics;
import java.awt.Rectangle;
import jabberpoint.model.Slide;
import jabberpoint.model.Presentation;

/**
 * Interface defining the abstraction for rendering presentations.
 * This is part of the Bridge pattern, allowing different rendering implementations
 * to be used with the same presentation abstraction.
 */
public interface PresentationRenderer {
    /**
     * Renders a slide within the specified area.
     *
     * @param graphics The graphics context to render on
     * @param slide The slide to render
     * @param area The area in which to render the slide
     * @param slideNumber The current slide number (1-based)
     * @param totalSlides The total number of slides
     */
    void renderSlide(Graphics graphics, Slide slide, Rectangle area, int slideNumber, int totalSlides);

    /**
     * Updates the presentation title in the UI.
     *
     * @param presentation The presentation containing the title
     */
    void updateTitle(Presentation presentation);
} 