import javax.swing.*;
import java.awt.*;

public class Form extends JFrame{
    public static final String programTitle = "The Game of Scraps";
    public static final String programVersion = "0.0.1";
    private JPanel titleScreen;
    private JLabel titleLabel;
    private JLabel versionLabel;
    private JButton continueButton;
    private JButton newGameButton;

    public Form() {
    }

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sHeight = screenSize.height/2;
        int sWidth = screenSize.width/2;
        Dimension screenDimension = new Dimension(sWidth,sHeight);

        Form mf = new Form();

        mf.setTitle("TGOS 0.0.1");
        mf.setContentPane(mf.titleScreen);
        mf.setSize(screenDimension);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
