package game;
import java.util.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pathfinding.Node;
import maze.MazeGenerator;
import pathfinding.BFSPathFinder;
import entities.Player;
import entities.Enemy;
public class GamePanel extends JPanel implements KeyListener {

    Player player;

    Enemy enemy;

    int tileSize =30;
    int cols = 21;
    int rows = 21;

    int [][] maze;

    boolean gameOver = false;
    boolean gameWon = false;

    int goalX = cols - 2;
    int goalY = rows - 2;

    BFSPathFinder pathFinder;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        setSize(cols * tileSize, rows * tileSize);
        setBackground(Color.BLACK);
        player = new Player(1, 1);

        enemy = new Enemy(cols - 2, rows - 2);
        MazeGenerator generator = new MazeGenerator(rows, cols);
        generator.generateMaze();
        maze = generator.getMaze();
        maze[enemy.getY()][enemy.getX()] = 0;
        pathFinder = new BFSPathFinder(maze, rows, cols);




        javax.swing.Timer timer = new javax.swing.Timer(300, e -> {

            if (!gameOver && !gameWon) {

                moveEnemy();

                checkGameOver();

                checkWin();
            }

            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);
        drawVisited(g);
        drawPath(g);
        drawGoal(g);
        drawPlayer(g);
        drawEnemy(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        if (gameOver) {
            g.drawString("GAME OVER", 220, 200);
        }

        if (gameWon) {
            g.drawString("YOU ESCAPED!", 200, 200);
        }
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

        g.fillRect(player.getX() * tileSize, player.getY()* tileSize, tileSize, tileSize);
    }

    public void drawEnemy(Graphics g) {

        g.setColor(Color.RED);

        g.fillRect(enemy.getX()* tileSize, enemy.getY() * tileSize, tileSize, tileSize);
    }

    public void drawGoal(Graphics g) {

        g.setColor(Color.YELLOW);

        g.fillRect(goalX * tileSize, goalY * tileSize, tileSize, tileSize);
    }

    public void drawVisited(Graphics g) {

        g.setColor(new Color(50, 100, 255));

        for (Node n : pathFinder.getVisitedNodes()){

            g.fillRect(
                    n.x * tileSize,
                    n.y * tileSize,
                    tileSize,
                    tileSize
            );
        }
    }
    public void drawPath(Graphics g) {

        g.setColor(Color.PINK);

        for (Node n : pathFinder.getFinalPath()){

            g.fillRect(
                    n.x * tileSize,
                    n.y * tileSize,
                    tileSize,
                    tileSize
            );
        }
    }
    public void moveEnemy() {

        ArrayList<Node> path =
                pathFinder.bfsPath(enemy.getX(), enemy.getY(),
                        player.getX(), player.getY());

        if (path.size() > 1) {

            Node next = path.get(0);

            enemy.setPosition(next.x, next.y);
        }
    }



    public void checkGameOver() {

        if (player.getX() == enemy.getX() && player.getY() == enemy.getY()){
            gameOver = true;
        }
    }

    public void checkWin() {

        if (player.getX() == goalX && player.getY() == goalY) {
            gameWon = true;
        }
    }





    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (gameOver || gameWon) {
            return;
        }

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            if (player.getY() > 0 && maze[player.getY() - 1][player.getX()] == 0) {
                player.setPosition(player.getX(), player.getY() - 1);
            }
        }

        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            if (player.getY() < rows - 1 && maze[player.getY() + 1][player.getX()] == 0) {
                player.setPosition(player.getX(), player.getY() + 1);
            }
        }

        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            if (player.getX() > 0 && maze[player.getY()][player.getX() - 1] == 0) {
                player.setPosition(player.getX() - 1, player.getY());
            }
        }

        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            if (player.getX() < cols - 1 && maze[player.getY()][player.getX() + 1] == 0) {
                player.setPosition(player.getX() + 1, player.getY());
            }
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}



}


