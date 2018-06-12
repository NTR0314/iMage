package iMage.iDeal;

import org.iMage.shutterpile.impl.filters.WatermarkFilter;
import org.iMage.shutterpile.impl.supplier.ImageWatermarkSupplier;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * This class is the main window for the GUI of iMage
 *
 * @author Oswald
 */
@SuppressWarnings("Duplicates")
public class MainWindow extends JFrame {
    private static final long serialVersionUID = 4424146395462393900L;
    private BufferedImage input;
    private BufferedImage watermark;
    private FileNameExtensionFilter pictureFile = new FileNameExtensionFilter("pictures", "jpg", "jpeg", "png");
    private int threshold = 127;
    private JLabel thresholdLabel = new JLabel("Threshold (127)");
    private boolean grayscaleWatermark = false;

    private JPanel createInputPanel() throws IOException {
        BufferedImage image = Utils.getImage("Input", 200, 150);
        JButton button = new JButton(new ImageIcon(image));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setVisible(true);
        button.setToolTipText("Input Picture");
        //noinspection Duplicates
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(pictureFile);
                int returnValue = fc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        BufferedImage image = ImageIO.read(fc.getSelectedFile());
                        input = Utils.resizeImage(image, 200, 150);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                if (input != null) {
                    button.setIcon(new ImageIcon(input));
                }

            }
        });

        JLabel text = new JLabel("Original");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(text);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 26)));

        return panel;
    }

    private JPanel createWatermarkPanel() throws IOException {
        BufferedImage image = Utils.getImage("Watermark", 200, 150);
        JButton button = new JButton(new ImageIcon(image));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setVisible(true);
        button.setToolTipText("The Watermark");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(pictureFile);
                int returnValue = fc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        BufferedImage image = ImageIO.read(fc.getSelectedFile());
                        watermark = Utils.resizeImage(image, 200, 150);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                if (watermark != null) {
                    button.setIcon(new ImageIcon(watermark));
                }

            }
        });

        JLabel text = new JLabel("Watermark");

        JButton init = new JButton("Init");
        init.setToolTipText("Calculate watermark");
        init.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageWatermarkSupplier iwms = new ImageWatermarkSupplier(watermark, grayscaleWatermark);

                watermark = iwms.getWatermark();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(text);
        panel.add(button);
        panel.add(new JPanel().add(init));


        return panel;
    }

    private JPanel createOutputPanel() throws IOException {
        BufferedImage image = Utils.getImage("Output", 200, 150);
        JButton button = new JButton(new ImageIcon(image));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setVisible(true);
        button.setToolTipText("The result/output picture");

        JLabel text = new JLabel("Output");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(text);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 26)));


        return panel;
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
        setSize(700, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("iDeal");

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        this.add(main);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        try {
            topPanel.add(createInputPanel());
            topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
            topPanel.add(createWatermarkPanel());
            topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
            topPanel.add(createOutputPanel());
        } catch (IOException e) {
            e.printStackTrace();
        }

        main.add(Box.createRigidArea(new Dimension(1, 75)));
        main.add(topPanel);
        main.add(setUpWMField());
        main.add(setUpSlider());
        main.add(setUpLastRow());

        this.setVisible(true);

    }

    private JPanel setUpWMField() {
        JTextField field = new JTextField();
        field.setInputVerifier(new MyInputVerifier());

        field.setMaximumSize(new Dimension(40, 25));

        JLabel text = new JLabel("WM per Row");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(text);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(field);
        panel.setVisible(true);

        return panel;
    }

    private JPanel setUpSlider() {
        JPanel panel = new JPanel();

        JSlider slider = new JSlider(0, 255);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(255);
        slider.setValue(127);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

                threshold = source.getValue();

                thresholdLabel.setText("Treshold(" + threshold + ")");
            }
        });

        panel.add(thresholdLabel);
        panel.add(slider);
        panel.setMaximumSize(new Dimension(500, 200));


        return panel;
    }

    private JPanel setUpLastRow() {
        JPanel panel = new JPanel();

        JCheckBox checkBox = new JCheckBox("Grayscale");
        checkBox.setHorizontalTextPosition(SwingConstants.LEFT);
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JCheckBox src = (JCheckBox) e.getSource();
                grayscaleWatermark = src.isSelected();
            }
        });

        JButton run = new JButton("Run");
        run.setToolTipText("Start calculating watermarked image");
        JButton save = new JButton("Save");
        save.setToolTipText("Save output image to desired location");

        panel.add(checkBox);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(run);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(save);


        return panel;
    }


}
