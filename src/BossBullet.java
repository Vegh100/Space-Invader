import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BossBullet extends AssetPainter {
    private final BufferedImage bullet;
    private final int x;
    private int y;
    private final int width;
    private final int height;

    public BossBullet(Boss boss) {
        super(null);
        try {
            bullet = ImageIO.read(new File("resources/img/BossBullet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.x = boss.getX() + boss.getWidth() / 2 - 15;
        this.y = boss.getHeight();
        this.width = 15;
        this.height = 15;
    }

    public void move() {
        y += 10;
    }

    public int getY() {
        return y;
    }

    public void printImg(Graphics g) {
        super.printImg(g);
        g.drawImage(bullet, x, y, width, height, null);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

}