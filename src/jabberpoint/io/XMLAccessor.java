package jabberpoint.io;

import java.io.IOException;
import java.util.Arrays;
import jabberpoint.model.Presentation;
import jabberpoint.io.xml.*;

/**
 * XMLAccessor handles reading and writing presentations in XML format.
 * This class acts as a facade for XML-based presentation storage operations.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Bram Suurd
 * @version 2.0 2025/04/01
 */
public class XMLAccessor extends Accessor {
    private final PresentationReader reader;
    private final PresentationWriter writer;
    
    public XMLAccessor() {
        // Initialize serializers
        var serializers = Arrays.asList(
            new TextItemSerializer(),
            new BitmapItemSerializer()
        );
        
        // Initialize reader and writer
        this.reader = new XMLReader(new DefaultXMLParser(), serializers);
        this.writer = new XMLWriter(serializers);
    }
    
    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
        reader.loadFile(presentation, filename);
    }
    
    @Override
    public void saveFile(Presentation presentation, String filename) throws IOException {
        writer.saveFile(presentation, filename);
    }
}
