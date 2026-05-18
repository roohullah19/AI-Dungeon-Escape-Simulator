package game;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {

    int playerX = 0;
    int playerY = 0;

    int tileSize = 40;
    int cols = 15;
    int rows = 10;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        setSize(cols * tileSize, rows * tileSize);
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);
        drawPlayer(g);
    }

    public void drawGrid(Graphics g) {

        g.setColor(Color.DARK_GRAY);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                int x = col * tileSize;
                int y = row * tileSize;

                g.drawRect(x, y, tileSize, tileSize);
            }
        }
    }

    public void drawPlayer(Graphics g) {

        g.setColor(Color.GREEN);

        g.fillRect(playerX * tileSize, playerY * tileSize, tileSize, tileSize);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            if (playerY > 0) playerY--;
        }

        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            if (playerY < rows - 1) playerY++;
        }

        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            if (playerX > 0) playerX--;
        }

        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            if (playerX < cols - 1) playerX++;
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}