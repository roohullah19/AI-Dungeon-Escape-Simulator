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

    int [][] maze;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        setSize(cols * tileSize, rows * tileSize);
        setBackground(Color.BLACK);
        maze=new int[rows][cols];
        generateMaze();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);
        drawPlayer(g);
    }

    public void drawGrid(Graphics g) {

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                int x = col * tileSize;
                int y = row * tileSize;

                if (maze[row][col] == 1) {
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, tileSize, tileSize);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, tileSize, tileSize);
                }

                g.setColor(Color.DARK_GRAY);
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
            if (playerY > 0 && maze[playerY - 1][playerX] == 0)
                playerY--;
        }

        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            if (playerY < rows - 1 && maze[playerY + 1][playerX] == 0)
                playerY++;
        }

        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            if (playerX > 0 && maze[playerY][playerX - 1] == 0)
                playerX--;
        }

        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            if (playerX < cols - 1 && maze[playerY][playerX + 1] == 0)
                playerX++;
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void generateMaze() {

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                if (r == 0 || c == 0 || r == rows - 1 || c == cols - 1) {
                    maze[r][c] = 1;
                }
                else {
                    maze[r][c] = (Math.random() < 0.2) ? 1 : 0;
                }
            }
        }

        maze[0][0] = 0;
    }
}