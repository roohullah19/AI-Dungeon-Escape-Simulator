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

    int tileSize =30;
    int cols = 21;
    int rows = 21;

    int [][] maze;

    boolean gameOver = false;
    boolean gameWon = false;

    int goalX = cols - 2;
    int goalY = rows - 2;

    ArrayList<Node> visitedNodes = new ArrayList<>();
    ArrayList<Node> finalPath = new ArrayList<>();

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        setSize(cols * tileSize, rows * tileSize);
        setBackground(Color.BLACK);
        maze=new int[rows][cols];
        generateMaze();

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

        g.fillRect(playerX * tileSize, playerY * tileSize, tileSize, tileSize);
    }

    public void drawEnemy(Graphics g) {

        g.setColor(Color.RED);

        g.fillRect(enemyX * tileSize, enemyY * tileSize, tileSize, tileSize);
    }

    public void drawGoal(Graphics g) {

        g.setColor(Color.YELLOW);

        g.fillRect(goalX * tileSize, goalY * tileSize, tileSize, tileSize);
    }

    public void drawVisited(Graphics g) {

        g.setColor(new Color(50, 100, 255));

        for (Node n : visitedNodes) {

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

        for (Node n : finalPath) {

            g.fillRect(
                    n.x * tileSize,
                    n.y * tileSize,
                    tileSize,
                    tileSize
            );
        }
    }

    public ArrayList<Node> bfsPath(int startX, int startY, int targetX, int targetY) {
        visitedNodes.clear();


        boolean[][] visited = new boolean[rows][cols];
        Node[][] parent = new Node[rows][cols];

        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(startX, startY));
        visited[startY][startX] = true;

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        while (!queue.isEmpty()) {

            Node current = queue.poll();
            visitedNodes.add(current);

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

        finalPath.clear();

        Node current = new Node(targetX, targetY);

        while (current != null && !(current.x == startX && current.y == startY)) {
            finalPath.add(current);
            current = parent[current.y][current.x];
        }

        Collections.reverse(finalPath);

        return finalPath;
    }

    public void moveEnemy() {

        ArrayList<Node> path = bfsPath(enemyX, enemyY, playerX, playerY);

        if (!path.isEmpty()) {
            Node next = path.getFirst();

            enemyX = next.x;
            enemyY = next.y;
        }
    }

    public void checkGameOver() {

        if (playerX == enemyX && playerY == enemyY) {
            gameOver = true;
        }
    }

    public void checkWin() {

        if (playerX == goalX && playerY == goalY) {
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
                maze[r][c] = 1;
            }
        }


        dfsMaze(1, 1);
        addExtraPaths();


        maze[1][1] = 0;
        maze[rows - 2][cols - 2] = 0;
    }

    public void dfsMaze(int x, int y) {

        maze[y][x] = 0;

        int[] dx = {0, 0, 2, -2};
        int[] dy = {2, -2, 0, 0};

        Integer[] directions = {0, 1, 2, 3};

        Collections.shuffle(Arrays.asList(directions));

        for (int dir : directions) {

            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (nx > 0 && ny > 0 &&
                    nx < cols - 1 &&
                    ny < rows - 1 &&
                    maze[ny][nx] == 1) {

                maze[y + dy[dir] / 2][x + dx[dir] / 2] = 0;

                dfsMaze(nx, ny);
            }
        }
    }
    public void addExtraPaths() {

        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {


                if (maze[r][c] == 1) {

                    if (Math.random() < 0.15) {

                        maze[r][c] = 0;
                    }
                }
            }
        }
    }
}
class Node {
    int x, y;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

