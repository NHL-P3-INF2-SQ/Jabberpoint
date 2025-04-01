import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
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

	private final Command newPresentationCommand;
	private final Command openPresentationCommand;
	private final Command savePresentationCommand;
	private final Command exitPresentationCommand;

	/**
	 * Creates a new MenuController with the specified frame and presentation.
	 *
	 * @param frame The parent frame for dialogs
	 * @param presentation The presentation to control
	 */
	public MenuController(Frame frame, Presentation presentation) {
		this.parent = frame;
		this.presentation = presentation;
		
		PresentationReceiver receiver = new PresentationReceiver(presentation);
		this.newPresentationCommand = new NewPresentationCommand(receiver);
		this.openPresentationCommand = new OpenPresentationCommand(receiver);
		this.savePresentationCommand = new SavePresentationCommand(receiver);
		this.exitPresentationCommand = new ExitPresentationCommand(receiver);
		
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
		MenuItem openItem = createMenuItem(OPEN, 'O', event -> openPresentationCommand.execute());
		fileMenu.add(openItem);
		
		// New menu item
		MenuItem newItem = createMenuItem(NEW, 'N', event -> newPresentationCommand.execute());
		fileMenu.add(newItem);
		
		// Save menu item
		MenuItem saveItem = createMenuItem(SAVE, 'S', event -> savePresentationCommand.execute());
		fileMenu.add(saveItem);
		
		// Exit menu item
		fileMenu.addSeparator();
		MenuItem exitItem = createMenuItem(EXIT, 'Q', event -> exitPresentationCommand.execute());
		fileMenu.add(exitItem);
		
		add(fileMenu);
	}

	/**
	 * Creates the View menu with Next, Previous, and Go to options.
	 */
	private void createViewMenu() {
		Menu viewMenu = new Menu(VIEW);
		
		// Next slide menu item
		MenuItem nextItem = createMenuItem(NEXT, '>', event -> presentation.nextSlide());
		viewMenu.add(nextItem);
		
		// Previous slide menu item
		MenuItem prevItem = createMenuItem(PREV, '<', event -> presentation.prevSlide());
		viewMenu.add(prevItem);
		
		// Go to slide menu item
		MenuItem gotoItem = createMenuItem(GOTO, 'G', event -> {
			String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
			try {
				int pageNumber = Integer.parseInt(pageNumberStr);
				presentation.setSlideNumber(pageNumber - 1);
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
		MenuItem aboutItem = createMenuItem(ABOUT, 'A', event -> AboutBox.show(parent));
		helpMenu.add(aboutItem);
		setHelpMenu(helpMenu);
	}

	/**
	 * Creates a menu item with the specified name and a keyboard shortcut.
	 *
	 * @param name The name of the menu item
	 * @param shortcut The keyboard shortcut
	 * @param listener The action listener
	 * @return The created MenuItem
	 */
	private MenuItem createMenuItem(String name, char shortcut, ActionListener listener) {
		MenuItem menuItem = new MenuItem(name, new MenuShortcut(shortcut));
		menuItem.addActionListener(listener);
		return menuItem;
	}
}
