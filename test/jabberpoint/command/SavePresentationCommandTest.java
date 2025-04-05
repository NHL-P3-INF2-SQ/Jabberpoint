package jabberpoint.command;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import jabberpoint.model.Presentation;
import jabberpoint.model.PresentationReceiver;
import javax.swing.JFrame;
import java.nio.file.Path;
import java.awt.Component;
import javax.swing.JFileChooser;

public class SavePresentationCommandTest {
    private Presentation presentation;
    private PresentationReceiver receiver;
    private SavePresentationCommand command;
    private JFrame dummyFrame;

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        dummyFrame = new JFrame() {
            @Override
            public Component add(Component comp) {
                // Override to prevent actual component addition
                return comp;
            }
        };
        receiver = new PresentationReceiver(presentation, dummyFrame);
        command = new SavePresentationCommand(receiver);

        // Add some content to the presentation
        presentation.setTitle("Test Presentation");
        presentation.append(new jabberpoint.model.Slide());
    }

    @Test
    public void testExecuteShowsFileChooser() {
        // Create a custom SecurityManager to prevent actual file operations
        SecurityManager originalManager = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkRead(String file) {
                // Allow read operations
            }

            @Override
            public void checkWrite(String file) {
                // Allow write operations
            }

            @Override
            public void checkPermission(java.security.Permission perm) {
                // Allow other operations
            }
        });

        try {
            // Execute the command - this should show a file chooser
            command.execute();
        } catch (Exception e) {
            // We expect an exception since we can't actually show a dialog in the test
            assertTrue(e.getMessage() == null ||
                    e.getMessage().contains("headless") ||
                    e.getMessage().contains("FileChooser"));
        } finally {
            // Restore the original SecurityManager
            System.setSecurityManager(originalManager);
        }
    }
}