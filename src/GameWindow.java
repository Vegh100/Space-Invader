import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    GamePanel gamePanel;
    Asset characters;
    private final int width;
    private final int height;
    KeyPressed keyPressed;
    MouseMotion mouseMotion;
    JLabel labelLevel;
    JLabel labelScore;
    JLabel labelLives;
    JLabel labelBullet;
    JLabel labelWrong;
    public int score, level;
    public int bulletStrength;
    public int lives;

    public GameWindow() {
        width = 450;
        height = 600;
        int bulletCapacity = 1;
        score = 1;
        bulletStrength = 1;
        lives = 1;
        level = 1;

        setTitle("2D Game");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        characters = new Character(this, 0, 1);
        gamePanel = new GamePanel(characters, this);
        keyPressed = new KeyPressed(gamePanel, this, bulletCapacity);
        mouseMotion = new MouseMotion(gamePanel, this);
        add(gamePanel, BorderLayout.CENTER);
        gamePanel.setKeyPressed(keyPressed);
        gamePanel.addKeyListener(keyPressed);
        gamePanel.addMouseMotionListener(mouseMotion);
        gamePanel.requestFocusInWindow();

        setLocationRelativeTo(null);
        setVisible(true);

        //stats
        createStatWindow();
    }

    private void createStatWindow() {
        JFrame statWindow = new JFrame("Statistics");
        statWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        statWindow.setLayout(new BoxLayout(statWindow.getContentPane(), BoxLayout.Y_AXIS));
        statWindow.setSize(300, 170);

        statWindow.setFocusableWindowState(false);

        JLabel titleLabel = new JLabel("Statistics");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelLevel = new JLabel("LEVEL " + level);
        labelLevel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statWindow.add(titleLabel);
        statWindow.add(labelLevel);

        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scorePanel.add(new JLabel("Score: "));
        labelScore = new JLabel(score + "");
        scorePanel.add(labelScore);
        statWindow.add(scorePanel);

        JPanel livesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        livesPanel.add(new JLabel("Lives: "));
        labelLives = new JLabel(lives + "");
        livesPanel.add(labelLives);
        statWindow.add(livesPanel);

        JPanel bulletPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bulletPanel.add(new JLabel("Bullet Strength: "));
        labelBullet = new JLabel(bulletStrength + " enemy");
        bulletPanel.add(labelBullet);
        statWindow.add(bulletPanel);

        JPanel wrongPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelWrong = new JLabel("");
        wrongPanel.add(labelWrong);
        statWindow.add(wrongPanel);

        statWindow.setLocation(this.getX() + this.getWidth(), this.getY());
        statWindow.setVisible(true);
    }

    public void updateLabelWrong(String name) {
        labelWrong.setText("Max capacity reached - Cannot add " + name);
        labelWrong.setForeground(Color.RED);
        Timer timer = new Timer(2000, _ -> labelWrong.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    public void updateLabelLevel() {
        level++;
        labelLevel.setText("LEVEL " + level);
    }

    public void updateLabelBullet() {
        bulletStrength++;
        labelBullet.setText(bulletStrength + " enemy");
    }

    public void updateLabelScore() {
        score++;
        labelScore.setText(score + "");
    }

    public void updateLabelLives(boolean incDic) {
        if (incDic) {
            lives++;
            labelLives.setText(lives + "");
        } else {
            lives--;
            labelLives.setText(lives + "");
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void exit() {
        dispose();
    }

}
