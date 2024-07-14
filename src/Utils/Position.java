package Utils;

public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x , int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double range(Position other){
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }

    @Override
    public int compareTo(Position o) {
        int yCompare = Integer.compare(this.y, o.y);
        if(yCompare != 0) return yCompare;
        return Integer.compare(this.x, o.x);
    }
}
