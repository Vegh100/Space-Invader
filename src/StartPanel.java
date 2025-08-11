import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class StartPanel extends JPanel {
    JButton startButton;
    JButton exitButton;
    JLabel label;
    JPanel buttonPanel;
    JPanel textPanel;
    String email;
    String phone;
    String username;
    String password;
    JButton saveButton;
    JButton loadButton;
    JTextField usernameField;
    JPasswordField passwordField;
    JTextField emailField;
    JTextField telField;

    public StartPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.black);

        startButton = new JButton("Start");
        exitButton = new JButton("Exit");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(exitButton);

        textPanel = new JPanel();
        textPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        c.gridx = 0;
        c.gridy = 0;
        textPanel.add(usernameLabel, c);

        usernameField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 0;
        textPanel.add(usernameField, c);

        JLabel passwordLabel = new JLabel("Password:");
        c.gridx = 0;
        c.gridy = 1;
        textPanel.add(passwordLabel, c);

        passwordField = new JPasswordField(20);
        c.gridx = 1;
        c.gridy = 1;
        textPanel.add(passwordField, c);

        JLabel emailLabel = new JLabel("Email:");
        c.gridx = 0;
        c.gridy = 2;
        textPanel.add(emailLabel, c);

        emailField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 2;
        textPanel.add(emailField, c);

        JLabel telLabel = new JLabel("Tel:");
        c.gridx = 0;
        c.gridy = 3;
        textPanel.add(telLabel, c);

        telField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 3;
        textPanel.add(telField, c);

        /*JButton submitButton = new JButton("Submit");
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.CENTER;
        textPanel.add(submitButton, c);*/
        saveButton = new JButton("Submit");
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.CENTER;
        textPanel.add(saveButton, c);

        loadButton = new JButton("Load from file");
        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.CENTER;
        textPanel.add(loadButton, c);


        label = new JLabel("Welcome to the game!");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.black);
        label.setHorizontalAlignment(JLabel.CENTER);
        setBackground(Color.white);
        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(textPanel, BorderLayout.CENTER);


        saveButton.addActionListener(_ -> {
            email = emailField.getText();
            phone = telField.getText();
            username = usernameField.getText();
            password = Arrays.toString(passwordField.getPassword());

            System.out.println("Email: " + email);
            System.out.println("Phone: " + phone);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            emailField.setText("");
            telField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            saveToFile();
        });
        loadButton.addActionListener(_ -> {
            try {
                loadFromFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void saveToFile() {
        File file = new File("userdata.txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Username: " + username + "\n");
            System.out.println("Username: " + username);
            writer.write("Password: " + password + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("Tel: " + phone + "\n");
            JOptionPane.showMessageDialog(this, "Data saved successfully to " + file.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadFromFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                usernameField.setText(bufferedReader.readLine().split(": ")[1]);
                passwordField.setText(bufferedReader.readLine().split(": ")[1]);
                emailField.setText(bufferedReader.readLine().split(": ")[1]);
                telField.setText(bufferedReader.readLine().split(": ")[1]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
