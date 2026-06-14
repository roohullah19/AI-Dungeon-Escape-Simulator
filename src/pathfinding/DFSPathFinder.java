package pathfinding;

import stats.AlgorithmStats;

import java.util.*;

public class DFSPathFinder {

    private int[][] maze;
    private int rows;
    private int cols;

    ArrayList<Node> visitedNodes = new ArrayList<>();
    ArrayList<Node> finalPath = new ArrayList<>();
    AlgorithmStats stats = new AlgorithmStats();

    public DFSPathFinder(int[][] maze, int rows, int cols) {

        this.maze = maze;
        this.rows = rows;
        this.cols = cols;
    }
    public ArrayList<Node> dfsPath(
            int startX,
            int startY,
            int targetX,
            int targetY) {

        visitedNodes.clear();
        finalPath.clear();
        long startTime = System.nanoTime();

        boolean[][] visited = new boolean[rows][cols];

        dfs(startX, startY, targetX, targetY, visited);

        Collections.reverse(finalPath);
        if (!finalPath.isEmpty()) {
            finalPath.remove(0);
        }
        stats.visitedNodes = visitedNodes.size();
        stats.pathLength = finalPath.size();
        stats.executionTime = System.nanoTime() - startTime;

        return finalPath;
    }
    private boolean dfs(int x, int y, int targetX, int targetY, boolean[][] visited) {
        if (x < 0 || y < 0 || x >= cols || y >= rows) {
            return false;
        }
        if (maze[y][x] == 1) {
            return false;
        }
        if (visited[y][x]) {
            return false;
        }
        visited[y][x] = true;

        visitedNodes.add(new Node(x, y));
        if (x == targetX && y == targetY) {

            finalPath.add(new Node(x, y));

            return true;
        }
        if (dfs(x, y - 1, targetX, targetY, visited) ||
                dfs(x, y + 1, targetX, targetY, visited) ||
                dfs(x - 1, y, targetX, targetY, visited) ||
                dfs(x + 1, y, targetX, targetY, visited)) {

            finalPath.add(new Node(x, y));

            return true;
        }


        return false;
    }

    public AlgorithmStats getStats() {
        return stats;
    }

    public ArrayList<Node> getVisitedNodes() {
        return visitedNodes;
    }

    public ArrayList<Node> getFinalPath() {
        return finalPath;
    }
}