import java.awt.*;

public class Brush {
    private Color color;
    private int size;

    public Brush() {
        color = Color.BLACK;
        size = 2;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = Math.max(1, size);
    }
}
