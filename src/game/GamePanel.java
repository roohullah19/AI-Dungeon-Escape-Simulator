package game;
import java.util.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {

    int playerX = 1;
    int playerY = 1;

    int enemyX = 13;
    int enemyY = 8;

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

        javax.swing.Timer timer = new javax.swing.Timer(200, e -> {
            moveEnemy();
            repaint();
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);
        drawPlayer(g);
        drawEnemy(g);
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

    public void drawEnemy(Graphics g) {

        g.setColor(Color.RED);

        g.fillRect(enemyX * tileSize, enemyY * tileSize, tileSize, tileSize);
    }
    public ArrayList<Node> bfsPath(int startX, int startY, int targetX, int targetY) {

        boolean[][] visited = new boolean[rows][cols];
        Node[][] parent = new Node[rows][cols];

        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(startX, startY));
        visited[startY][startX] = true;

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            if (current.x == targetX && current.y == targetY) {
                break;
            }

            for (int i = 0; i < 4; i++) {

                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < cols && ny < rows) {

                    if (!visited[ny][nx] && maze[ny][nx] == 0) {

                        queue.add(new Node(nx, ny));
                        visited[ny][nx] = true;
                        parent[ny][nx] = current;
                    }
                }
            }
        }

        ArrayList<Node> path = new ArrayList<>();

        Node current = new Node(targetX, targetY);

        while (current != null && !(current.x == startX && current.y == startY)) {
            path.add(current);
            current = parent[current.y][current.x];
        }

        Collections.reverse(path);

        return path;
    }

    public void moveEnemy() {

        ArrayList<Node> path = bfsPath(enemyX, enemyY, playerX, playerY);

        if (!path.isEmpty()) {
            Node next = path.getFirst();

            enemyX = next.x;
            enemyY = next.y;
        }
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
                    maze[r][c] = (Math.random() < 0.3) ? 1 : 0;
                }
            }
        }

        maze[1][1] = 0;
        maze[1][2] = 0;
        maze[2][1] = 0;

    }
}
class Node {
    int x, y;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

