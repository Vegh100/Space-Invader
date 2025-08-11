import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background extends AssetPainter {
    private final BufferedImage FirstBackground;
    private final BufferedImage SecondBackground;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean choice;


    public Background(GameWindow gameWindow){
        super(null);
        try {
            FirstBackground = ImageIO.read(new File("resources/img/Green_Nebula_05-1024x1024.png"));
            SecondBackground = ImageIO.read(new File("resources/img/Starfield_04-1024x1024.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        x = 0;
        y = 0;
        width = gameWindow.getWidth();
        height = gameWindow.getHeight();
        choice = true;
    }

    public void printImg(Graphics g) {
        super.printImg(g);
        if (choice) {
            g.drawImage(FirstBackground, x, y, width, height, null);
        }else {
            g.drawImage(SecondBackground, x, y, width, height, null);
        }
    }

    public void setChoice(boolean choice){
        this.choice = choice;
    }
}
