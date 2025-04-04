package jabberpoint.io;

import java.io.IOException;
import jabberpoint.model.Presentation;

/**
 * Interface for reading presentations from a source.
 */
public interface PresentationReader {
    void loadFile(Presentation presentation, String filename) throws IOException;
} 