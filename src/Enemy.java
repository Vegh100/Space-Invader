import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Enemy extends AssetPainter {
    private final BufferedImage img;
    private final int x;
    private int y;
    private final int width;
    private final int height;

    public Enemy(GameWindow gameWindow) {
        super(null);
        Random random = new Random();
        try {
            img = ImageIO.read(new File("resources/img/enemy-big.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.width = 25;
        this.height = 25;
        System.out.println(gameWindow.getWidth() / 2 + " " + width);
        this.x = random.nextInt(gameWindow.getWidth() / 2 - width - 25) + 25;
        this.y = 0;
    }

    public void move(int speed) {
        this.y += speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void printImg(Graphics g) {
        super.printImg(g);
        g.drawImage(img, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Feltételezzük, hogy ezek a mezők már léteznek
    }


}
