import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Calo was here");
        System.out.println("Matteo was here");
        System.out.println("Fiona was here");

        // make a frame with size
        JFrame frame = new JFrame("Swing Paint");
        frame.setSize(600, 600);

        // center it relative to entire device screen
        frame.setLocationRelativeTo(null);

        // make a SettingScreen frame for testing (Fiona)
        SettingScreen test = new SettingScreen();

        // make sure when frame is closed, program terminates
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add top-level panel to frame (this helps swap screens later)
        frame.add(new AppScreen());

        // make everything visible
        frame.setVisible(true);
    }
}