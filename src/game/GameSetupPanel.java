package game;

import javax.swing.*;
import java.awt.*;

import difficulty.Difficulty;

public class GameSetupPanel extends JPanel {

    private GameWindow window;

    private JTextField nameField;
    private JComboBox<Difficulty> difficultyBox;

    public GameSetupPanel(GameWindow window) {
        this.window = window;

        setLayout(null);
        setBackground(Color.BLACK);

        JLabel title = new JLabel("GAME SETUP");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(300, 100, 300, 40);
        add(title);

        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(250, 180, 200, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(250, 210, 300, 35);
        add(nameField);

        JLabel diffLabel = new JLabel("Select Difficulty:");
        diffLabel.setForeground(Color.WHITE);
        diffLabel.setBounds(250, 260, 200, 30);
        add(diffLabel);

        difficultyBox = new JComboBox<>(Difficulty.values());
        difficultyBox.setBounds(250, 290, 300, 35);
        add(difficultyBox);

        JButton startBtn = new JButton("START GAME");
        startBtn.setBounds(300, 360, 200, 40);

        startBtn.setBackground(new Color(0, 120, 0));
        startBtn.setForeground(Color.WHITE);

        startBtn.addActionListener(e -> {

            String name = nameField.getText().trim();

            if (name.isEmpty()) return;

            window.getGamePanel().setPlayerName(name);
            window.getGamePanel().setDifficulty((Difficulty) difficultyBox.getSelectedItem());

            window.startGame();
        });

        add(startBtn);
    }
}