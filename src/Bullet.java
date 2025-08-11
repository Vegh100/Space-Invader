import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet extends AssetPainter {
    private final BufferedImage img;
    private final int x;
    private int y;
    private final int width;
    private final int height;
    private int capacity;

    public Bullet(int x, int y, int capacity) {
        super(null);
        try {
            img = ImageIO.read(new File("resources/img/YellowBullet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.x = x;
        this.y = y;
        this.width = 15;
        this.height = 15;
        this.capacity = capacity;
    }

    public void move() {
        y -= 10;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void printImg(Graphics g) {
        super.printImg(g);
        g.drawImage(img, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isOutOfBounds() {
        return y < 0 || y > GamePanel.HEIGHT || x < 0 || x > GamePanel.WIDTH;
    }
}
