import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppScreen extends JPanel implements ActionListener {
    JButton clearBtn, blackBtn, blueBtn, greenBtn, redBtn, magentaBtn;
    MyCanvas canvas;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearBtn) {
            canvas.clear();
        } else if (e.getSource() == blackBtn) {
            canvas.black();
        } else if (e.getSource() == blueBtn) {
            canvas.blue();
        } else if (e.getSource() == greenBtn) {
            canvas.green();
        } else if (e.getSource() == redBtn) {
            canvas.red();
        } else if (e.getSource() == magentaBtn) {
            canvas.magenta();
        }
    }

    public AppScreen() {
        // make this screen border layout
        this.setLayout(new BorderLayout());

        // create and add canvas to screen
        canvas = new MyCanvas();
        this.add(canvas, BorderLayout.CENTER);

        // create toolbar
        JPanel toolbar = new JPanel();

        // create buttons bound to actionPerformed logic above
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);
        blackBtn = new JButton("Black");
        blackBtn.addActionListener(this);
        blueBtn = new JButton("Blue");
        blueBtn.addActionListener(this);
        greenBtn = new JButton("Green");
        greenBtn.addActionListener(this);
        redBtn = new JButton("Red");
        redBtn.addActionListener(this);
        magentaBtn = new JButton("Magenta");
        magentaBtn.addActionListener(this);

        // add buttons to toolbar
        toolbar.add(greenBtn);
        toolbar.add(blueBtn);
        toolbar.add(blackBtn);
        toolbar.add(redBtn);
        toolbar.add(magentaBtn);
        toolbar.add(clearBtn);

        // add toolbar ABOVE canvas (hence NORTH)
        this.add(toolbar, BorderLayout.NORTH);
    }
}
