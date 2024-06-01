import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {
    public static Color colorInput(String title, Color initialColor) {
        return JColorChooser.showDialog(null, title, initialColor);
    }

    // calculates perceived brightness of an RGB color
    // as a double between 0 and 1, based on W3 formula:
    // https://www.w3.org/TR/AERT/#color-contrast
    public static double calcLuminance(Color color) {
        return (
            0.299 * color.getRed()
            + 0.587 * color.getGreen()
            + 0.114 * color.getBlue()
        ) / 255;
    }

    public static ImageIcon icon(String filePath, int width, int height) {
        return new ImageIcon(
            new ImageIcon(filePath)
                .getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
    }

    // creates an Image of specified dimensions
    public static Image createImage(String filePath, int width, int height) {
        return toBufferedImage(icon(filePath, width, height).getImage());
    }

    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null),
            BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bi;
    }
}
