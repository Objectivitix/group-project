import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Toolbar extends JPanel implements ActionListener, ChangeListener {
    private MyCanvas canvas;
    private JButton chooseColorBtn, bgColorBtn, bgImageBtn, resetBtn, downloadBtn, eraseBtn;
    private JSlider sizeSlider;
    private JLabel sizeLabel;

    public Toolbar(MyCanvas canvas) {
        super();

        this.canvas = canvas;

        // create resetBtn bound to actionPerformed logic below
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

        chooseColorBtn = new JButton("Choose Color");
        chooseColorBtn.setBorder(new RoundedBorder(5));
        chooseColorBtn.addActionListener(this);

        sizeLabel = new JLabel("Size: 1");
        sizeSlider = new JSlider(1, 100, 8);
        sizeSlider.setPreferredSize(new Dimension(200, 20));
        sizeSlider.addChangeListener(this);

        downloadBtn = new JButton("Save As");
        downloadBtn.setBorder(new RoundedBorder(5));
        downloadBtn.addActionListener(this);

        eraseBtn = new JButton("Erase");
        eraseBtn.setBorder(new RoundedBorder(5));
        eraseBtn.addActionListener(this);

        // add buttons to toolbar
        add(resetBtn);
        add(chooseColorBtn);
        add(eraseBtn);
        add(bgColorBtn);
        add(bgImageBtn);
        add(sizeSlider);
        add(sizeLabel);
        add(downloadBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetBtn) reset();
        if (e.getSource() == chooseColorBtn) color();
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
        chooseColorBtn.setBackground(null);
        chooseColorBtn.setForeground(Color.BLACK);
        bgColorBtn.setBackground(null);
        bgColorBtn.setForeground(Color.BLACK);
    }

    private void color() {
        Color c = Utils.colorInput(Color.BLACK);

        // button itself showcases the chosen color
        chooseColorBtn.setBackground(c);

        // button text becomes white when BG is too dark
        chooseColorBtn.setForeground(
            Utils.calcLuminance(c) < 0.5
                ? Color.WHITE : Color.BLACK);

        canvas.setColor(c);
        canvas.setBrushColor(c);
    }

    private void bgColor() {
        Color bg = Utils.colorInput(Color.WHITE);
        bgColorBtn.setBackground(bg);

        // button text becomes white when BG is too dark
        bgColorBtn.setForeground(
            Utils.calcLuminance(bg) < 0.5
                ? Color.WHITE : Color.BLACK);

        canvas.setBGColor(bg);
    }

    private void bgImage() {
        JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }

                return file.getName().matches("(?i).*(\\.jpg|\\.jpeg|\\.png)");
            }

            @Override
            public String getDescription() {
                return "Stationary image files (JPG or PNG)";
            }
        });

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            canvas.setBGImage(chooser.getSelectedFile().getAbsolutePath());
            bgColorBtn.setBackground(null);
            bgColorBtn.setForeground(Color.BLACK);
            canvas.setHasBGImage(true);
        }
    }

    private void sizeSlide() {
        float rawValue = sizeSlider.getValue();
        float realSize = (float) (2 * Math.pow(1.04723, rawValue) - 1.9);

        sizeLabel.setText(String.format("Size: %.1f", realSize));
        canvas.setBrushSize(realSize);
    }

    private void download() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            canvas.saveAs(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void erase() {
        if (canvas.getHasBGImage()) {
            canvas.setBrushColor(Color.WHITE);
        } else {
            canvas.setBrushColor(canvas.getBgColor());
        }
    }
}
