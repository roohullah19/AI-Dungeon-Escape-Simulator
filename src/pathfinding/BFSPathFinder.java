package pathfinding;

import java.util.*;

public class BFSPathFinder {
    ArrayList<Node> visitedNodes = new ArrayList<>();
    ArrayList<Node> finalPath = new ArrayList<>();
    int[][] maze;

    int rows;
    int cols;

    public BFSPathFinder(int[][] maze, int rows, int cols) {

        this.maze = maze;

        this.rows = rows;
        this.cols = cols;
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


    public ArrayList<Node> getVisitedNodes() {
        return visitedNodes;
    }

    public ArrayList<Node> getFinalPath() {
        return finalPath;
    }

}