import javax.swing.*;
import java.awt.*;

public class AppScreen extends JPanel {
    public AppScreen(Color bg, int h, int l) {
        // set dimension and layout
        this.setPreferredSize(new Dimension(l, h));
        this.setLayout(new BorderLayout());

        // create and add canvas to screen
        MyCanvas canvas = new MyCanvas(bg);
        this.add(canvas, BorderLayout.CENTER);

        Toolbar toolbar = new Toolbar(canvas);

        // add toolbar ABOVE canvas (hence NORTH)
        this.add(toolbar, BorderLayout.NORTH);
    }
}
