import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Controls the menu system of the presentation application.
 * This class manages the creation and handling of menu items for
 * file operations, view control, and help functionality.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */
public class MenuController extends MenuBar {
	
	private static final long serialVersionUID = 227L;
	
	/**
	 * The parent frame, used for dialog display.
	 */
	private final Frame parent;
	
	/**
	 * The presentation being controlled.
	 */
	private final Presentation presentation;
	
	/**
	 * Menu item labels
	 */
	private static final String ABOUT = "About";
	private static final String FILE = "File";
	private static final String EXIT = "Exit";
	private static final String GOTO = "Go to";
	private static final String HELP = "Help";
	private static final String NEW = "New";
	private static final String NEXT = "Next";
	private static final String OPEN = "Open";
	private static final String PAGENR = "Page number?";
	private static final String PREV = "Prev";
	private static final String SAVE = "Save";
	private static final String VIEW = "View";
	
	/**
	 * File names
	 */
	private static final String TESTFILE = "test.xml";
	private static final String SAVEFILE = "dump.xml";
	
	/**
	 * Error messages
	 */
	private static final String IOEX = "IO Exception: ";
	private static final String LOADERR = "Load Error";
	private static final String SAVEERR = "Save Error";

	/**
	 * Creates a new MenuController with the specified frame and presentation.
	 *
	 * @param frame The parent frame for dialogs
	 * @param presentation The presentation to control
	 */
	public MenuController(Frame frame, Presentation presentation) {
		this.parent = frame;
		this.presentation = presentation;
		
		// Create and configure menus
		createFileMenu();
		createViewMenu();
		createHelpMenu();
	}

	/**
	 * Creates the File menu with Open, New, Save, and Exit options.
	 */
	private void createFileMenu() {
		Menu fileMenu = new Menu(FILE);
		
		// Open menu item
		MenuItem openItem = createMenuItem(OPEN);
		openItem.addActionListener(e -> {
			this.presentation.clear();
			Accessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.loadFile(this.presentation, TESTFILE);
				this.presentation.setSlideNumber(0);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(this.parent, IOEX + exc, 
					LOADERR, JOptionPane.ERROR_MESSAGE);
			}
			this.parent.repaint();
		});
		fileMenu.add(openItem);
		
		// New menu item
		MenuItem newItem = createMenuItem(NEW);
		newItem.addActionListener(e -> {
			this.presentation.clear();
			this.parent.repaint();
		});
		fileMenu.add(newItem);
		
		// Save menu item
		MenuItem saveItem = createMenuItem(SAVE);
		saveItem.addActionListener(e -> {
			Accessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.saveFile(this.presentation, SAVEFILE);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(this.parent, IOEX + exc, 
					SAVEERR, JOptionPane.ERROR_MESSAGE);
			}
		});
		fileMenu.add(saveItem);
		
		// Exit menu item
		fileMenu.addSeparator();
		MenuItem exitItem = createMenuItem(EXIT);
		exitItem.addActionListener(e -> this.presentation.exit(0));
		fileMenu.add(exitItem);
		
		add(fileMenu);
	}

	/**
	 * Creates the View menu with Next, Previous, and Go to options.
	 */
	private void createViewMenu() {
		Menu viewMenu = new Menu(VIEW);
		
		// Next slide menu item
		MenuItem nextItem = createMenuItem(NEXT);
		nextItem.addActionListener(e -> this.presentation.nextSlide());
		viewMenu.add(nextItem);
		
		// Previous slide menu item
		MenuItem prevItem = createMenuItem(PREV);
		prevItem.addActionListener(e -> this.presentation.prevSlide());
		viewMenu.add(prevItem);
		
		// Go to slide menu item
		MenuItem gotoItem = createMenuItem(GOTO);
		gotoItem.addActionListener(e -> {
			String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
			try {
				int pageNumber = Integer.parseInt(pageNumberStr);
				this.presentation.setSlideNumber(pageNumber - 1);
			} catch (NumberFormatException ex) {
				// Ignore invalid input
			}
		});
		viewMenu.add(gotoItem);
		
		add(viewMenu);
	}

	/**
	 * Creates the Help menu with About option.
	 */
	private void createHelpMenu() {
		Menu helpMenu = new Menu(HELP);
		MenuItem aboutItem = createMenuItem(ABOUT);
		aboutItem.addActionListener(e -> AboutBox.show(this.parent));
		helpMenu.add(aboutItem);
		setHelpMenu(helpMenu);
	}

	/**
	 * Creates a menu item with the specified name and a keyboard shortcut.
	 *
	 * @param name The name of the menu item
	 * @return The created MenuItem
	 */
	private MenuItem createMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}
}
