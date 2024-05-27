import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

public class SettingScreen extends JFrame{
    //Create variables
    //save file??
    //upload image??
    public int height;
    private int length;
    private boolean hasHeight;
    private boolean hasLength;
    private JFrame settingFrame = new JFrame();
    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    public SettingScreen() {
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
        JLabel askHeight = new JLabel("Enter canvas height in pixels:");
        topPanel.add(askHeight);//Add enter label to textPanel
        JTextField enterHeight = new JTextField(10);//Create textbox for entering height
        topPanel.add(enterHeight);//adds name textField to textPanel

        JLabel warning = new JLabel("<html>Dimensions should be greater than 300 each, <br> otherwise canvas size is set to 600x600 automatically.</html>");
        topPanel.add(warning);

        JLabel askLength = new JLabel("Enter canvas length in pixels:");
        topPanel.add(askLength);//Add enter label to textPanel
        JTextField enterLength = new JTextField(10);//Create textbox for entering length
        topPanel.add(enterLength);//add textField to textPanel

        // middle label dropdown
        JLabel askColor = new JLabel("Choose canvas color:");
        middlePanel.add(askColor);//Add enter label to textPanel

        //weird dropdown code?
        String[] options = {"Green", "Blue", "Red", "Orange"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(0); // Set the default selected item

        middlePanel.add(comboBox);//add textField to textPanel

        //bottom panel submit button & message label
        JLabel message = new JLabel("");
        settingFrame.add(message);

        JButton submit = new JButton("New canvas");
        bottomPanel.add(submit);

        settingFrame.setVisible(true);//make frame visible


        enterHeight.addActionListener(new ActionListener() {//Create new ActionListener to detect textField interaction
            @Override
            public void actionPerformed(ActionEvent e) {//when word is entered and enter is clicked
                try{
                    height = Integer.parseInt(enterHeight.getText());//assign text from textField to height variable, parse to int
                    //enterHeight.setText("");//replace user's input with nothing
                    hasHeight = true;
                } catch (Exception o){
                    enterHeight.setText("");
                    message.setText("Please enter an integer!");
                }
            }
        });

        enterLength.addActionListener(new ActionListener() {//Create new ActionListener to detect textField interaction
            @Override
            public void actionPerformed(ActionEvent e) {//when word is entered and enter is clicked
                try {
                    length = Integer.parseInt(enterLength.getText());//assign text from textField to length variable, parse to int
                    //enterHeight.setText("");//replace user's input with nothing
                    hasLength = true;
                } catch (Exception o){
                    enterLength.setText("");
                    message.setText("Please enter an integer!");
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    height = Integer.parseInt(enterHeight.getText());//assign text from textField to height variable, parse to int
                    //enterHeight.setText("");//replace user's input with nothing
                    hasHeight = true;
                } catch (Exception o){
                    enterHeight.setText("");
                    message.setText("Both numbers must be integers!");
                }
                if(hasHeight && hasLength) {
                    if(height >= 300 && length >=300) {
                        JFrame frame = new JFrame("Swing Paint");
                        frame.setSize(length, height);
                        // make sure when frame is closed, program terminates
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        // add top-level panel to frame (this helps swap screens later)
                        frame.add(new AppScreen());

                        // make everything visible
                        frame.setVisible(true);
                    } else{
                        JFrame frame = new JFrame("Swing Paint");
                        frame.setSize(600, 600);
                        // make sure when frame is closed, program terminates
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        // add top-level panel to frame (this helps swap screens later)
                        frame.add(new AppScreen());

                        // make everything visible
                        frame.setVisible(true);
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
    }
