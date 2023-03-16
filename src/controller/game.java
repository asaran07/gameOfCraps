package controller;

import javax.swing.*;


public class game extends JFrame {

    private void dis(JComponent theComponent) {
        if (theComponent instanceof JButton jButton) {
            jButton.setEnabled(false);
        }
    }

}


