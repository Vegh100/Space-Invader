import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyPressed implements KeyListener {
    private final GamePanel gamePanel;
    private final GameWindow gameWindow;
    private int capacity;

    public KeyPressed(GamePanel gamePanel, GameWindow gameWindow, int capacity) {
        this.gamePanel = gamePanel;
        this.gameWindow = gameWindow;
        this.capacity = capacity;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        ArrayList<Character> characters = gamePanel.getCharacterArrayList();

        // Balra mozgás
        int moveSpeed = 20;
        if (key == KeyEvent.VK_LEFT && characters.getFirst().getX() - characters.getFirst().getWidth() / 2 > 25) {
            for (Character character : characters) {
                character.setX(character.getX() - moveSpeed);
                character.setShieldX(character.getX());
            }
            gamePanel.repaint();
        }

        // Jobbra mozgás
        if (key == KeyEvent.VK_RIGHT && characters.getLast().getX() + characters.getFirst().getWidth() / 2 < gameWindow.getWidth() - characters.getLast().getWidth() - 25) {
            for (Character character : characters) {
                character.setX(character.getX() + moveSpeed);
                character.setShieldX(character.getX());
            }
            gamePanel.repaint();
            //System.out.println("jobbra-----");
        }

        if (key == KeyEvent.VK_SPACE) {
            for (Character character : characters) {
                gamePanel.fireBullet(character, capacity);
            }
            //System.out.println("space");
        }
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
