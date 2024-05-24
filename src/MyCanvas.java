import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MyCanvas extends JPanel {
    // we need an image to draw on - this is the actual "canvas"
    private Image image;

    // a "graphics context" object allows us to draw everything
    private Graphics2D g2;

    // current & previous mouse coordinates, for dragging lines
    private int currentX, currentY, oldX, oldY;

    public MyCanvas() {
        // don't know what this means
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

            // clear canvas using our own method
            clear();
        }

        // update the image by drawing it again
        g.drawImage(image, 0, 0, null);
    }

    // the following methods are used in toolbar
    public void clear() {
        // clearing essentially means drawing white rectangle over entire canvas
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        // set brush color back to black
        g2.setPaint(Color.black);

        // update everything
        repaint();
    }

    public void red() {
        g2.setPaint(Color.red);
    }

    public void orange() {
        g2.setPaint(Color.orange);
    }

    public void yellow() {
        g2.setPaint(Color.yellow);
    }

    public void black() {
        g2.setPaint(Color.black);
    }

    public void magenta() {
        g2.setPaint(Color.magenta);
    }

    public void green() {
        g2.setPaint(Color.green);
    }

    public void blue() {
        g2.setPaint(Color.blue);
    }


//    public void setColor(Color color) {
//        this.color = color;
//    }


}
