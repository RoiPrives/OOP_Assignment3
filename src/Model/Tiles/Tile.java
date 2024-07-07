package Model.Tiles;

import Model.Utils.Position;

public abstract class Tile {
    protected char tile;
    protected Position position;

    public Tile(char tile) {
        this.tile = tile;
    }

    public void initialize(Position position) {
        this.position = position;
    }
}
