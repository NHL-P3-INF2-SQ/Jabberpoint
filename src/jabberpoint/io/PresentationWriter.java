package jabberpoint.io;

import java.io.IOException;
import jabberpoint.model.Presentation;

/**
 * Interface for writing presentations to a destination.
 */
public interface PresentationWriter {
    void saveFile(Presentation presentation, String filename) throws IOException;
} 