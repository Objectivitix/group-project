import javax.swing.*;

public class Main {

    // make a SettingScreen frame for testing (Fiona)
    public static SettingScreen test = new SettingScreen();
    public static void main(String[] args) {
        JFrame frame = new JFrame("Swing Paint");

        // center it relative to entire device screen
        frame.setLocationRelativeTo(null);

        ClickandDrop lol = new ClickandDrop();
        lol.setVisible(true);

        // make sure when frame is closed, program terminates
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add top-level panel to frame (this helps swap screens later)
        frame.add(new AppScreen());

        frame.pack();

        // make everything visible
        frame.setVisible(true);
    }
}