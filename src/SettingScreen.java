import javax.swing.*;
import java.awt.*;
import java.awt.Font;

public class SettingScreen extends JFrame {
    //Create variables
    public int height;
    private int length;

    // set color to white as default
    private Color c = Color.WHITE;

    //create frame and panels
    private JFrame settingFrame = new JFrame();
    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();

    private JButton pickColor;

    public SettingScreen() {
        // initialize frame
        settingFrame.setTitle("New Canvas");
        settingFrame.setSize(400, 300);//Setting the size of the frame
        settingFrame.setLayout(null);//Setting the layout of the frame to null
        settingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Setting the close operation to exit on close(when GUI is closed program stops)

        //Make panels
        topPanel.setBounds(0,0, 400, 150);//sets location and dimensions of textPanel
        settingFrame.add(topPanel);//adds text panel to the Jframe

        middlePanel.setBounds(0,150,400, 50);
        settingFrame.add(middlePanel);

        bottomPanel.setBounds(0,200,400, 100);
        settingFrame.add(bottomPanel);


        //top panel - textFields
        JLabel label = new JLabel("Lauder's Colors");
        label.setForeground(Color.BLUE);//set color
        label.setFont(new Font("Brush Script MT", Font.PLAIN, 22));//set font and size
        topPanel.add(label);

        JLabel warning = new JLabel("<html>Dimensions should each be 600 or greater, <br> otherwise canvas size is set to 700x700 automatically.</html>");
        warning.setFont(new Font("Serif", Font.PLAIN, 14));
        topPanel.add(warning);

        JLabel askHeight = new JLabel("Enter canvas height in pixels:");
        topPanel.add(askHeight);//Add instructions (askHeight) label to textPanel

        JTextField enterHeight = new JTextField(10);//Create textField for entering height
        topPanel.add(enterHeight);//adds name textField to textPanel

        JLabel askLength = new JLabel("Enter canvas length in pixels:");
        topPanel.add(askLength);

        JTextField enterLength = new JTextField(10);
        topPanel.add(enterLength);


        //middle panel - color picking button
        pickColor = new JButton("Choose background color");
        pickColor.addActionListener(e -> pickColorPopup());
        middlePanel.add(pickColor);

        //bottom panel (submit button & message label)
        JLabel message = new JLabel("");
        bottomPanel.add(message);

        JButton submit = new JButton("New canvas");
        bottomPanel.add(submit);

        submit.addActionListener(e -> {
            String userHeight = enterHeight.getText();
            String userLength = enterLength.getText();

            // if either input is blank, tell user to enter dimensions
            if (userHeight.isBlank() || userLength.isBlank()) {
                message.setText("Enter two integers for canvas dimensions.");
                return;
            }

            try {
                height = Integer.parseInt(userHeight); // try to parse height to int
            } catch (Exception o) {
                message.setText("Height must be an integer!");
                settingFrame.repaint();
                return;
            }

            try {
                length = Integer.parseInt(userLength); // try to parse length to int
            } catch (Exception o) {
                message.setText("Length must be an integer!");
                settingFrame.repaint();
                return;
            }

            if (height < 600 || length < 600) {
                height = 700;
                length = 700;
            }

            JFrame frame = new JFrame("Swing Paint");

            // make sure when frame is closed, program terminates
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // add screen and resize frame using pack
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
