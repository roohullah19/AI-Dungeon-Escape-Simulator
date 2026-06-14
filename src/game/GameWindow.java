package game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;

    MenuPanel menuPanel;
    GamePanel gamePanel;

    public GameWindow() {

        setTitle("AI Dungeon Escape Simulator");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this);

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(gamePanel, "GAME");

        add(mainPanel);

        cardLayout.show(mainPanel, "MENU");

        setVisible(true);
    }
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void showMenu() {
        cardLayout.show(mainPanel, "MENU");
    }

    public void startGame() {
        cardLayout.show(mainPanel, "GAME");
        gamePanel.requestFocusInWindow();
    }
}