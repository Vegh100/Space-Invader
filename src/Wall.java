import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wall extends AssetPainter {
    private final BufferedImage img;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean visible;

    public Wall(int y, GameWindow gameWindow){
        super(null);
        try {
            img = ImageIO.read(new File("resources/img/clouds.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.x = gameWindow.getWidth() / 2;
        this.y = 0;
        this.width = 15;
        this.height = y - 100;
        this.visible = true;
    }

    public void printImg(Graphics g){
        super.printImg(g);
        if (visible){
            g.drawImage(img, x, y, width, height, null);
        }
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
