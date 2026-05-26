package entities;

public class Enemy {

    int x;
    int y;

    public Enemy(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {

        this.x = x;
        this.y = y;
    }
}