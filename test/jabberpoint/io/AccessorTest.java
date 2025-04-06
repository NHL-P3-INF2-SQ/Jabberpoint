package jabberpoint.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import jabberpoint.model.Presentation;
import jabberpoint.util.DemoPresentation;

public class AccessorTest {

    @Test
    public void testGetDemoAccessor() {
        Accessor accessor = Accessor.getDemoAccessor();
        assertNotNull(accessor);
        assertTrue(accessor instanceof DemoPresentation);
    }

    @Test
    public void testGetDemoName() {
        assertEquals("Demonstratie presentatie", Accessor.getDemoName());
    }

    @Test
    public void testGetDefaultExtension() {
        assertEquals(".xml", Accessor.getDefaultExtension());
    }

    @Test
    public void testConcreteImplementation() throws IOException {
        // Create a concrete implementation for testing
        Accessor concreteAccessor = new Accessor() {
            @Override
            public void loadFile(Presentation presentation, String filename) throws IOException {
                // Test implementation
            }

            @Override
            public void saveFile(Presentation presentation, String filename) throws IOException {
                // Test implementation
            }
        };

        // Verify we can create an instance
        assertNotNull(concreteAccessor);
    }
}