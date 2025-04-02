package jabberpoint.ui;
import java.awt.Frame;
import javax.swing.JOptionPane;

/**
 * Displays the About dialog box for JabberPoint.
 * This class provides information about the application's copyright,
 * authors, and licensing terms.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.0 2025/04/01
 */
public class AboutBox {
    
    /**
     * The title of the about dialog.
     */
    private static final String DIALOG_TITLE = "About JabberPoint";
    
    /**
     * The message to display in the about dialog.
     */
    private static final String ABOUT_MESSAGE = 
        "JabberPoint is a primitive slide-show program in Java(tm). It\n" +
        "is freely copyable as long as you keep this notice and\n" +
        "the splash screen intact.\n" +
        "Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
        "Adapted by Gert Florijn (version 1.1) and " +
        "Sylvia Stuurman (version 1.2 and higher) for the Open" +
        "University of the Netherlands, 2002 -- now." +
        "Author's version available from http://www.darwinsys.com/";

    /**
     * Private constructor to prevent instantiation.
     */
    private AboutBox() {
        // Utility class should not be instantiated
    }

    /**
     * Displays the about dialog box with information about JabberPoint.
     *
     * @param parent The parent frame for the dialog box
     */
    public static void show(Frame parent) {
        JOptionPane.showMessageDialog(
            parent,
            ABOUT_MESSAGE,
            DIALOG_TITLE,
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
