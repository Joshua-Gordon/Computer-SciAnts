import java.awt.*;
import java.awt.image.BufferedImage;

public class Utilities {

    public static BufferedImage colorImage(BufferedImage bi, Color toColor) {
        //bi is  int rgb
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                Color c = new Color(bi.getRGB(x, y));
                if(c.equals(Color.BLACK)) {
                    bi.setRGB(x,y,toColor.getRGB());
                }
            }
        }
        return bi;
    }

}
