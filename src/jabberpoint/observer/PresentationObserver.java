package jabberpoint.observer;

import jabberpoint.model.Slide;
import jabberpoint.model.Presentation;

/**
 * Interface for objects that want to observe presentation changes.
 * Implements the Observer pattern to decouple presentation state changes
 * from the UI components that display them.
 */
public interface PresentationObserver {
    /**
     * Called when the presentation state changes.
     *
     * @param presentation The presentation that changed
     * @param currentSlide The current slide being displayed
     */
    void onPresentationUpdate(Presentation presentation, Slide currentSlide);
} 