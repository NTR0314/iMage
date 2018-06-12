package iMage.iDeal;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class MyInputVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        try {
            Integer value = Integer.parseInt(text);
            input.setForeground(Color.BLACK);
            return true;
        } catch (NumberFormatException e) {
            input.setForeground(Color.RED);
            return false;
        }


    }
}