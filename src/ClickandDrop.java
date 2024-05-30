import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ClickandDrop extends JFrame {
    private String selectedShape = "rectangle"; //make a name for the shape
    private int startX, startY, endX, endY;   //where the mouse is clicks -> start, mouse released -> end
    private boolean drawing = false; //

    private final List<Shape> shapes = new ArrayList<>(); // List to store shapes


    public ClickandDrop() {
        setTitle("The real shape shit");
        setSize(800, 600); //set window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // If window is closed the program will end
        setLocationRelativeTo(null);

        JPanel toolbar = new JPanel();
        JButton rectangleButton = new JButton("Rectangle"); //make a rectangle button
        JButton circleButton = new JButton("Circle"); //circle button

        rectangleButton.addActionListener(e -> selectedShape = "rectangle");
        circleButton.addActionListener(e -> selectedShape = "circle");

        toolbar.add(rectangleButton); //add the button to the window
        toolbar.add(circleButton); //add the button to the window

        DrawingPanel drawingPanel = new DrawingPanel();

        add(toolbar, BorderLayout.NORTH); // Adds toolbar at the top of Jpanel
        add(drawingPanel, BorderLayout.CENTER); // Adds drawing panel in the center of the Jpanel
    }

    class Shape {
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
    class DrawingPanel extends JPanel {
        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) { //mouse clicked -> get the horizontal, vertical of the start
                    startX = e.getX();
                    startY = e.getY();
                    drawing = true;
                }

                public void mouseReleased(MouseEvent e) {
                    if (drawing) { //if drawing is false then get the horizontal, vertical of the end
                        endX = e.getX();
                        endY = e.getY();
                        drawing = false;
                        shapes.add(new Shape(selectedShape, startX, startY, endX, endY));
                        repaint(); //update
                    }
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (drawing) {
                        endX = e.getX();
                        endY = e.getY();
                        repaint(); //update
                    }
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Shape shape : shapes) {
                int x = Math.min(shape.startX, shape.endX); // The x-coordinate top left of the shape
                int y = Math.min(shape.startY, shape.endY); // The y-coordinate top left of the shape
                int width = Math.abs(shape.endX - shape.startX); // Width of the shape
                int height = Math.abs(shape.endY - shape.startY); // Height of the shape

                if (shape.type.equals("rectangle")) {
                    g.drawRect(x, y, width, height); // Draw the rectangle
                } else if (shape.type.equals("circle")) {
                    g.drawOval(x, y, width, height); // Draw the circle
                }
            }
            if (drawing) {
                int x = Math.min(startX, endX); //the x-coordinate top left of the shape, the Math.min returns the smaller int of the 2 variables
                int y = Math.min(startY, endY); //the y-coordinate top left of the shape
                int width = Math.abs(endX - startX);//Math.abs(100) is 100 pixels, basically where coordinate the mouse is released - the coordinate mouse clicked
                int height = Math.abs(endY - startY);

                if (selectedShape.equals("rectangle")) {
                    g.drawRect(x, y, width, height); //draw the rectangle and its width, height, coordinate if the rectangle is picked
                } else if (selectedShape.equals("circle")) {
                    g.drawOval(x, y, width, height); //draw the rectangle and its width, height, coordinate if the rectangle is picked
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { //ensure that the creation and display of the GUI happen on the EDT what ever the fuck EDT is
            ClickandDrop shapeDrawer = new ClickandDrop();
            shapeDrawer.setVisible(true);
        });
    }
}
