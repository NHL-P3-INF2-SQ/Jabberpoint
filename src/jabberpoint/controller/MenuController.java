package jabberpoint.controller;
import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import jabberpoint.model.Presentation;
import jabberpoint.model.PresentationReceiver;
import jabberpoint.command.Command;
import jabberpoint.command.NewPresentationCommand;
import jabberpoint.command.OpenPresentationCommand;
import jabberpoint.command.SavePresentationCommand;
import jabberpoint.command.ExitPresentationCommand;
import jabberpoint.command.NextSlideCommand;
import jabberpoint.command.PreviousSlideCommand;
import jabberpoint.ui.AboutBox;
import jabberpoint.util.ErrorHandler;

/**
 * Controls the menu system of the presentation application.
 * This class manages the creation and handling of menu items for
 * file operations, view control, and help functionality.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.0 2025/04/01
 */
public class MenuController extends MenuBar implements PresentationReceiver.PresentationUpdateListener {
	
	private static final long serialVersionUID = 227L;
	
	/**
	 * The parent frame, used for dialog display.
	 */
	private final Frame parent;
	
	/**
	 * The presentation being controlled.
	 */
	private Presentation presentation;
	
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
	private final Command nextSlideCommand;
	private final Command prevSlideCommand;
	private final PresentationReceiver receiver;  // Store receiver reference for goto command

	/**
	 * Creates a new MenuController with the specified frame and presentation receiver.
	 *
	 * @param frame The parent frame for dialogs
	 * @param receiver The presentation receiver to use for commands
	 */
	public MenuController(Frame frame, PresentationReceiver receiver) {
		this.parent = frame;
		this.presentation = receiver.getPresentation();
		this.receiver = receiver;
		receiver.addPresentationUpdateListener(this);  // Register for presentation updates
		
		this.newPresentationCommand = new NewPresentationCommand(receiver);
		this.openPresentationCommand = new OpenPresentationCommand(receiver);
		this.savePresentationCommand = new SavePresentationCommand(receiver);
		this.exitPresentationCommand = new ExitPresentationCommand(receiver);
		this.nextSlideCommand = new NextSlideCommand(receiver);
		this.prevSlideCommand = new PreviousSlideCommand(receiver);
		
		// Create and configure menus
		createFileMenu();
		createViewMenu();
		createHelpMenu();
	}

	@Override
	public void onPresentationChanged(Presentation newPresentation) {
		this.presentation = newPresentation;
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
		MenuItem nextItem = createMenuItem(NEXT, '>', event -> nextSlideCommand.execute());
		viewMenu.add(nextItem);
		
		// Previous slide menu item
		MenuItem prevItem = createMenuItem(PREV, '<', event -> prevSlideCommand.execute());
		viewMenu.add(prevItem);
		
		// Go to slide menu item
		MenuItem gotoItem = createMenuItem(GOTO, 'G', event -> {
			String pageNumberStr = JOptionPane.showInputDialog(parent, PAGENR);
			try {
				int pageNumber = Integer.parseInt(pageNumberStr);
				// Use the receiver to access the current presentation
				receiver.getPresentation().setSlideNumber(pageNumber - 1);
			} catch (NumberFormatException ex) {
				ErrorHandler.handleValidationError("Invalid page number format", parent);
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
		MenuItem item = new MenuItem(name, new MenuShortcut(shortcut));
		item.addActionListener(listener);
		return item;
	}
}
