import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingScreen extends JFrame{
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
    public SettingScreen() {
        setTitle("New canvas");
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
        JLabel warning = new JLabel("<html>Dimensions should be greater than 300 each, <br> otherwise canvas size is set to 600x600 automatically.</html>");
        topPanel.add(warning);

        JLabel askHeight = new JLabel("Enter canvas height in pixels:");
        topPanel.add(askHeight);//Add enter label to textPanel

        JTextField enterHeight = new JTextField(10);//Create textbox for entering height
        topPanel.add(enterHeight);//adds name textField to textPanel

        JLabel askLength = new JLabel("Enter canvas length in pixels:");
        topPanel.add(askLength);//Add enter label to textPanel

        JTextField enterLength = new JTextField(10);//Create textbox for entering length
        topPanel.add(enterLength);//add textField to textPanel


        // middle label dropdown
        /*JLabel askColor = new JLabel("Choose canvas color:");
        middlePanel.add(askColor);//Add enter label to textPanel*/

        JButton pickColor = new JButton("Choose background color");
        middlePanel.add(pickColor);

        //bottom panel (submit button & message label)
        JLabel message = new JLabel("");
        bottomPanel.add(message);

        JButton submit = new JButton("New canvas");
        bottomPanel.add(submit);

        settingFrame.setVisible(true);//make frame visible

        /*weird dropdown code?
        String[] options = {"Green", "Blue", "Red", "Orange"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(0); // Set the default selected item

        middlePanel.add(comboBox);//add textField to textPanel*/

        pickColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
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
                if(hasHeight && hasLength) {
                    if(height >= 300 && length >=300) {
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
                    } else{
                        System.out.println("hi");
                        JFrame frame = new JFrame("Swing Paint");
                        frame.setSize(600, 600);
                        // make sure when frame is closed, program terminates
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        // add top-level panel to frame (this helps swap screens later)
                        frame.add(new AppScreen(c, height, length));

                        frame.pack();

                        // make everything visible
                        frame.setVisible(true);

                        //close setting screen
                        settingFrame.dispose();
                    }
                }
            }
        });
    }
    public int getLength(){
        return length;
    }
    public int getHeight(){
        return height;
    }
    public Color getInitBgColor(){
        return c;
    }
    /*public void setBGColor(Color bg) {
        this.bgColor = bg;

        g2.setPaint(bgColor);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        // set setPaint() method back to chosen color
        setColor(color);
        g2.setPaint(color);

        // update everything
        repaint();
    }*/
}
