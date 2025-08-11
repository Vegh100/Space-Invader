import javax.swing.*;
import java.awt.*;

public class ExitPanel extends JPanel {
    private final JButton button;

    public ExitPanel() {
        setBackground(Color.GRAY);
        JLabel label = new JLabel("GAME OVER!!!!");
        button = new JButton("Exit");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.black);
        label.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }

    public JButton getButton() {
        return button;
    }

}
