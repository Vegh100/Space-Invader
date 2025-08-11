import javax.swing.*;

public class StartWindow extends JFrame {

    public StartWindow() {

        StartPanel startPanel = new StartPanel();
        add(startPanel);
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton startButton = startPanel.getStartButton();
        startButton.addActionListener(_ -> {
            new GameWindow();
            dispose();
        });
        JButton exitButton = startPanel.getExitButton();
        exitButton.addActionListener(_ -> System.exit(0));
    }

    public static void main(String[] args) {
        new StartWindow();
    }

}
