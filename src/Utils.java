import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {
    // opens a new color chooser widget
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

    // creates an ImageIcon of specified dimensions
    public static ImageIcon icon(String filePath, int width, int height) {
        return new ImageIcon(
            new ImageIcon(filePath)
                .getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
    }

    // convenience overload for toolbar buttons
    public static ImageIcon icon(String filePath) {
        return icon(filePath, 15, 15);
    }

    // creates an Image of specified dimensions that works with graphics objects
    public static Image createImage(String filePath, int width, int height) {
        return toBufferedImage(icon(filePath, width, height).getImage());
    }

    // we must convert to BufferedImage for it to work with graphics objects
    private static BufferedImage toBufferedImage(Image image) {
        // if we can simply cast, do so
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // create new BufferedImage with same dimensions
        BufferedImage bi = new BufferedImage(
            image.getWidth(null),
            image.getHeight(null),
            BufferedImage.TYPE_INT_RGB
        );

        // draw original image on BufferedImage
        Graphics g = bi.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        // then return it
        return bi;
    }
}
