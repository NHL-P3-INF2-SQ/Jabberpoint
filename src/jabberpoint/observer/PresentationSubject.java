package jabberpoint.observer;

/**
 * Interface for objects that can be observed for presentation changes.
 * Part of the Observer pattern implementation for presentation state changes.
 *
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.0 2025/04/01
 */
public interface PresentationSubject {
    /**
     * Adds an observer to be notified of presentation changes.
     *
     * @param observer The observer to add
     */
    void addObserver(PresentationObserver observer);

    /**
     * Removes an observer from the notification list.
     *
     * @param observer The observer to remove
     */
    void removeObserver(PresentationObserver observer);

    /**
     * Notifies all registered observers of a presentation change.
     */
    void notifyObservers();
}