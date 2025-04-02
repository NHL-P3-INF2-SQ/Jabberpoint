package jabberpoint.io;

import java.io.IOException;
import jabberpoint.model.Presentation;
import jabberpoint.util.DemoPresentation;

/**
 * An abstract class that provides functionality to read and write presentation data.
 * Concrete subclasses must implement the loadFile and saveFile methods to specify
 * how presentation data should be loaded from and saved to different storage formats.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.0 2025/04/01
 */
public abstract class Accessor {
	
	/**
	 * The default name for the demonstration presentation.
	 */
	private static final String DEMO_NAME = "Demonstratie presentatie";
	
	/**
	 * The default file extension for presentation files.
	 */
	private static final String DEFAULT_EXTENSION = ".xml";

	/**
	 * Creates and returns a new DemoPresentation accessor.
	 * This factory method provides access to the demonstration presentation.
	 *
	 * @return A new DemoPresentation accessor instance
	 */
	public static Accessor getDemoAccessor() {
		return new DemoPresentation();
	}

	/**
	 * Default constructor for the Accessor class.
	 */
	protected Accessor() {
		// Protected constructor to allow subclassing while preventing direct instantiation
	}

	/**
	 * Loads presentation data from a file into a Presentation object.
	 *
	 * @param presentation The Presentation object to load the data into
	 * @param filename The name of the file to load the presentation from
	 * @throws IOException If there is an error reading the file
	 */
	public abstract void loadFile(Presentation presentation, String filename) throws IOException;

	/**
	 * Saves presentation data from a Presentation object to a file.
	 *
	 * @param presentation The Presentation object containing the data to save
	 * @param filename The name of the file to save the presentation to
	 * @throws IOException If there is an error writing to the file
	 */
	public abstract void saveFile(Presentation presentation, String filename) throws IOException;

	/**
	 * Gets the default demonstration presentation name.
	 *
	 * @return The default demonstration presentation name
	 */
	public static String getDemoName() {
		return DEMO_NAME;
	}

	/**
	 * Gets the default file extension for presentation files.
	 *
	 * @return The default file extension
	 */
	public static String getDefaultExtension() {
		return DEFAULT_EXTENSION;
	}
}
