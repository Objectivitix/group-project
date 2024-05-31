import javax.swing.*;
import java.awt.*;

public class AppScreen extends JPanel {
    public AppScreen(Color bg, int height, int length) {
        // set dimension and layout
        setPreferredSize(new Dimension(length, height));
        setLayout(new BorderLayout());

        // create and add canvas to screen
        MyCanvas canvas = new MyCanvas(bg);
        add(canvas, BorderLayout.CENTER);

        // add toolbar ABOVE canvas (hence NORTH)
        Toolbar toolbar = new Toolbar(canvas);
        add(toolbar, BorderLayout.NORTH);
    }
}
