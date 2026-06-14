package game;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import difficulty.Difficulty;
import entities.EnemyType;
import pathfinding.*;
import maze.MazeGenerator;
import entities.Player;
import entities.Enemy;
import powerups.*;
import stats.AlgorithmStats;

public class GamePanel extends JPanel implements KeyListener {

    Player player;

    ArrayList<Enemy> enemies;
    ArrayList<PowerUp> powerUps;
    Difficulty difficulty=Difficulty.HARD;

    Stack<Node> moveHistory = new Stack<>();
    TreeMap<Integer, String> leaderboard = new TreeMap<>(Collections.reverseOrder());
    int score = 0;
    long startTime;
    String playerName = "Player";

    private JPanel pausePanel;



    int tileSize =30;
    int cols = 21;
    int rows = 21;

    int [][] maze;

    boolean gameOver = false;
    boolean gameWon = false;
    boolean enemiesFrozen = false;
    long freezeEndTime = 0;
    boolean phaseWallActive = false;
    long phaseWallEndTime = 0;
    private GameWindow window;

    boolean paused = false;
    TreeSet<PowerUpType> collectedPowerUps = new TreeSet<>();


    int goalX = cols - 2;
    int goalY = rows - 2;

    BFSPathFinder pathFinder;
    AStarPathFinder aStarPathFinder;
    DFSPathFinder dfsPathFinder;

    public GamePanel(GameWindow window) {
        this.window=window;
        setFocusable(true);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        setSize(cols * tileSize, rows * tileSize);
        setBackground(Color.BLACK);

        initializeGame();

        javax.swing.Timer timer = new javax.swing.Timer(300, e -> {

            if (!gameOver && !gameWon&&!paused) {
                score = (int)((System.currentTimeMillis() - startTime) / 1000);
                checkPowerUpCollection();
                updatePowerUps();
                moveEnemy();

                checkGameOver();
                checkWin();

            }

            repaint();
        });
        timer.start();
        createPausePanel();
        add(pausePanel);
        pausePanel.setVisible(false);
        setLayout(null);
    }
    public void createPausePanel() {

        pausePanel = new JPanel();
        pausePanel.setLayout(null);
        pausePanel.setBounds(0, 0, cols * tileSize, rows * tileSize);
        pausePanel.setBackground(new Color(0, 0, 0, 180));

        JLabel title = new JLabel("PAUSED");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBounds(300, 150, 300, 50);
        pausePanel.add(title);

        JButton resume = createPauseButton("RESUME", 250);
        JButton restart = createPauseButton("RESTART", 310);
        JButton menu = createPauseButton("MAIN MENU", 370);
        JButton exit = createPauseButton("EXIT", 430);

        pausePanel.add(resume);
        pausePanel.add(restart);
        pausePanel.add(menu);
        pausePanel.add(exit);


        resume.addActionListener(e -> togglePause());

        restart.addActionListener(e -> {
            initializeGame();
            togglePause();
        });

        menu.addActionListener(e -> {
            togglePause();
            window.showMenu();
        });

        exit.addActionListener(e -> System.exit(0));
    }

    private JButton createPauseButton(String text, int y) {

        JButton btn = new JButton(text);
        btn.setBounds(300, y, 200, 40);

        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(30, 30, 30, 200));
        btn.setFocusPainted(false);

        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 120, 0, 200));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(30, 30, 30, 200));
            }
        });

        return btn;
    }

    public void togglePause() {
        paused = !paused;
        pausePanel.setVisible(paused);
    }

    public void initializeGame() {
        gameOver = false;
        gameWon = false;
        player = new Player(1, 1);
        enemies = new ArrayList<>();
        powerUps = new ArrayList<>();
        score = 0;
        startTime = System.currentTimeMillis();

        enemies = new ArrayList<>();
        collectedPowerUps.clear();

        if (difficulty == Difficulty.EASY) {
            enemies.add(new Enemy(cols - 2, rows - 2, EnemyType.BFS));
        }else if (difficulty == Difficulty.MEDIUM) {
            enemies.add(new Enemy(cols - 2, rows - 2, EnemyType.BFS));
            enemies.add(new Enemy(1, rows - 2, EnemyType.DFS));
        }else {
            enemies.add(new Enemy(cols - 2, rows - 2, EnemyType.BFS));
            enemies.add(new Enemy(1, rows - 2, EnemyType.DFS));
            enemies.add(new Enemy(cols - 2, 1, EnemyType.ASTAR));
        }


        MazeGenerator generator = new MazeGenerator(rows, cols);
        generator.generateMaze();
        maze = generator.getMaze();
        maze[enemies.get(0).getY()][enemies.get(0).getX()] = 0;
        pathFinder = new BFSPathFinder(maze, rows, cols);
        dfsPathFinder = new DFSPathFinder(maze, rows, cols);
        aStarPathFinder = new AStarPathFinder(maze, rows, cols);

        spawnPowerUp();
        spawnPowerUp();
        spawnPowerUp();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);
//        drawVisited(g);
        drawPath(g);
 //       drawDFSPath(g);
        drawGoal(g);
        drawPowerUp(g);
        drawPlayer(g);
        drawEnemy(g);
        drawStats(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        if (gameOver) {
            g.drawString("GAME OVER", 220, 200);
        }

        if (gameWon) {
            g.drawString("YOU ESCAPED!", 200, 200);
        }
        if (paused) {

            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PAUSED", 300, 200);

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press ESC to Resume", 260, 260);
        }
    }
    public void spawnPowerUp() {

        Random random = new Random();

        int x;
        int y;

        do {
            x = random.nextInt(cols);
            y = random.nextInt(rows);

        } while (maze[y][x] == 1 || (x == player.getX() && y == player.getY()));

        PowerUpType type;

        if (Math.random() < 0.5) {
            type = PowerUpType.FREEZE_ENEMY;
        }
        else {
            type = PowerUpType.PHASE_WALL;
        }

        powerUps.add(new PowerUp(x, y, type));
    }

    public void drawStats(Graphics g) {
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRoundRect(5, 5, 280, 180, 15, 15);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 12));

        g.drawString("=== ALGORITHM STATS ===", 15, 25);

        g.drawString("BFS: V=" + pathFinder.getStats().visitedNodes + " P=" + pathFinder.getStats().pathLength, 15, 50);

        g.drawString("DFS: V=" + dfsPathFinder.getStats().visitedNodes + " P=" + dfsPathFinder.getStats().pathLength, 15, 70);

        g.drawString("A*: V=" + aStarPathFinder.getStats().visitedNodes + " P=" + aStarPathFinder.getStats().pathLength, 15, 90);

        g.drawString("Time BFS: " + pathFinder.getStats().executionTime, 15, 120);
        g.drawString("Time A*: " + dfsPathFinder.getStats().executionTime, 15, 140);
        g.drawString("Time DFS: " + aStarPathFinder.getStats().executionTime, 15, 160);
        g.drawString("Heap Ops: " + aStarPathFinder.getStats().heapOperations, 15, 180);

        g.drawString("Score: " + score, 15, 205);


    }
    public void drawPowerUp(Graphics g) {
        for (PowerUp powerUp:powerUps){
            if (powerUp == null) {
                return;
            }
            switch (powerUp.getType()){
                case FREEZE_ENEMY -> {
                    g.setColor(Color.CYAN);
                    break;
                }case PHASE_WALL -> {
                    g.setColor(Color.magenta);
                    break;
                }
            }



            g.fillOval(powerUp.getX() * tileSize, powerUp.getY() * tileSize, tileSize, tileSize);

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



        for (Enemy enemy : enemies) {
            if(enemy.getType()==EnemyType.BFS){
                g.setColor(Color.RED);

                g.fillRect(enemy.getX() * tileSize, enemy.getY() * tileSize, tileSize, tileSize);
            } else if (enemy.getType()==EnemyType.DFS) {
                g.setColor(Color.ORANGE);
                g.fillRect(enemy.getX() * tileSize, enemy.getY() * tileSize, tileSize, tileSize);

            }else if (enemy.getType() == EnemyType.ASTAR) {

                g.setColor(Color.MAGENTA);

                g.fillRect(
                        enemy.getX() * tileSize,
                        enemy.getY() * tileSize,
                        tileSize,
                        tileSize
                );
            }
        }
    }

    public void drawGoal(Graphics g) {

        g.setColor(Color.YELLOW);

        g.fillRect(goalX * tileSize, goalY * tileSize, tileSize, tileSize);
    }
    public void drawDFSPath(Graphics g) {

        g.setColor(Color.cyan);

        for (Node n : dfsPathFinder.getFinalPath()) {

            g.fillRect(
                    n.x * tileSize,
                    n.y * tileSize,
                    tileSize,
                    tileSize
            );
        }
    }

    public void drawVisited(Graphics g) {

        g.setColor(new Color(50, 100, 255));

        for (Node n : pathFinder.getVisitedNodes()){

            g.fillRect(n.x * tileSize, n.y * tileSize, tileSize, tileSize);
        }
    }
    public void drawPath(Graphics g) {

        g.setColor(Color.PINK);

        for (Node n : pathFinder.getFinalPath()){

            g.fillRect(n.x * tileSize, n.y * tileSize, tileSize, tileSize);
        }
    }
    public void moveEnemy() {
        if (enemiesFrozen) {
            if (System.currentTimeMillis() > freezeEndTime) {
                enemiesFrozen = false;
            } else {
                return;
            }
        }

        for (Enemy enemy : enemies) {

            if (enemy.getType() == EnemyType.BFS) {

                ArrayList<Node> path =
                        pathFinder.bfsPath(
                                enemy.getX(),
                                enemy.getY(),
                                player.getX(),
                                player.getY()
                        );

                if (!path.isEmpty()) {

                    Node next = path.get(0);

                    enemy.setPosition(next.x, next.y);
                }
            }

            else if (enemy.getType() == EnemyType.DFS) {

                ArrayList<Node> path = dfsPathFinder.dfsPath(enemy.getX(), enemy.getY(), player.getX(), player.getY());
                if (!path.isEmpty()) {

                    Node next = path.get(0);

                    enemy.setPosition(next.x, next.y);
                }
            }
            else if (enemy.getType() == EnemyType.ASTAR) {

                ArrayList<Node> path =
                        aStarPathFinder.aStarPath(
                                enemy.getX(),
                                enemy.getY(),
                                player.getX(),
                                player.getY()
                        );

                if (!path.isEmpty()) {

                    Node next = path.get(0);
                    enemy.setPosition(next.x, next.y);

                }
            }
        }
    }



    public void checkGameOver() {

        for (Enemy enemy : enemies) {

            if (player.getX() == enemy.getX() && player.getY() == enemy.getY()) {

                gameOver = true;
                leaderboard.put(score,playerName);
                return;
            }
        }
    }

    public void checkWin() {

        if (player.getX() == goalX && player.getY() == goalY) {
            score+=10;
            gameWon = true;
            leaderboard.put(score,playerName);
        }
    }

    public void checkPowerUpCollection() {

        Iterator<PowerUp> iterator = powerUps.iterator();

        while (iterator.hasNext()) {

            PowerUp powerUp = iterator.next();

            if (player.getX() == powerUp.getX() && player.getY() == powerUp.getY()) {
                score+=5;

                collectedPowerUps.add(powerUp.getType());

                if (powerUp.getType() == PowerUpType.FREEZE_ENEMY) {

                    enemiesFrozen = true;
                    freezeEndTime = System.currentTimeMillis() + 5000;
                }

                else if (powerUp.getType() == PowerUpType.PHASE_WALL) {

                    phaseWallActive = true;
                    phaseWallEndTime = System.currentTimeMillis() + 5000;
                }

                iterator.remove();
            }
        }
    }
    public void updatePowerUps() {

        if (phaseWallActive && System.currentTimeMillis() > phaseWallEndTime) {

            phaseWallActive = false;
        }
    }





    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            togglePause();
            return;
        }
        if (key == KeyEvent.VK_R) {
            initializeGame();
            paused = false;
        }
        if (key == KeyEvent.VK_M) {
            window.showMenu();
        }


        if (key == KeyEvent.VK_R) {

            initializeGame();

            repaint();

            return;
        }

        if (gameOver || gameWon) {
            return;
        }
        if (key == KeyEvent.VK_Z) {

            if (!moveHistory.isEmpty()) {
                Node previous = moveHistory.pop();

                player.setPosition(previous.x, previous.y);
            }
            repaint();
            return;
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            if (player.getY() > 0 && (phaseWallActive || maze[player.getY() - 1][player.getX()] == 0)) {
                moveHistory.push(new Node(player.getX(), player.getY()));
                player.setPosition(player.getX(),player.getY() - 1
                );
            }
        }

        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            if (player.getY() < rows - 1 &&(phaseWallActive || maze[player.getY() +1][player.getX()] == 0)) {
                moveHistory.push(new Node(player.getX(), player.getY()));
                player.setPosition(player.getX(), player.getY() + 1);
            }
        }

        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {

            if (player.getX() > 0 && (phaseWallActive || maze[player.getY()][player.getX()-1] == 0)) {
                moveHistory.push(new Node(player.getX(), player.getY()));
                player.setPosition(player.getX() - 1, player.getY());
            }
        }

        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            if (player.getX() < cols - 1 && (phaseWallActive || maze[player.getY()][player.getX()+1] == 0)) {
                moveHistory.push(new Node(player.getX(), player.getY()));
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


