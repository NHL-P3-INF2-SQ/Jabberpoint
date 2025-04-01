package jabberpoint.io;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.factory.SlideItemFactory;
import jabberpoint.model.TextItem;
import jabberpoint.model.BitmapItem;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/**
 * XMLAccessor handles reading and writing presentations in XML format.
 * This class provides functionality to load presentations from XML files
 * and save presentations to XML files.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/04/01 Updated with improved documentation and encapsulation
 */

public class XMLAccessor extends Accessor {
	
    /**
     * XML tag and attribute names
     */
    private static final String SHOW_TITLE = "showtitle";
    private static final String SLIDE_TITLE = "title";
    private static final String SLIDE = "slide";
    private static final String ITEM = "item";
    private static final String LEVEL = "level";
    private static final String KIND = "kind";
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    
    /**
     * Error messages
     */
    private static final String ERROR_PARSER_CONFIG = "Parser Configuration Exception";
    private static final String ERROR_UNKNOWN_TYPE = "Unknown Element type";
    private static final String ERROR_NUMBER_FORMAT = "Number Format Exception";
    
    /**
     * XML document constants
     */
    private static final String XML_HEADER = "<?xml version=\"1.0\"?>";
    private static final String DOCTYPE = "<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">";
    private static final String ROOT_ELEMENT = "presentation";
    
    /**
     * Extracts the title from an XML element using the specified tag name.
     *
     * @param element The XML element to extract the title from
     * @param tagName The name of the tag containing the title
     * @return The title text content
     */
    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    /**
     * Loads a presentation from an XML file.
     *
     * @param presentation The presentation to load the data into
     * @param filename The name of the file to load
     * @throws IOException If there is an error reading the file
     */
    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();    
            Document document = builder.parse(new File(filename));
            Element root = document.getDocumentElement();
            
            // Set presentation title
            presentation.setTitle(getTitle(root, SHOW_TITLE));

            // Load all slides
            NodeList slides = root.getElementsByTagName(SLIDE);
            for (int i = 0; i < slides.getLength(); i++) {
                Element xmlSlide = (Element) slides.item(i);
                Slide slide = new Slide();
                slide.setTitle(getTitle(xmlSlide, SLIDE_TITLE));
                presentation.append(slide);
                
                // Load all items in the slide
                NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
                for (int j = 0; j < slideItems.getLength(); j++) {
                    loadSlideItem(slide, (Element) slideItems.item(j));
                }
            }
        } catch (ParserConfigurationException e) {
            System.err.println(ERROR_PARSER_CONFIG);
        } catch (SAXException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.toString());
            throw e;
        }
    }

    /**
     * Loads a single slide item from an XML element.
     *
     * @param slide The slide to add the item to
     * @param item The XML element containing the item data
     */
    private void loadSlideItem(Slide slide, Element item) {
        // Get the level attribute
        int level = 1; // default level
        NamedNodeMap attributes = item.getAttributes();
        String levelText = attributes.getNamedItem(LEVEL).getTextContent();
        if (levelText != null) {
            try {
                level = Integer.parseInt(levelText);
            } catch (NumberFormatException e) {
                System.err.println(ERROR_NUMBER_FORMAT);
            }
        }

        // Get the type and content
        String type = attributes.getNamedItem(KIND).getTextContent();
        String content = item.getTextContent();
        
        try {
            // Use the factory to create the appropriate slide item
            SlideItem slideItem = SlideItemFactory.createSlideItem(type, level, content);
            slide.append(slideItem);
        } catch (IllegalArgumentException e) {
            System.err.println(ERROR_UNKNOWN_TYPE + ": " + type);
        }
    }

    /**
     * Saves a presentation to an XML file.
     *
     * @param presentation The presentation to save
     * @param filename The name of the file to save to
     * @throws IOException If there is an error writing to the file
     */
    @Override
    public void saveFile(Presentation presentation, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            // Write XML header and presentation start
            out.println(XML_HEADER);
            out.println(DOCTYPE);
            out.println("<" + ROOT_ELEMENT + ">");
            
            // Write presentation title
            out.printf("<%s>%s</%s>%n", 
                SHOW_TITLE, presentation.getTitle(), SHOW_TITLE);
            
            // Write each slide
            for (int i = 0; i < presentation.getSize(); i++) {
                Slide slide = presentation.getSlide(i);
                out.println("<" + SLIDE + ">");
                out.printf("<%s>%s</%s>%n", 
                    SLIDE_TITLE, slide.getTitle(), SLIDE_TITLE);
                
                // Write slide items
                for (SlideItem slideItem : slide.getSlideItems()) {
                    out.print("<" + ITEM + " kind=\"");
                    if (slideItem instanceof TextItem) {
                        TextItem textItem = (TextItem) slideItem;
                        out.printf("%s\" level=\"%d\">%s", 
                            TEXT, slideItem.getLevel(), textItem.getText());
                    } else if (slideItem instanceof BitmapItem) {
                        BitmapItem bitmapItem = (BitmapItem) slideItem;
                        out.printf("%s\" level=\"%d\">%s", 
                            IMAGE, slideItem.getLevel(), bitmapItem.getImagePath());
                    } else {
                        System.out.println("Ignoring " + slideItem);
                        continue;
                    }
                    out.println("</" + ITEM + ">");
                }
                out.println("</" + SLIDE + ">");
            }
            out.println("</" + ROOT_ELEMENT + ">");
        }
    }
}
