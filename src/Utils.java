import java.awt.*;

public class Utils {
    public static double calcLuminance(Color color) {
        // calculates perceived brightness of an RGB color
        // as a double between 0 and 1, based on W3 formula:
        // https://www.w3.org/TR/AERT/#color-contrast
        return (
            0.299 * color.getRed()
            + 0.587 * color.getGreen()
            + 0.114 * color.getBlue()
        ) / 255;
    }
}
