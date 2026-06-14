package pathfinding;

public class AStarNode  implements Comparable<AStarNode>{

    int x;
    int y;

    int g;
    int h;
    int f;

    AStarNode parent;

    public AStarNode(int x, int y) {

        this.x = x;
        this.y = y;
    }
    @Override
    public int compareTo(AStarNode other) {

        return this.f - other.f;
    }
}