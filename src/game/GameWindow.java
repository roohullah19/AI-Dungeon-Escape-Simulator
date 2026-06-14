package game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;

    MenuPanel menuPanel;
    GamePanel gamePanel;
    GameSetupPanel namePanel;



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
        namePanel=new GameSetupPanel(this);

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(namePanel, "SETUP");
        mainPanel.add(gamePanel, "GAME");

        add(mainPanel);

        cardLayout.show(mainPanel, "MENU");

        setVisible(true);
    }
    public GamePanel getGamePanel() {return gamePanel;
    }

    public void showMenu() {
        gamePanel.stopGameLoop();
        cardLayout.show(mainPanel, "MENU");
    }
    public void showSetupScreen() {
        gamePanel.stopGameLoop();
        cardLayout.show(mainPanel, "SETUP");
    }

    public void startGame() {
        cardLayout.show(mainPanel, "GAME");
        gamePanel.startNewGame();
        gamePanel.requestFocusInWindow();

        gamePanel.startGameLoop();
    }
    public boolean isGameActive() {
        return cardLayout != null;
    }
}