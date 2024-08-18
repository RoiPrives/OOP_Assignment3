package Model.Tiles;

import Model.Tiles.Units.Unit;
import Utils.Position;

public abstract class Tile {
    protected char tile;
    protected Position position;

    public Tile(char tile) {
        this.tile = tile;
    }

    public Tile initialize(Position position) {
        this.position = position;
        return this;
    }

    public abstract Position accept(Unit unit);

    public void swapPositions(Tile toSwap) {
        Position temp = new Position(toSwap.position);
        toSwap.position = new Position(this.position);
        this.position = new Position(temp);
    }

    public Position getPosition(){
        return this.position;
    }
    public char getTile() {
        return tile;
    }

}
