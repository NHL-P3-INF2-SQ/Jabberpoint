import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

/**
 * The main application window for displaying presentations.
 * This class sets up the window and manages the slide viewer component
 * along with its associated controllers.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class SlideViewerFrame extends JFrame {
	private static final long serialVersionUID = 3227L;
	
	/**
	 * Window title and dimensions
	 */
	private static final String JABTITLE = "Jabberpoint 1.7 - OU";
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;
	
	/**
	 * Creates a new SlideViewerFrame for the specified presentation.
	 *
	 * @param title The window title
	 * @param presentation The presentation to display
	 */
	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
		presentation.setShowView(slideViewerComponent);
		this.setupWindow(slideViewerComponent, presentation);
	}

	/**
	 * Sets up the window with the slide viewer component and controllers.
	 * This includes setting the window title, size, and adding necessary
	 * event listeners and controllers.
	 *
	 * @param slideViewerComponent The component to display slides
	 * @param presentation The presentation being displayed
	 */
	private void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
		this.setTitle(JABTITLE);
		
		// Add window close handler
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// Add components and controllers
		this.getContentPane().add(slideViewerComponent);
		this.addKeyListener(new KeyController(presentation));
		this.setMenuBar(new MenuController(this, presentation));
		
		// Set window properties
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}
}
