import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Grass extends AssetPainter {
    private final BufferedImage img;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Grass(GameWindow gameWindow, int x, int y) {
        super(null);
        try {
            img = ImageIO.read(new File("resources/img/clouds.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.x = x;
        this.y = y;
        this.width = 25;
        this.height = gameWindow.getHeight();
    }

    public void printImg(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }
}
