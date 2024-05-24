import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Calo was here");
        System.out.println("Matteo was here");

        // make a frame with size
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