import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

public class SettingScreen extends JFrame{
    //Create variables
    public int height;
    private int length;
    private JFrame settingFrame = new JFrame();
    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    public SettingScreen() {
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

        //Make top label and textField
        JLabel askHeight = new JLabel("Enter canvas height in pixels:");//label to ask for user's input for name
        topPanel.add(askHeight);//Add enter label to textPanel
        JTextField enterHeight = new JTextField(10);//Create textbox for entering height
        topPanel.add(enterHeight);//adds name textField to textPanel

        //Make middle label and textField
        JLabel askLength = new JLabel("Enter canvas length in pixels:");//label to ask for user's input for name
        middlePanel.add(askLength);//Add enter label to textPanel
        JTextField enterLength = new JTextField(10);//Create textbox for entering length
        middlePanel.add(enterLength);//add textField to textPanel

        //Make bottom label and textField
        JLabel askColor = new JLabel("Choose canvas color:");//label to ask for user's input for name
        bottomPanel.add(askColor);//Add enter label to textPanel

        //weird dropdown code?
        String[] options = {"Green", "Blue", "Red", "Orange"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(0); // Set the default selected item

        bottomPanel.add(comboBox);//add textField to textPanel

        settingFrame.setVisible(true);//make frame visible


        enterHeight.addActionListener(new ActionListener() {//Create new ActionListener to detect textField interaction
            @Override//override the existing preset ActionEvent method??
            public void actionPerformed(ActionEvent e) {//when word is entered and enter is clicked
                try {
                    height = Integer.parseInt(enterHeight.getText());//assign text from textField to petName variable
                    //enterHeight.setText("");//replace user's input with thanks
                } catch (InputMismatchException o){
                    askHeight.setText("Please enter an integer!");
                    enterHeight.setText("");
                }
            }
        });

        enterLength.addActionListener(new ActionListener() {//Create new ActionListener to detect textField interaction
            @Override//override the existing preset ActionEvent method??
            public void actionPerformed(ActionEvent e) {//when word is entered and enter is clicked
                try {
                    length = Integer.parseInt(enterLength.getText());//assign text from textField to petName variable
                    //enterHeight.setText("");//replace user's input with thanks
                } catch (InputMismatchException o){
                    askLength.setText("Please enter an integer!");
                    enterLength.setText("");
                }
            }
        });

        enterLength.addActionListener(new ActionListener() {//Create new ActionListener to detect textField interaction
            @Override//override the existing preset ActionEvent method??
            public void actionPerformed(ActionEvent e) {//when word is entered and enter is clicked
                try {
                    length = Integer.parseInt(enterLength.getText());//assign text from textField to petName variable
                    //enterHeight.setText("");//replace user's input with thanks
                } catch (InputMismatchException o){
                    askLength.setText("Please enter an integer!");
                    enterLength.setText("");
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
