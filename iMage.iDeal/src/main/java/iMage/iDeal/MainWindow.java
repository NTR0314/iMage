package iMage.iDeal;

import javax.swing.*;
import java.awt.*;

//TODO add dependancy to shutterpile

/**
 * This class is the main window for the GUI of iMage
 *
 * @author Oswald
 */
public class MainWindow extends JFrame {
    private static final long serialVersionUID = 4424146395462393900L;
    private static final String INPUT_PATH = "src/resources/Input.png";
    private static final String OUTPUT_PATH = "src/resources/Output.png";
    private static final String WATERMARK_PATH = "src/resources/Watermark.png";

    private void setUpJButton() {

    }

    /**
     * Main method of the GUI: sets up the main window
     * @param args arguements
     */
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        mw.setUp();
    }

    private void setUp() {
        this.setSize(700, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("iDeal");
        this.setLayout(new BorderLayout());
        this.setVisible(true);

    }


}
