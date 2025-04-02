package jabberpoint;

import jabberpoint.model.Presentation;
import jabberpoint.model.Style;
import jabberpoint.io.Accessor;
import jabberpoint.io.XMLAccessor;
import jabberpoint.ui.SlideViewerFrame;
import jabberpoint.util.ErrorHandler;
import java.io.IOException;

/** JabberPoint Main Programma
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.0 2025/04/01
 */

public class JabberPoint {
    protected static final String IOERR = "IO Error: ";
    protected static final String JABERR = "Jabberpoint Error ";
    protected static final String JABVERSION = "Jabberpoint 1.0 - OU version";

    /** Het Main Programma */
    public static void main(String argv[]) {
        
        Style.createStyles();
        Presentation presentation = new Presentation();
        SlideViewerFrame frame = new SlideViewerFrame(JABVERSION, presentation);
        try {
            if (argv.length == 0) { // een demo presentatie
                Accessor.getDemoAccessor().loadFile(presentation, "");
            } else {
                new XMLAccessor().loadFile(presentation, argv[0]);
            }
            presentation.setSlideNumber(0);
        } catch (IOException ex) {
            ErrorHandler.handleIOError(ex, frame);
        }
    }
}
