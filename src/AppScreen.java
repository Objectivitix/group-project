import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

public class AppScreen extends JPanel implements ActionListener {
    JButton clearBtn;
    MyCanvas canvas;


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearBtn) {
            canvas.clear();
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

            // create clearBtn bound to actionPerformed logic above
            clearBtn = new JButton("Clear");
            clearBtn.addActionListener(this);
            clearBtn.setBorder(new RoundedBorder(5)); //5 is the radius
            clearBtn.setBackground(Color.WHITE);

            // chooseColorBtn code
            JButton chooseColorBtn = new JButton("Choose Color");
            chooseColorBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color c = JColorChooser.showDialog(null, "Select a color", Color.BLACK);
                    // button itself showcases the chosen color (not when it's cleared bc that code is in a diff class lol i haven't figured that out yet)
                    chooseColorBtn.setBackground(c);
                    canvas.setBrushColor(c);
                }
            });

            // add buttons to toolbar
            toolbar.add(chooseColorBtn);
            toolbar.add(clearBtn);


            // add toolbar ABOVE canvas (hence NORTH)
            this.add(toolbar, BorderLayout.NORTH);
    }
}