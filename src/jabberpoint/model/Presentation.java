package jabberpoint.model;

import java.util.ArrayList;
import jabberpoint.ui.SlideViewerComponent;

/**
 * Manages a presentation consisting of multiple slides.
 * This class maintains the collection of slides and handles navigation between them.
 * There is typically only one instance of this class per presentation.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class Presentation {
	
	/**
	 * The title of the presentation.
	 */
	private String showTitle;
	
	/**
	 * The collection of slides in the presentation.
	 */
	private ArrayList<Slide> showList;
	
	/**
	 * The index of the current slide being displayed.
	 */
	private int currentSlideNumber;
	
	/**
	 * The component responsible for displaying slides.
	 */
	private SlideViewerComponent slideViewComponent;

	/**
	 * Creates a new empty presentation.
	 */
	public Presentation() {
		this(null);
	}

	/**
	 * Creates a new presentation with the specified viewer component.
	 *
	 * @param slideViewerComponent The component to display slides
	 */
	public Presentation(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
		this.clear();
	}

	/**
	 * Gets the total number of slides in the presentation.
	 *
	 * @return The number of slides
	 */
	public int getSize() {
		return this.showList.size();
	}

	/**
	 * Gets the presentation title.
	 *
	 * @return The title of the presentation
	 */
	public String getTitle() {
		return this.showTitle;
	}

	/**
	 * Sets the presentation title.
	 *
	 * @param title The new title for the presentation
	 */
	public void setTitle(String title) {
		this.showTitle = title;
	}

	/**
	 * Sets the slide viewer component for this presentation.
	 *
	 * @param slideViewerComponent The component to display slides
	 */
	public void setShowView(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
	}

	/**
	 * Gets the current slide number (0-based index).
	 *
	 * @return The current slide number
	 */
	public int getSlideNumber() {
		return this.currentSlideNumber;
	}

	/**
	 * Sets the current slide number and updates the display.
	 *
	 * @param number The new slide number (0-based index)
	 */
	public void setSlideNumber(int number) {
		this.currentSlideNumber = number;
		if (this.slideViewComponent != null) {
			this.slideViewComponent.update(this, this.getCurrentSlide());
		}
	}

	/**
	 * Moves to the previous slide if not at the beginning.
	 */
	public void prevSlide() {
		if (this.currentSlideNumber > 0) {
			this.setSlideNumber(this.currentSlideNumber - 1);
		}
	}

	/**
	 * Moves to the next slide if not at the end.
	 */
	public void nextSlide() {
		if (this.currentSlideNumber < (this.showList.size() - 1)) {
			this.setSlideNumber(this.currentSlideNumber + 1);
		}
	}

	/**
	 * Clears the presentation, removing all slides.
	 */
	public void clear() {
		this.showList = new ArrayList<>();
		this.setSlideNumber(-1);
	}

	/**
	 * Adds a slide to the presentation.
	 *
	 * @param slide The slide to add
	 */
	public void append(Slide slide) {
		this.showList.add(slide);
	}

	/**
	 * Gets a slide by its index.
	 *
	 * @param number The index of the slide to retrieve
	 * @return The requested slide, or null if the index is invalid
	 */
	public Slide getSlide(int number) {
		if (number < 0 || number >= this.getSize()) {
			return null;
		}
		return this.showList.get(number);
	}

	/**
	 * Gets the currently displayed slide.
	 *
	 * @return The current slide
	 */
	public Slide getCurrentSlide() {
		return this.getSlide(this.currentSlideNumber);
	}

	/**
	 * Exits the application with the specified status code.
	 *
	 * @param status The exit status code
	 */
	public void exit(int status) {
		System.exit(status);
	}
}
