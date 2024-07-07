package Model.Utils;

public class Position {
    private int x;
    private int y;

    public Position(int x , int y){
        this.x = x;
        this.y = y;
    }

    public double range(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

}
