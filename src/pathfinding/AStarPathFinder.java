package pathfinding;
import java.util.*;
import stats.AlgorithmStats;


public class AStarPathFinder {

    private int[][] maze;
    private int rows;
    private int cols;
    ArrayList<Node> visitedNodes = new ArrayList<>();
    AlgorithmStats stats = new AlgorithmStats();

    ArrayList<Node> finalPath = new ArrayList<>();

    public AStarPathFinder(int[][] maze, int rows, int cols) {

        this.maze = maze;
        this.rows = rows;
        this.cols = cols;
    }
    public ArrayList<Node> aStarPath(int startX, int startY, int targetX, int targetY) {
        visitedNodes.clear();
        finalPath.clear();
        stats.heapOperations = 0;
        PriorityQueue<AStarNode> openList = new PriorityQueue<>();
        HashSet<String> closedList = new HashSet<>();
        AStarNode startNode = new AStarNode(startX, startY);
        AStarNode goalNode = null;
        long startTime = System.nanoTime();

        startNode.g = 0;

        startNode.h = Math.abs(startX - targetX) + Math.abs(startY - targetY);

        startNode.f = startNode.g + startNode.h;
        openList.add(startNode);
        stats.heapOperations++;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        while (!openList.isEmpty()){
            AStarNode current =openList.poll();
            stats.heapOperations++;
            visitedNodes.add(new Node(current.x, current.y));
            closedList.add(current.x+","+current.y);

            if (current.x == targetX && current.y == targetY) {
                goalNode = current;
                break;
            }
            for (int i = 0; i < 4; i++) {

                int nx = current.x + dx[i];
                int ny = current.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= cols || ny >= rows) {
                    continue;
                }
                if (maze[ny][nx] == 1) {
                    continue;
                }
                if (closedList.contains(nx + "," + ny)) {
                    continue;
                }
                AStarNode neighbor = new AStarNode(nx, ny);
                neighbor.g = current.g + 1;
                neighbor.h = Math.abs(nx - targetX) + Math.abs(ny - targetY);
                neighbor.f=neighbor.g+neighbor.h;
                neighbor.parent=current;
                openList.add(neighbor);
                stats.heapOperations++;

            }



        }
        if (goalNode == null) {
            stats.visitedNodes = visitedNodes.size();
            stats.pathLength = finalPath.size();
            stats.executionTime = System.nanoTime() - startTime;
            return finalPath;
        }
        AStarNode current = goalNode;
        while (current.parent != null) {

            finalPath.add(new Node(current.x, current.y));

            current = current.parent;
        }

        Collections.reverse(finalPath);
        stats.visitedNodes = visitedNodes.size();
        stats.pathLength = finalPath.size();
        stats.executionTime = System.nanoTime() - startTime;

        return finalPath;
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