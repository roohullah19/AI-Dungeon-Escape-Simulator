package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {

    private Image background;

    private JButton startButton;
    private JButton instructionButton;
    private JButton aboutButton;
    private JButton exitButton;
    private GameWindow window;

    public MenuPanel(GameWindow window) {
        this.window=window;

        setLayout(null);
        background = new ImageIcon("bg.png").getImage();

        JLabel title = new JLabel("AI DUNGEON ESCAPE SIMULATOR");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(150, 80, 600, 40);
        add(title);

        startButton = createButton("START GAME");
        instructionButton = createButton("INSTRUCTIONS");
        aboutButton = createButton("ABOUT BFS / DFS");
        exitButton = createButton("EXIT");
        int centerX = 270;
        int width = 260;
        int height = 45;
        int startY = 300;
        int gap = 60;

        startButton.setBounds(centerX, startY, width, height);
        instructionButton.setBounds(centerX, startY + gap, width, height);
        aboutButton.setBounds(centerX, startY + gap * 2, width, height);
        exitButton.setBounds(centerX, startY + gap * 3, width, height);

        add(startButton);
        add(instructionButton);
        add(aboutButton);
        add(exitButton);

        startButton.addActionListener(e -> {
            window.showSetupScreen();
        });

        instructionButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Use WASD or Arrow Keys to move.\n" +
                            "Reach the yellow goal while avoiding enemies.\n" +
                            "Collect power-ups for advantages.");
        });

        aboutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "BFS Enemy: Finds shortest path using Breadth First Search\n" +
                            "DFS Enemy: Moves randomly using Depth First style exploration\n" +
                            "Maze is generated using recursive backtracking (DSA concept)");
        });

        exitButton.addActionListener(e -> System.exit(0));
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);


        button.setFont(new Font("Arial", Font.BOLD, 16));

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.WHITE);

        button.setBackground(new Color(20, 20, 20, 180));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 120, 0, 180));
                button.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(20, 20, 20, 180));
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}