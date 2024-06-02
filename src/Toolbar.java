import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Toolbar extends JPanel implements ActionListener, ChangeListener {
    // canvas on which resplendent artworks are painted
    private MyCanvas canvas;

    // various buttons
    private JButton colorBtn, bgColorBtn, bgImageBtn, resetBtn, downloadBtn, eraseBtn;

    // used to adjust brush size
    private JSlider sizeSlider;
    private JLabel sizeLabel;

    public Toolbar(MyCanvas canvas) {
        super();
        this.canvas = canvas;

        // create buttons bound to actionPerformed logic below
        resetBtn = new JButton("Reset");
        resetBtn.setBorder(new RoundedBorder(5));  // 5 is the radius
        resetBtn.setBackground(Color.WHITE);
        resetBtn.addActionListener(this);

        bgColorBtn = new JButton("BG Color");
        bgColorBtn.setBorder(new RoundedBorder(5));
        bgColorBtn.addActionListener(this);

        bgImageBtn = new JButton("BG Image");
        bgImageBtn.setBorder(new RoundedBorder(5));
        bgImageBtn.addActionListener(this);

        colorBtn = new JButton("Brush Color");
        colorBtn.setBorder(new RoundedBorder(5));
        colorBtn.addActionListener(this);

        JButton freehandButton = new JButton("Freehand");
        JButton rectangleButton = new JButton("Rectangle");
        JButton circleButton = new JButton("Circle");

        freehandButton.addActionListener(e -> canvas.selectFreehand());
        rectangleButton.addActionListener(e -> canvas.selectRectangle());
        circleButton.addActionListener(e -> canvas.selectCircle());

        // slider is bound to stateChanged logic below
        sizeLabel = new JLabel("Size: 1");
        sizeSlider = new JSlider(1, 100, 8);
        sizeSlider.setPreferredSize(new Dimension(200, 20));
        sizeSlider.addChangeListener(this);

        // these buttons are displayed as icons for smoother UI
        downloadBtn = new JButton(Utils.icon("src/save-as.png", 15, 15));
        downloadBtn.setBorder(new RoundedBorder(5));
        downloadBtn.addActionListener(this);

        eraseBtn = new JButton(Utils.icon("src/eraser.png", 15, 15));
        eraseBtn.setBorder(new RoundedBorder(5));
        eraseBtn.addActionListener(this);

        // add buttons to toolbar
        add(colorBtn);
        add(freehandButton);
        add(rectangleButton);
        add(circleButton);
        add(eraseBtn);
        add(bgColorBtn);
        add(bgImageBtn);
        add(sizeSlider);
        add(sizeLabel);
        add(resetBtn);
        add(downloadBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // when button pressed, delegate to method accordingly
        if (e.getSource() == resetBtn) reset();
        if (e.getSource() == colorBtn) color();
        if (e.getSource() == bgColorBtn) bgColor();
        if (e.getSource() == bgImageBtn) bgImage();
        if (e.getSource() == downloadBtn) download();
        if (e.getSource() == eraseBtn) erase();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == sizeSlider) sizeSlide();
    }

    private void reset() {
        canvas.reset();

        // reset chooseColorBtn and bgColorBtn, too
        colorBtn.setBackground(null);
        colorBtn.setForeground(Color.BLACK);
        bgColorBtn.setBackground(null);
        bgColorBtn.setForeground(Color.BLACK);
    }

    private void color() {
        Color c = Utils.colorInput("Select brush color", Color.BLACK);

        // if user cancels input, simply exit method
        if (c == null) {
            return;
        }

        // button itself showcases the chosen color
        colorBtn.setBackground(c);

        // button text becomes white when BG is too dark
        colorBtn.setForeground(
            Utils.calcLuminance(c) < 0.5
                ? Color.WHITE : Color.BLACK);

        canvas.setColor(c);
    }

    private void bgColor() {
        Color bg = Utils.colorInput("Select background color", Color.WHITE);

        // if user cancels input, simply exit method
        if (bg == null) {
            return;
        }

        bgColorBtn.setBackground(bg);

        // button text becomes white when BG is too dark
        bgColorBtn.setForeground(
            Utils.calcLuminance(bg) < 0.5
                ? Color.WHITE : Color.BLACK);

        canvas.setBGColor(bg);
    }

    private void bgImage() {
        // create a new file chooser
        JFileChooser chooser = new JFileChooser();

        // since we can only open image files, set a filter
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                // allow files to show up in chooser widget if
                // they are folders (since you should be able
                // to navigate into folders)
                if (file.isDirectory()) {
                    return true;
                }

                // using regex, allow files to show up if they
                // are of extensions .jpg, .jpeg, or .png
                return file.getName().matches("(?i).*(\\.jpg|\\.jpeg|\\.png)");
            }

            // write a simple description of desired file type
            @Override
            public String getDescription() {
                return "Stationary image files (JPG or PNG)";
            }
        });

        // open up a "select file" widget, only run code if input goes through
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            canvas.setBGImage(chooser.getSelectedFile().getAbsolutePath());

            // reset bgColorBtn
            bgColorBtn.setBackground(null);
            bgColorBtn.setForeground(Color.BLACK);

            // keep state: other methods need to know if there's a BG image
            canvas.setHasBGImage(true);
        }
    }

    private void sizeSlide() {
        // get current "tick" slider is on; 100 ticks, we start with 8th tick
        float rawValue = sizeSlider.getValue();

        // map tick with actual size using exponential function to create
        // nonlinear slider that goes from 0.2 to 200, inclusive, with
        // much finer control at smaller sizes
        float realSize = (float) (2 * Math.pow(1.04723, rawValue) - 1.9);

        // display and set current size
        sizeLabel.setText(String.format("Size: %.1f", realSize));
        canvas.setBrushSize(realSize);
    }

    private void download() {
        JFileChooser chooser = new JFileChooser();

        // open up a "save as" widget
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            canvas.saveAs(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void erase() {
        // if canvas has background image, we just set brush to white;
        // otherwise, we set it to BG color for erasing effect
        if (canvas.getHasBGImage()) {
            canvas.setBrushColor(Color.WHITE);
        } else {
            canvas.setBrushColor(canvas.getBgColor());
        }

        // reflect this change in colorBtn
        colorBtn.setBackground(canvas.getBgColor());
    }
}
