import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Boss extends AssetPainter {
    private final BufferedImage[] bossCreationFrames;
    private final BufferedImage boss;
    private int x;
    private final int y;
    private final int width;
    private final int height;
    private final int[] createWidth;
    private final int[] createHeight;
    private int currentFrame;
    private Timer animationTimer;
    private boolean active, ready;
    private int health;
    private final int barWidth;

    public Boss(GameWindow gameWindow) {
        super(null);

        bossCreationFrames = new BufferedImage[6];
        createHeight = new int[6];
        createWidth = new int[6];
        try {
            for (int i = 0; i < 6; i++) {
                System.out.println("resources/img/BossCreate" + (6 - i) + ".png");
                bossCreationFrames[i] = ImageIO.read(new File("resources/img/BossCreate" + (6 - i) + ".png"));
                createWidth[i] = 50 + i*10;
                createHeight[i] = 50 + i*10;
            }
            boss = ImageIO.read(new File("resources/img/Boss.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.x = gameWindow.getWidth()/2;
        this.y = 40;
        this.width = 120;
        this.height = 120;
        this.currentFrame = 0;
        this.active = false;
        this.ready = false;
        this.health = 100;
        this.barWidth = health;

        animationTimer = new Timer(800, _ -> {
            currentFrame++;
            if (currentFrame >= bossCreationFrames.length) {
                animationTimer.stop();
            }
        });
    }

    public void drawHealthBar(Graphics g) {
        int barHeight = 10;
        int currentHealthWidth = health;

        g.setColor(Color.RED);
        g.fillRect(x - barWidth/2, 15, barWidth, barHeight);

        g.setColor(Color.GREEN);
        g.fillRect(x - barWidth/2, 15, currentHealthWidth, barHeight);
    }

    @Override
    public void printImg(Graphics g) {
        if (!active) return;
        super.printImg(g);
        animationTimer.start();
        if (currentFrame < bossCreationFrames.length) {
            g.drawImage(bossCreationFrames[currentFrame], x - createWidth[currentFrame]/2, y, createWidth[currentFrame], createHeight[currentFrame], null);
        } else {
            g.drawImage(boss, x - width/2, y, width, height, null);
            drawHealthBar(g);
            ready = true;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getX() {
        return x - width/2;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isReady() {
        return ready;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
