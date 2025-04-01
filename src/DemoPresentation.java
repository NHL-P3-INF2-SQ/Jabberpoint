/**
 * A built-in demonstration presentation that provides a sample
 * presentation to showcase JabberPoint's features.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class DemoPresentation extends Accessor {

	/**
	 * Error message for attempted save operations.
	 */
	private static final String ERROR_SAVE_DEMO = "Cannot save demo presentation";

	/**
	 * Loads the demo presentation with predefined content.
	 * The filename parameter is unused as this is a built-in presentation.
	 *
	 * @param presentation The presentation to load the demo content into
	 * @param unusedFilename Unused parameter (demo is built-in)
	 */
	@Override
	public void loadFile(Presentation presentation, String unusedFilename) {
		presentation.setTitle("Demo Presentation");
		
		// First slide: Introduction
		Slide introSlide = new Slide();
		introSlide.setTitle("JabberPoint");
		introSlide.append(1, "The Java Presentation Tool");
		introSlide.append(2, "Copyright (c) 1996-2000: Ian Darwin");
		introSlide.append(2, "Copyright (c) 2000-now:");
		introSlide.append(2, "Gert Florijn and Sylvia Stuurman");
		introSlide.append(4, "Running JabberPoint without a filename");
		introSlide.append(4, "shows this presentation");
		introSlide.append(1, "Navigation:");
		introSlide.append(3, "Next slide: PgDn or Enter");
		introSlide.append(3, "Previous slide: PgUp or up-arrow");
		introSlide.append(3, "Quit: q or Q");
		presentation.append(introSlide);

		// Second slide: Demonstration of levels and styles
		Slide styleSlide = new Slide();
		styleSlide.setTitle("Demonstration of Levels and Styles");
		styleSlide.append(1, "Level 1");
		styleSlide.append(2, "Level 2");
		styleSlide.append(1, "Level 1 again");
		styleSlide.append(1, "Level 1 uses style number 1");
		styleSlide.append(2, "Level 2 uses style number 2");
		styleSlide.append(3, "This is how level 3 looks");
		styleSlide.append(4, "And this is level 4");
		presentation.append(styleSlide);

		// Third slide: Final slide with image
		Slide finalSlide = new Slide();
		finalSlide.setTitle("The Third Slide");
		finalSlide.append(1, "To open a new presentation,");
		finalSlide.append(2, "use File->Open from the menu.");
		finalSlide.append(1, " ");
		finalSlide.append(1, "This is the end of the presentation.");
		finalSlide.append(new BitmapItem(1, "JabberPoint.jpg"));
		presentation.append(finalSlide);
	}

	/**
	 * Saving is not supported for the demo presentation.
	 *
	 * @param presentation The presentation to save
	 * @param unusedFilename Unused parameter
	 * @throws IllegalStateException Always thrown as saving is not supported
	 */
	@Override
	public void saveFile(Presentation presentation, String unusedFilename) {
		throw new IllegalStateException(ERROR_SAVE_DEMO);
	}
}
