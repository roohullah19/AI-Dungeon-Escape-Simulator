package pathfinding;

import java.util.*;
import stats.AlgorithmStats;


public class BFSPathFinder {
    ArrayList<Node> visitedNodes = new ArrayList<>();
    ArrayList<Node> finalPath = new ArrayList<>();
    AlgorithmStats stats = new AlgorithmStats();
    int[][] maze;

    int rows;
    int cols;

    public BFSPathFinder(int[][] maze, int rows, int cols) {

        this.maze = maze;

        this.rows = rows;
        this.cols = cols;
    }
    public ArrayList<Node> bfsPath(int startX, int startY, int targetX, int targetY) {
        long startTime = System.nanoTime();
        visitedNodes.clear();
        finalPath.clear();


        boolean[][] visited = new boolean[rows][cols];
        Node[][] parent = new Node[rows][cols];

        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(startX, startY));
        visited[startY][startX] = true;

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        boolean pathFound = false;

        while (!queue.isEmpty()) {

            Node current = queue.poll();
            visitedNodes.add(current);

            if (current.x == targetX && current.y == targetY) {
                pathFound = true;
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
        if (!pathFound) {
            return finalPath;
        }



        Node current = new Node(targetX, targetY);

        while (current != null && !(current.x == startX && current.y == startY)) {
            finalPath.add(current);
            current = parent[current.y][current.x];
        }

        Collections.reverse(finalPath);
        stats.visitedNodes=visitedNodes.size();
        stats.pathLength=finalPath.size();
        stats.executionTime =System.nanoTime() - startTime;

        return finalPath;
    }

    public AlgorithmStats getStats() {return stats;}

    public ArrayList<Node> getVisitedNodes() {
        return visitedNodes;
    }

    public ArrayList<Node> getFinalPath() {
        return finalPath;
    }



}