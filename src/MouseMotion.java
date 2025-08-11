import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MouseMotion implements MouseMotionListener {
    private final GamePanel gamePanel;
    private final GameWindow gameWindow;

    public MouseMotion(GamePanel gamePanel, GameWindow gameWindow) {
        this.gamePanel = gamePanel;
        this.gameWindow = gameWindow;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        ArrayList<Character> characters = gamePanel.getCharacterArrayList();

        if (mouseX >= 25 && mouseX <= gameWindow.getWidth() - (characters.getLast().getWidth() * characters.size()) - 25) {
            int offset = 0;
            for (Character character : characters) {
                character.setX(mouseX + offset);
                character.setShieldX(mouseX + offset);
                offset += character.getWidth() + 2;
            }
            gamePanel.repaint();
        }
    }
}
