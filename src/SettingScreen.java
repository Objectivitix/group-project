import javax.swing.*;
import java.awt.*;

public class SettingScreen extends JFrame {
    //Create variables
    //upload image??
    public int height;
    private int length;

    private boolean hasHeight;
    private boolean hasLength;

    private Color c = Color.WHITE;

    private JFrame settingFrame = new JFrame();

    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();

    private JButton pickColor;

    public SettingScreen() {
        super("New Canvas");

        //set submit conditions to false
        hasLength = false;
        hasHeight = false;


        //Make frame
        settingFrame.setSize(400, 300);//Setting the size of the frame
        settingFrame.setLayout(null);//Setting the layout of the frame to null
        settingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Setting the close operation to exit on close(when GUI is closed program stops)

        //Make panels
        topPanel.setBounds(0,0, 400, 100);//sets location and dimensions of textPanel
        settingFrame.add(topPanel);//adds text panel to the Jframe

        middlePanel.setBounds(0,100,400, 100);
        settingFrame.add(middlePanel);

        bottomPanel.setBounds(0,200,400, 100);
        settingFrame.add(bottomPanel);


        //top label - textFields
        JLabel warning = new JLabel("<html>Dimensions should be greater than 300 each, <br> otherwise canvas size is set to 700x700 automatically.</html>");
        topPanel.add(warning);

        JLabel askHeight = new JLabel("Enter canvas height in pixels:");
        topPanel.add(askHeight);//Add enter label to textPanel

        JTextField enterHeight = new JTextField(10);//Create textbox for entering height
        topPanel.add(enterHeight);//adds name textField to textPanel

        JLabel askLength = new JLabel("Enter canvas length in pixels:");
        topPanel.add(askLength);//Add enter label to textPanel

        JTextField enterLength = new JTextField(10);//Create textbox for entering length
        topPanel.add(enterLength);//add textField to textPanel


        // middle color picker popup button
        pickColor = new JButton("Choose background color");
        pickColor.addActionListener(e -> pickColorPopup());
        middlePanel.add(pickColor);

        // bottom panel (submit button & message label)
        JLabel message = new JLabel("");
        bottomPanel.add(message);

        JButton submit = new JButton("New canvas");
        bottomPanel.add(submit);

        submit.addActionListener(e -> {
            try {
                height = Integer.parseInt(enterHeight.getText());//assign text from textField to height variable, parse to int
                hasHeight = true;//accept as valid height
            } catch (Exception o){
                message.setText("Height must be an integer!");
                settingFrame.repaint();
            }

            try{
                length = Integer.parseInt(enterLength.getText());//assign text from textField to height variable, parse to int
                hasLength = true;//accept as valid length
            } catch (Exception o){
                message.setText("Length must be an integer!");
                settingFrame.repaint();
            }

            if (!(hasHeight && hasLength)) {
                return;
            }

            if (height < 300 || length < 300) {
                height = 700;
                length = 700;
            }

            JFrame frame = new JFrame("Swing Paint");

            // make sure when frame is closed, program terminates
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // add top-level panel to frame (this helps swap screens later)
            frame.add(new AppScreen(c, height, length));

            frame.pack();

            // make everything visible
            frame.setVisible(true);

            //close setting screen
            settingFrame.dispose();
        });

        settingFrame.setVisible(true);//make frame visible
    }

    private void pickColorPopup() {
        c = Utils.colorInput("Select canvas background", Color.BLACK);

        // if user cancels input, simply exit method
        if (c == null) {
            return;
        }

        pickColor.setBackground(c);

        // button text becomes white when BG is too dark
        pickColor.setForeground(
            Utils.calcLuminance(c) < 0.5
                ? Color.WHITE : Color.BLACK);
    }
}
