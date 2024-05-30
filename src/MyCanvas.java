import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class MyCanvas extends JPanel {
    // we need an image to draw on - this is the actual "canvas"
    private Image image;

    // a "graphics context" object allows us to draw everything
    private Graphics2D g2;

    // current & previous mouse coordinates, for dragging lines
    private int currentX, currentY, oldX, oldY;

    // toolbar changes these variables, and the GUI reflects it
    private Color color;
    private Color bgColor;
    private float brushSize;

    //boolean to determine eraser color
    private boolean hasBGImage = false;


    public MyCanvas(Color bg) {
        bgColor = bg;

        setDoubleBuffered(false);

        // when mouse pressed in our canvas, save "starting" coordinates
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // user drags mouse - these are most recent coordinates
                currentX = e.getX();
                currentY = e.getY();

                if (g2 != null) {
                    // draw line if g2 context not null
                    g2.drawLine(oldX, oldY, currentX, currentY);

                    // refresh canvas to display line
                    repaint();

                    // make current coordinates old (for new ones to become current)
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    // `paintComponent` is called by `repaint` method,
    // so it helps us update the canvas
    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            // if there's no image to draw on, make one
            image = createImage(getSize().width, getSize().height);

            // get graphics context from image
            g2 = (Graphics2D) image.getGraphics();

            // enable antialiasing (makes graphics smoother by softening lines and blurring edges)
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // set initial brush size to 1
            setBrushSize(1);

            // reset canvas using our own method
            reset();

            setBGColor(bgColor);
        }

        // update the image by drawing it again
        g.drawImage(image, 0, 0, null);
    }

    // the following methods are used in toolbar
    public void reset() {
        // to reset everything is to draw white rectangle over entire canvas
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        // set brush color back to black
        setColor(Color.BLACK);

        // update everything
        repaint();
    }

    public void setBGColor(Color bg) {
        this.bgColor = bg;

        g2.setPaint(bgColor);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        // set setPaint() method back to chosen color
        setColor(color);

        // update everything
        repaint();
    }

    public void setBGImage(String filePath) {
        image = Utils.createImage(filePath, getWidth(), getHeight());
        g2 = (Graphics2D) image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setColor(color);
        setBrushSize(brushSize);
        repaint();
    }

    // sets the brush color to the chosen color
    public void setBrushColor(Color color) {
        g2.setPaint(color);
    }

    // sets our color variable to our chosen color
    public void setColor(Color color) {
        this.color = color;
        g2.setPaint(color);
    }

    public void setBrushSize(float size) {
        this.brushSize = size;
        g2.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
    }

    public void saveAs(String filePath) {
        Rectangle rect = this.getBounds();
        BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        this.paint(image.getGraphics());

        try {
            // Use the ImageIO to write the image to a file
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Color getBgColor() {
        return bgColor;
    }

    public boolean getHasBGImage() {
        return hasBGImage;
    }

    public void setHasBGImage(boolean hasBGImage) {
        this.hasBGImage = hasBGImage;
    }
}
