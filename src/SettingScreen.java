import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingScreen extends JFrame{
    //Create variables
    public int height;
    private int length;
    private boolean hasHeight;
    private boolean hasLength;
    private Color c = Color.WHITE; // set color to white as default

    //create frame and panels
    private JFrame settingFrame = new JFrame();
    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    public SettingScreen() {
        settingFrame.setTitle("New canvas");

        //Make frame
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

        JLabel warning = new JLabel("<html>Dimensions should each be 600 or greater, <br> otherwise canvas size is set to 600x600 automatically.</html>");
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
        JButton pickColor = new JButton("Choose background color");//make a Jbutton for choosing the background color
        middlePanel.add(pickColor);

        //bottom panel (submit button & message label)
        JLabel message = new JLabel("");
        bottomPanel.add(message);

        JButton submit = new JButton("New canvas");
        bottomPanel.add(submit);

        settingFrame.setVisible(true);//make frame visible

        pickColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c = JColorChooser.showDialog(null, "Select a color", Color.BLACK);
                pickColor.setBackground(c);// button text becomes white when BG is too dark
                pickColor.setForeground(
                        Utils.calcLuminance(c) < 0.5
                                ? Color.WHITE : Color.BLACK);
            }
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hasHeight = false;
                hasLength = false;
                try {
                    height = Integer.parseInt(enterHeight.getText());//assign text from textField to height variable, parse to int
                    hasHeight = true;//accept as valid height
                } catch (Exception o) {
                    message.setText("Height must be an integer!");
                    settingFrame.repaint();
                }
                try {
                    length = Integer.parseInt(enterLength.getText());//assign text from textField to height variable, parse to int
                    hasLength = true;//accept as valid length
                } catch (Exception o) {
                    message.setText("Length must be an integer!");
                    settingFrame.repaint();
                }
                if (hasHeight && hasLength) {
                    if (height >= 600 && length >= 600) {
                        JFrame frame = new JFrame("Swing Paint");
                        frame.setSize(length, height);
                        // make sure when frame is closed, program terminates
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        // add top-level panel to frame (this helps swap screens later)
                        frame.add(new AppScreen(c, height, length));

                        frame.pack();

                        // make everything visible
                        frame.setVisible(true);

                        //close setting screen
                        settingFrame.dispose();
                    } else {
                        JFrame frame = new JFrame("Swing Paint");
                        frame.setSize(600, 600);
                        // make sure when frame is closed, program terminates
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        // add top-level panel to frame (this helps swap screens later)
                        frame.add(new AppScreen(c, 600, 600));

                        frame.pack();

                        // make everything visible
                        frame.setVisible(true);

                        //close setting screen
                        settingFrame.dispose();
                    }
                } else if (!hasHeight && !hasLength){//if neither has been given, output different error message
                    message.setText("Enter two integers for canvas dimensions.");
                }
            }
        });
    }
    public int getLength(){
        return length;//Just in case someone wants to access it from this class?
    }
    public int getHeight(){
        return height;
    }
}