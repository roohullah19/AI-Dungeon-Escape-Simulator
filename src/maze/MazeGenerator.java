package maze;

import java.util.*;

public class MazeGenerator {

    int rows;
    int cols;

    int[][] maze;

    public MazeGenerator(int rows, int cols) {

        this.rows = rows;
        this.cols = cols;

        maze = new int[rows][cols];
    }

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
    public int[][] getMaze() {
        return maze;
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

                    if (Math.random() < 0.25) {

                        maze[r][c] = 0;
                    }
                }
            }
        }
    }
}