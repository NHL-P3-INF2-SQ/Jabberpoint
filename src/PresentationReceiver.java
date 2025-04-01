import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import java.io.File;

/**
 * Receiver class that handles all presentation operations
 */
public class PresentationReceiver {
    private Presentation presentation;

    /**
     * Constructor that takes a presentation
     * @param presentation The presentation to operate on
     */
    public PresentationReceiver(Presentation presentation) {
        this.presentation = presentation;
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

        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                this.presentation.clear();  // Clear the current presentation
                Accessor accessor = new XMLAccessor();
                accessor.loadFile(this.presentation, fileChooser.getSelectedFile().getPath());
                this.presentation.setSlideNumber(0);  // Reset to first slide
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error loading presentation: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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

        int returnVal = fileChooser.showSaveDialog(null);
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
                JOptionPane.showMessageDialog(null, 
                    "Error saving presentation: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Exit the presentation
     */
    public void exitPresentation() {
        System.exit(0);
    }
} 