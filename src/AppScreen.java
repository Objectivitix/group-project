import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AppScreen extends JPanel implements ActionListener {
    JButton chooseColorBtn;
    JButton clearBtn, bgColorBtn;
    MyCanvas canvas;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearBtn) {
            canvas.clear();

            // reset chooseColorBtn and bgColorBtn, too
            chooseColorBtn.setBackground(null);
            chooseColorBtn.setForeground(Color.BLACK);
            bgColorBtn.setBackground(null);
            bgColorBtn.setForeground(Color.BLACK);
        }
        if (e.getSource() == bgColorBtn) {
            canvas.colorBG();
            bgColorBtn.setBackground(null);
        }
    }
    public AppScreen() {
            this.setPreferredSize(new Dimension(600, 600));
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

        // bgColorBtn code
        bgColorBtn = new JButton("Background Color");
        bgColorBtn.setBorder(new RoundedBorder(5));
        bgColorBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color bg = JColorChooser.showDialog(null, "Select background color", Color.WHITE);
                bgColorBtn.setBackground(bg);

                // button text becomes white when BG is too dark
                if (Utils.calcLuminance(bg) < 0.5) {
                    chooseColorBtn.setForeground(Color.WHITE);
                }

                canvas.setBGColor(bg);
            }
        });

            // chooseColorBtn code
            chooseColorBtn = new JButton("Choose Color");
            chooseColorBtn.setBorder(new RoundedBorder(5));
            chooseColorBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color c = JColorChooser.showDialog(null, "Select a color", Color.BLACK);
                    // button itself showcases the chosen color
                    chooseColorBtn.setBackground(c);

                    // button text becomes white when BG is too dark
                    chooseColorBtn.setForeground(
                        Utils.calcLuminance(c) < 0.5
                            ? Color.WHITE : Color.BLACK);

                    canvas.setBrushColor(c);
                }
            });

            JLabel sizeLabel = new JLabel("Size: 1");

            JSlider sizeSlider = new JSlider(1, 100, 8);
            sizeSlider.setPreferredSize(new Dimension(200, 20));
            sizeSlider.addChangeListener(e -> {
                float rawValue = sizeSlider.getValue();
                float realSize = (float) (2 * Math.pow(1.04723, rawValue) - 1.9);

                sizeLabel.setText(String.format("Size: %.1f", realSize));
                canvas.setBrushSize(realSize);
            });

            JButton downloadBtn = new JButton("Save As");
            downloadBtn.addActionListener(e -> {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    canvas.saveAs(chooser.getSelectedFile().getAbsolutePath());
                }
            });

            // add buttons to toolbar
            toolbar.add(chooseColorBtn);
            toolbar.add(bgColorBtn);
            toolbar.add(sizeSlider);
            toolbar.add(sizeLabel);
            toolbar.add(clearBtn);
            toolbar.add(downloadBtn);


            // add toolbar ABOVE canvas (hence NORTH)
            this.add(toolbar, BorderLayout.NORTH);
    }
}