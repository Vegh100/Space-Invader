import javax.swing.*;

public class ExitWindow extends JFrame {
    public ExitWindow() {
        ExitPanel exitPanel = new ExitPanel();
        JButton button = exitPanel.getButton();
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        add(exitPanel);
        button.addActionListener(_ -> System.exit(0));
        setVisible(true);
    }
}
