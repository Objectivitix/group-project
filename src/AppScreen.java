import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

public class AppScreen extends JPanel implements ActionListener {
    JButton clearBtn, blackBtn, redBtn, orangeBtn, yellowBtn, greenBtn, blueBtn, magentaBtn;
    MyCanvas canvas;
    Color color;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearBtn) {
            canvas.clear();
        } else if (e.getSource() == blackBtn) {
            canvas.black();
        } else if (e.getSource() == redBtn) {
            canvas.red();
        } else if (e.getSource() == orangeBtn) {
            canvas.orange();
        } else if (e.getSource() == yellowBtn) {
            canvas.yellow();
        } else if (e.getSource() == greenBtn) {
            canvas.green();
        } else if (e.getSource() == blueBtn) {
            canvas.blue();
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
        clearBtn.setBorder(new RoundedBorder(5)); //5 is the radius
        clearBtn.setBackground(Color.WHITE);

        // color chooser
        JButton chooseColorButton = new JButton("Choose Color");
//        chooseColorButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Color c = JColorChooser.showDialog(null, "Select a color", Color.BLACK);
//                chooseColorButton.setBackground(c);
//                canvas.setColor(c);
//            }
//        });
//
        blackBtn = new JButton("Black");
        blackBtn.addActionListener(this);
        blackBtn.setBorder(new RoundedBorder(5)); //5 is the radius
        blackBtn.setBackground(Color.BLACK);

        redBtn = new JButton("Red");
        redBtn.addActionListener(this);
        redBtn.setBorder(new RoundedBorder(5)); //5 is the radius
        redBtn.setBackground(Color.RED);

        orangeBtn = new JButton("Orange");
        orangeBtn.addActionListener(this);
        orangeBtn.setBorder(new RoundedBorder(5)); //5 is the radius
        orangeBtn.setBackground(Color.ORANGE);

        yellowBtn = new JButton("Yellow");
        yellowBtn.addActionListener(this);
        yellowBtn.setBorder(new RoundedBorder(5)); //5 is the radius
        yellowBtn.setBackground(Color.YELLOW);

        greenBtn = new JButton("Green");
        greenBtn.addActionListener(this);
        greenBtn.setBorder(new RoundedBorder(5)); //5 is the radius
        greenBtn.setBackground(Color.GREEN);

        blueBtn = new JButton("Blue");
        blueBtn.addActionListener(this);
        blueBtn.setBorder(new RoundedBorder(5)); //5 is the radius
        blueBtn.setBackground(Color.BLUE);

        magentaBtn = new JButton("Magenta");
        magentaBtn.addActionListener(this);
        magentaBtn.setBorder(new RoundedBorder(5)); //5 is the radius
        magentaBtn.setBackground(Color.MAGENTA);


        // add buttons to toolbar
        toolbar.add(redBtn);
        toolbar.add(orangeBtn);
        toolbar.add(yellowBtn);
        toolbar.add(greenBtn);
        toolbar.add(blueBtn);
        toolbar.add(magentaBtn);
        toolbar.add(blackBtn);
        toolbar.add(clearBtn);

        // add toolbar ABOVE canvas (hence NORTH)
        this.add(toolbar, BorderLayout.NORTH);
    }
}
