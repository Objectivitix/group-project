import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    // Shapes list to store rectangles and circles
    private List<Shape> shapes = new ArrayList<>();

    // Variables to track drawing mode
    private String selectedShape = "freehand";
    private boolean drawing = false;
    private int startX, startY, endX, endY;

    public MyCanvas(Color bg) {
        bgColor = bg;

        setDoubleBuffered(false);

        // when mouse pressed in our canvas, save "starting" coordinates
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
                if (selectedShape.equals("freehand")) {
                    currentX = oldX;
                    currentY = oldY;
                    drawing = true;
                } else {
                    startX = oldX;
                    startY = oldY;
                    drawing = true;
                }
            }
            public void mouseReleased(MouseEvent e) {
                if (!selectedShape.equals("freehand")) {
                    endX = e.getX();
                    endY = e.getY();
                    int x = Math.min(startX, endX);//the x-coordinate top left of the shape, the Math.min returns the smaller int of the 2 variables
                    int y = Math.min(startY, endY);//the y-coordinate top left of the shape
                    int width = Math.abs(endX - startX);//Math.abs(100) is 100 pixels, basically where coordinate the mouse is released - the coordinate mouse clicked
                    int height = Math.abs(endY - startY);

                    if (selectedShape.equals("rectangle")) {
                        g2.drawRect(x, y, width, height); //draw the rectangle and its width, height, coordinate if the rectangle is picked
                    } else if (selectedShape.equals("circle")) {
                        g2.drawOval(x, y, width, height); //draw the rectangle and its width, height, coordinate if the rectangle is picked
                    }
                    drawing = false;
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // user drags mouse - these are most recent coordinates
                currentX = e.getX();
                currentY = e.getY();

                if (g2 != null&& selectedShape.equals("freehand")) {
                    // draw line if g2 context not null
                    g2.drawLine(oldX, oldY, currentX, currentY);

                    // refresh canvas to display line
                    repaint();

                    // make current coordinates old (for new ones to become current)
                    oldX = currentX;
                    oldY = currentY;
                }
                else if (!selectedShape.equals("freehand")) {
                    endX = currentX;
                    endY = currentY;
                    repaint();
                }
            }
        });
    }

    private void initializeGraphics() {
        // make an image to draw on
        image = createImage(getWidth(), getHeight());

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

    // `paintComponent` is called by `repaint` method,
    // so it helps us update the canvas
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            initializeGraphics();
        }

        // update the image by drawing it again
        g.drawImage(image, 0, 0, null);

        if (drawing && !selectedShape.equals("freehand")) {
            int x = Math.min(startX, endX);//the x-coordinate top left of the shape, the Math.min returns the smaller int of the 2 variables
            int y = Math.min(startY, endY);//the y-coordinate top left of the shape
            int width = Math.abs(endX - startX);//Math.abs(100) is 100 pixels, basically where coordinate the mouse is released - the coordinate mouse clicked
            int height = Math.abs(endY - startY);

            if (selectedShape.equals("rectangle")) {
                g.drawRect(x, y, width, height); //draw the rectangle and its width, height, coordinate if the rectangle is picked
            } else if (selectedShape.equals("circle")) {
                g.drawOval(x, y, width, height); //draw the rectangle and its width, height, coordinate if the rectangle is picked
            }
        }
    }

    // the following methods are used in toolbar
    public void reset() {
        // to reset everything is to draw white rectangle over entire canvas
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        // set brush color back to black
        setColor(Color.BLACK);

        //set hasBgImage back to false
        hasBGImage = false;

        //set bgColor variable to white for eraser
        bgColor = Color.white;

        //delete the shapes WOOHOOO
        shapes.clear();

        // update everything
        repaint();
    }

    public void setBGColor(Color bg) {
        bgColor = bg;

        g2.setPaint(bgColor);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        // set setPaint() method back to chosen color
        setColor(color);
        hasBGImage = false;

        // update everything
        repaint();
    }

    public void setBGImage(String filePath) {
        // first we create the image with dimensions equal to canvas's
        image = Utils.createImage(filePath, getWidth(), getHeight());

        // then we update graphics object, enabling antialiasing as usual
        g2 = (Graphics2D) image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // finally, make sure this new g2 has our color & brush size too
        setColor(color);
        setBrushSize(brushSize);

        // aaaand update everything on the actual display
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
        brushSize = size;

        // sets BasicStroke with our brush size, and round end caps for smooth freehanding
        g2.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
    }

    public void saveAs(String filePath) {
        // create new BufferedImage with dimensions equal to canvas's
        Rectangle rect = getBounds();
        BufferedImage img = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);

        // paint our canvas on this image's graphics, effectively copying it
        paint(img.getGraphics());

        try {
            // use the ImageIO to write the image to a file
            ImageIO.write(img, "png", new File(filePath));
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

    // Methods to set the selected shape
    public void selectFreehand() {
        selectedShape = "freehand";
    }

    public void selectRectangle() {
        selectedShape = "rectangle";
    }

    public void selectCircle() {
        selectedShape = "circle";
    }
    // Shape class to store shape information
    private static class Shape {
        String type;
        int startX, startY, endX, endY;

        Shape(String type, int startX, int startY, int endX, int endY) {
            this.type = type;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }
    }
}
