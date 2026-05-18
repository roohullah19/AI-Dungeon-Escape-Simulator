package game;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow() {

        setTitle("AI Dungeon Escape Simulator");

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        GamePanel panel = new GamePanel();
        add(panel);

        setVisible(true);
    }
}