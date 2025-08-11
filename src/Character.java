import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Character extends AssetPainter {
    private final BufferedImage img;
    private final BufferedImage shieldImg;
    private int x;
    private final int y;
    private final int width;
    private final int height;
    private int shieldX;
    private final int shieldY;
    private final int shieldWidth;
    private final int shieldHeight;
    GameWindow gameWindow;

    public Character(GameWindow gameWindow, int i, int max) {
        super(null);
        this.gameWindow = gameWindow;
        try {
            img = ImageIO.read(new File("resources/img/Ship.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            shieldImg = ImageIO.read(new File("resources/img/Shields.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        width = 25;
        height = 25;
        shieldWidth = 27;
        shieldHeight = 27;
        x = (gameWindow.getWidth() - max * width) / 2 + i * width;
        y = gameWindow.getHeight() - 100;
        shieldX = x;
        shieldY = y;
    }


    public void printImg(Graphics g, boolean shieldOn) {
        super.printImg(g);
        g.drawImage(img, x, y, width, height, null);
        if (shieldOn) g.drawImage(shieldImg, shieldX, shieldY, shieldWidth, shieldHeight, null);
    }

    public void setX(int max, int iteratorValue) {
        int totalWidth = max * width;
        int startX = (gameWindow.getWidth() - totalWidth) / 2;
        x = startX + iteratorValue * (width + 2);
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setShieldX(int shieldX) {
        this.shieldX = shieldX;
    }
}