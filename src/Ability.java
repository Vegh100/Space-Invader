import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Ability extends AssetPainter {
    private BufferedImage bufferedImage;
    private final int x;
    private int y;
    private final int width;
    private final int height;
    private int damage;
    private final String imgPath;

    public Ability(GameWindow gameWindow, String imgPath){
        super(null);
        this.imgPath = imgPath;
        try {
            bufferedImage = ImageIO.read(new File(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Random random = new Random();
        this.width = 35;
        this.height = 35;
        this.x = random.nextInt(gameWindow.getWidth() / 2 - width - (25 + width/2)) + gameWindow.getWidth() / 2 + 15;// random.nextInt(gameWindow.getWidth() / 2 - width);
        this.y = 0;
        damage = random.nextInt(20) + 10;
    }

    public void move(int speed){
        this.y += speed;
    }

    public int decreaseDamage(){
       /* if (damage > 1) {
            this.damage--;
            return damage;
        }else{
            this.damage = 0;
            return 0;
        }*/
        damage = Math.max(damage - 1, 0);
        return damage;
    }

    public void printImg(Graphics g){
        super.printImg(g);
         g.drawImage(bufferedImage, x, y, width, height, null);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String damageText = Integer.toString(damage);
        int textWidth = g.getFontMetrics().stringWidth(damageText);
        g.drawString(damageText, x + (width - textWidth) / 2, y - 5);
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

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public String getImgPath() {
        return imgPath;
    }
}
