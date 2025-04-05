package jabberpoint.util;
import jabberpoint.io.Accessor;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.factory.SlideItemFactory;

/**
 * A built-in demonstration presentation that provides a sample
 * presentation to showcase JabberPoint's features.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.0 2025/04/01
 */
public class DemoPresentation extends Accessor {

	/**
	 * Loads the demo presentation with predefined content.
	 * The filename parameter is unused as this is a built-in presentation.
	 *
	 * @param presentation The presentation to load the demo content into
	 * @param unusedFilename Unused parameter (demo is built-in)
	 */
	@Override
	public void loadFile(Presentation presentation, String unusedFilename) {
		// If content is present for whatever reason, wipe it.
		presentation.clear();


		presentation.setTitle("Demo Presentation");
		
		// Create first slide
		Slide slide = new Slide();
		slide.setTitle("JabberPoint");
		slide.append(SlideItemFactory.createTextItem(1, "The Java presentation tool"));
		slide.append(SlideItemFactory.createTextItem(2, "Copyright (c) 1996-now by Ian F. Darwin"));
		slide.append(SlideItemFactory.createTextItem(2, "Adapted by Gert Florijn and Sylvia Stuurman"));
		slide.append(SlideItemFactory.createTextItem(4, "JabberPoint® support: ian@darwinsys.com"));
		presentation.append(slide);

		// Create second slide
		slide = new Slide();
		slide.setTitle("What is JabberPoint?");
		slide.append(SlideItemFactory.createTextItem(1, "JabberPoint is a primitive slide-show program in Java"));
		slide.append(SlideItemFactory.createTextItem(2, "It is freely copyable as long as you keep this notice"));
		slide.append(SlideItemFactory.createTextItem(2, "The slideshow data is read from text files"));
		slide.append(SlideItemFactory.createTextItem(2, "Images referenced from the text files are displayed"));
		slide.append(SlideItemFactory.createTextItem(1, "JabberPoint® was created by Ian F. Darwin"));
		presentation.append(slide);

		// Create third slide
		slide = new Slide();
		slide.setTitle("JabberPoint® features");
		slide.append(SlideItemFactory.createTextItem(1, "Text and image items"));
		slide.append(SlideItemFactory.createTextItem(2, "Different text styles and levels"));
		slide.append(SlideItemFactory.createTextItem(2, "XML-based file format"));
		slide.append(SlideItemFactory.createTextItem(2, "Keyboard and mouse navigation"));
		slide.append(SlideItemFactory.createTextItem(2, "Automatic sizing and scaling"));
		slide.append(SlideItemFactory.createTextItem(1, "Demonstration images"));
		slide.append(SlideItemFactory.createBitmapItem(1, "JabberPoint.jpg"));
		presentation.append(slide);
	}

	/**
	 * Saving is not supported for the demo presentation.
	 *
	 * @param presentation The presentation to save (unused)
	 * @param filename The filename to save to (unused)
	 * @throws IllegalStateException Always, as saving is not supported
	 */
	@Override
	public void saveFile(Presentation presentation, String filename) {
		ErrorHandler.handleValidationError("Cannot save demo presentation", null);
		throw new IllegalStateException("Cannot save demo presentation");
	}
}
