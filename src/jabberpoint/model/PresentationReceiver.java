package jabberpoint.model;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import jabberpoint.io.XMLAccessor;
import jabberpoint.io.Accessor;
import jabberpoint.util.ErrorHandler;
import javax.swing.JFrame;

/**
 * Receiver class that handles all presentation operations.
 * This class implements the Command pattern's receiver role,
 * executing the actual operations requested by commands.
 *
 * @author Jesse van der Voet, Bram Suurd
 * @version 1.7 2024/04/02
 */
public class PresentationReceiver {
    private Presentation presentation;
    private JFrame parentFrame;

    /**
     * Constructor that takes a presentation and parent frame
     * @param presentation The presentation to operate on
     * @param parentFrame The parent frame for dialogs
     */
    public PresentationReceiver(Presentation presentation, JFrame parentFrame) {
        this.presentation = presentation;
        this.parentFrame = parentFrame;
    }

    /**
     * Move to the next slide in the presentation
     */
    public void nextSlide() {
        this.presentation.nextSlide();
    }

    /**
     * Move to the previous slide in the presentation
     */
    public void prevSlide() {
        this.presentation.prevSlide();
    }

    /**
     * Create a new presentation
     */
    public void newPresentation() {
        this.presentation = new Presentation();
    }

    /**
     * Open a presentation using a file chooser dialog
     */
    public void openPresentation() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Presentations", "xml"));
        
        // Set initial directory to Downloads folder
        String userHome = System.getProperty("user.home");
        File downloadsDir = new File(userHome, "Downloads");
        if (downloadsDir.exists() && downloadsDir.isDirectory()) {
            fileChooser.setCurrentDirectory(downloadsDir);
        }

        int returnVal = fileChooser.showOpenDialog(parentFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                this.presentation.clear();  // Clear the current presentation
                Accessor accessor = new XMLAccessor();
                accessor.loadFile(this.presentation, fileChooser.getSelectedFile().getPath());
                this.presentation.setSlideNumber(0);  // Reset to first slide
            } catch (IOException e) {
                ErrorHandler.handleIOError(e, parentFrame);
            }
        }
    }

    /**
     * Save the current presentation using a file chooser dialog
     */
    public void savePresentation() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Presentations", "xml"));
        
        // Set initial directory to Downloads folder
        String userHome = System.getProperty("user.home");
        File downloadsDir = new File(userHome, "Downloads");
        if (downloadsDir.exists() && downloadsDir.isDirectory()) {
            fileChooser.setCurrentDirectory(downloadsDir);
        }

        // Suggest a default filename
        fileChooser.setSelectedFile(new File("presentation.xml"));

        int returnVal = fileChooser.showSaveDialog(parentFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                Accessor accessor = new XMLAccessor();
                String filePath = fileChooser.getSelectedFile().getPath();
                // Add .xml extension if not present
                if (!filePath.toLowerCase().endsWith(".xml")) {
                    filePath += ".xml";
                }
                accessor.saveFile(this.presentation, filePath);
            } catch (IOException e) {
                ErrorHandler.handleIOError(e, parentFrame);
            }
        }
    }

    /**
     * Exit the presentation
     */
    public void exitPresentation() {
        try {
            System.exit(0);
        } catch (SecurityException ex) {
            ErrorHandler.handleGeneralError(ex, parentFrame);
        }
    }
} 