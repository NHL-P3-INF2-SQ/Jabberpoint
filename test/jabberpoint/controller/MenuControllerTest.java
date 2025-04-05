package jabberpoint.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import jabberpoint.model.Presentation;
import jabberpoint.model.PresentationReceiver;
import jabberpoint.model.Slide;
import javax.swing.JFrame;

public class MenuControllerTest {
    private MenuController menuController;
    private Presentation presentation;
    private PresentationReceiver receiver;
    private Frame dummyFrame;
    private JFrame dummyJFrame;

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        dummyFrame = new Frame();
        dummyJFrame = new JFrame();
        receiver = new PresentationReceiver(presentation, dummyJFrame);
        menuController = new MenuController(dummyFrame, receiver);

        // Add some slides to the presentation
        presentation.append(new Slide());
        presentation.append(new Slide());
        presentation.append(new Slide());

        // Set initial slide
        presentation.setSlideNumber(0);
    }

    @Test
    public void testMenuStructure() {
        // Test that all menus are created
        assertEquals(3, menuController.getMenuCount()); // File, View, Help menus

        // Test File menu structure
        Menu fileMenu = menuController.getMenu(0);
        assertEquals("File", fileMenu.getLabel());
        assertEquals(5, fileMenu.getItemCount()); // Open, New, Save, separator, Exit

        // Test View menu structure
        Menu viewMenu = menuController.getMenu(1);
        assertEquals("View", viewMenu.getLabel());
        assertEquals(3, viewMenu.getItemCount()); // Next, Prev, Goto

        // Test Help menu
        Menu helpMenu = menuController.getHelpMenu();
        assertNotNull(helpMenu);
        assertEquals("Help", helpMenu.getLabel());
        assertEquals(1, helpMenu.getItemCount()); // About
    }

    @Test
    public void testFileMenuItems() {
        Menu fileMenu = menuController.getMenu(0);

        // Test menu item labels
        assertEquals("Open", fileMenu.getItem(0).getLabel());
        assertEquals("New", fileMenu.getItem(1).getLabel());
        assertEquals("Save", fileMenu.getItem(2).getLabel());
        assertEquals("Exit", fileMenu.getItem(4).getLabel());

        // Test shortcuts
        assertEquals('O', fileMenu.getItem(0).getShortcut().getKey());
        assertEquals('N', fileMenu.getItem(1).getShortcut().getKey());
        assertEquals('S', fileMenu.getItem(2).getShortcut().getKey());
        assertEquals('Q', fileMenu.getItem(4).getShortcut().getKey());
    }

    @Test
    public void testViewMenuItems() {
        Menu viewMenu = menuController.getMenu(1);

        // Test menu item labels
        assertEquals("Next", viewMenu.getItem(0).getLabel());
        assertEquals("Prev", viewMenu.getItem(1).getLabel());
        assertEquals("Go to", viewMenu.getItem(2).getLabel());

        // Test shortcuts
        assertEquals('>', viewMenu.getItem(0).getShortcut().getKey());
        assertEquals('<', viewMenu.getItem(1).getShortcut().getKey());
        assertEquals('G', viewMenu.getItem(2).getShortcut().getKey());
    }

    @Test
    public void testNextSlideMenuItem() {
        Menu viewMenu = menuController.getMenu(1);
        MenuItem nextItem = viewMenu.getItem(0);

        // Simulate menu item click
        nextItem.getActionListeners()[0].actionPerformed(
                new ActionEvent(nextItem, ActionEvent.ACTION_PERFORMED, "Next"));

        // Verify slide changed
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void testPrevSlideMenuItem() {
        // Start from slide 1
        presentation.setSlideNumber(1);

        Menu viewMenu = menuController.getMenu(1);
        MenuItem prevItem = viewMenu.getItem(1);

        // Simulate menu item click
        prevItem.getActionListeners()[0].actionPerformed(
                new ActionEvent(prevItem, ActionEvent.ACTION_PERFORMED, "Prev"));

        // Verify slide changed
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testNewPresentationMenuItem() {
        Menu fileMenu = menuController.getMenu(0);
        MenuItem newItem = fileMenu.getItem(1);

        // Add some content to current presentation
        presentation.setTitle("Test Presentation");

        // Simulate menu item click
        newItem.getActionListeners()[0].actionPerformed(
                new ActionEvent(newItem, ActionEvent.ACTION_PERFORMED, "New"));

        // Verify new presentation is created
        assertEquals(0, receiver.getPresentation().getSize());
        assertEquals(-1, receiver.getPresentation().getSlideNumber());
    }
}