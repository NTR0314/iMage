package iMage.iDeal;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    private JButton createInputJButton() throws IOException {
        BufferedImage inputImage = Utils.getImage("Input", 200, 150);
        JButton inputButton = new JButton(new ImageIcon(inputImage));
        inputButton.setBorder(BorderFactory.createEmptyBorder());
        inputButton.setContentAreaFilled(false);
        inputButton.setVisible(true);

        return inputButton;
    }

    /**
     * Main method of the GUI: sets up the main window
     *
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
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        try {
            topPanel.add(createInputJButton());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.add(topPanel);


        this.setVisible(true);

    }



}
