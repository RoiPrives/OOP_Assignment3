package Utils;

import java.util.Objects;

public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x , int y){
        this.x = x;
        this.y = y;
    }

    public Position(Position pos){
        this.x = pos.getX();
        this.y = pos.getY();
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
    public void setPosition(Position pos){
        x = pos.getX();
        y = pos.getY();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position){
            Position pos = (Position)obj;
            return x == pos.getX() && y == pos.getY();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Position o) {
        int yCompare = Integer.compare(this.y, o.y);
        if(yCompare != 0) return yCompare;
        return Integer.compare(this.x, o.x);
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }
}
