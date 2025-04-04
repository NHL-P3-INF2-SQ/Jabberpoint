package jabberpoint.io.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.io.PresentationWriter;
import jabberpoint.util.ErrorHandler;

/**
 * Implementation of PresentationWriter for XML files.
 */
public class XMLWriter implements PresentationWriter {
    private static final String XML_HEADER = "<?xml version=\"1.0\"?>";
    private static final String DOCTYPE = "<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">";
    private static final String ROOT_ELEMENT = "presentation";
    private static final String SHOW_TITLE = "showtitle";
    private static final String SLIDE = "slide";
    private static final String SLIDE_TITLE = "title";
    private static final String ITEM = "item";
    
    private final List<SlideItemSerializer> serializers;
    
    public XMLWriter(List<SlideItemSerializer> serializers) {
        this.serializers = serializers;
    }
    
    @Override
    public void saveFile(Presentation presentation, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            writeHeader(out);
            writePresentation(out, presentation);
        }
    }
    
    private void writeHeader(PrintWriter out) {
        out.println(XML_HEADER);
        out.println(DOCTYPE);
        out.println("<" + ROOT_ELEMENT + ">");
    }
    
    private void writePresentation(PrintWriter out, Presentation presentation) {
        writePresentationTitle(out, presentation.getTitle());
        
        for (int i = 0; i < presentation.getSize(); i++) {
            writeSlide(out, presentation.getSlide(i));
        }
        
        out.println("</" + ROOT_ELEMENT + ">");
    }
    
    private void writePresentationTitle(PrintWriter out, String title) {
        out.printf("<%s>%s</%s>%n", SHOW_TITLE, title, SHOW_TITLE);
    }
    
    private void writeSlide(PrintWriter out, Slide slide) {
        out.println("<" + SLIDE + ">");
        out.printf("<%s>%s</%s>%n", SLIDE_TITLE, slide.getTitle(), SLIDE_TITLE);
        
        for (SlideItem item : slide.getSlideItems()) {
            writeSlideItem(out, item);
        }
        
        out.println("</" + SLIDE + ">");
    }
    
    private void writeSlideItem(PrintWriter out, SlideItem item) {
        for (SlideItemSerializer serializer : serializers) {
            if (serializer.supports(item)) {
                out.print("<" + ITEM + " ");
                out.print(serializer.serialize(item));
                out.println("</" + ITEM + ">");
                return;
            }
        }
        ErrorHandler.handleValidationError("No suitable serializer found for item", null);
    }
} 